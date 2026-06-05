/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import Modelo.dao.UsuarioDao;
import javax.swing.*;

import Modelo.entidades.Incidencia;
import Modelo.entidades.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Vector;
import java.sql.SQLException;

/**
 *
 * @author Portege Z930
 */

public class DModificarIncidencia extends JDialog {
    private Incidencia incidencia;
    private boolean modificado = false;
    private Usuario usuarioActual;
    private JTextField txtTitulo;
    private JTextArea txtDescripcion;
    private JComboBox<String> cbPrioridad;
    private JComboBox<String> cbEstado;
    private JComboBox cbTecnico;
    
    public DModificarIncidencia(JFrame parent, Incidencia incidencia, Usuario usuarioActual) {
        super(parent, "Modificar Incidencia", true);
        this.incidencia = incidencia;
        this.usuarioActual = usuarioActual;
        
        setSize(500, 400);
        setLocationRelativeTo(parent);
        initComponents();
    }
    //en estás otras tres ventanas estoy usando el initComponents para mejor organización del código (*en la principal no se me ha ocurrido desde el principio...)
    private void initComponents() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  
        // Panel de campos
        JPanel camposPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        // Título (Todos pueden modificar)
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        camposPanel.add(new JLabel("Título:"), gbc);
        
        gbc.gridx = 1; gbc.weightx = 1;
        txtTitulo = new JTextField(incidencia.getTitulo(), 30);
        camposPanel.add(txtTitulo, gbc);
        
        // Descripción (Todos pueden modificar)
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        camposPanel.add(new JLabel("Descripción:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1; gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        txtDescripcion = new JTextArea(incidencia.getDescripcion(), 5, 30);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        camposPanel.add(scrollDesc, gbc);
        
        // Prioridad (Todos pueden modificar)
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        camposPanel.add(new JLabel("Prioridad:"), gbc);
        
        gbc.gridx = 1;
        cbPrioridad = new JComboBox<>(new String[]{"BAJA", "MEDIA", "ALTA"});
        cbPrioridad.setSelectedItem(incidencia.getPrioridad().name());
        camposPanel.add(cbPrioridad, gbc);
        
        // Estado (Solo técnicos y administradores)
        Usuario.Perfil perfil = usuarioActual.getPerfil();
        if (perfil == Usuario.Perfil.TECNICO || perfil == Usuario.Perfil.ADMINISTRADOR) {
            gbc.gridx = 0; gbc.gridy = 3;
            camposPanel.add(new JLabel("Estado:"), gbc);
            gbc.gridx = 1;
            // Determina estados disponibles según estado actual
            Incidencia.Estado estadoActual = incidencia.getEstado();
            Vector<String> estadosDisponibles = new Vector<>();
            if (estadoActual == Incidencia.Estado.PENDIENTE) {
                estadosDisponibles.add("PENDIENTE");
                estadosDisponibles.add("EN_PROCESO");
                estadosDisponibles.add("ESPERA");
                // !! No puede pasar directamente a CERRADA desde PENDIENTE
            } else if (estadoActual == Incidencia.Estado.EN_PROCESO) {
                estadosDisponibles.add("EN_PROCESO");
                estadosDisponibles.add("ESPERA");
                estadosDisponibles.add("CERRADA");
            } else if (estadoActual == Incidencia.Estado.ESPERA) {
                estadosDisponibles.add("ESPERA");
                estadosDisponibles.add("EN_PROCESO");
                estadosDisponibles.add("CERRADA");
            } else if (estadoActual == Incidencia.Estado.CERRADA) {
                estadosDisponibles.add("CERRADA");
                // Cerrada solo se puede reabrir, no cambia a otros estados
            }
            cbEstado = new JComboBox<>(estadosDisponibles);
            cbEstado.setSelectedItem(estadoActual.name());
            camposPanel.add(cbEstado, gbc);
            // Técnico (Solo para técnicos/admins)
            gbc.gridx = 0; gbc.gridy = 4;
            camposPanel.add(new JLabel("Asignar a técnico:"), gbc);
            
            gbc.gridx = 1;
            cbTecnico = new JComboBox<>();
            cargarTecnicosEnCombo();
            camposPanel.add(cbTecnico, gbc);
        } else {
            // Para usuarios, no mostrar estado ni técnico
            cbEstado = null;
            cbTecnico = null;
        }      
        // Panel de botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar Cambios");
        JButton btnCancelar = new JButton("Cancelar"); 
        // Estilo botón guardar según perfil
        if (perfil == Usuario.Perfil.USUARIO) {
            btnGuardar.setBackground(new Color(70, 130, 180)); // Azul
        } else if (perfil == Usuario.Perfil.TECNICO) {
            btnGuardar.setBackground(new Color(60, 179, 113)); // Verde
        } else {
            btnGuardar.setBackground(new Color(220, 120, 60)); // Naranja
        }
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.addActionListener(e -> {
            if (validarCampos()) {
                actualizarIncidencia();
                modificado = true;
                dispose();
            }
        });
        btnCancelar.addActionListener(e -> dispose());
        botonesPanel.add(btnCancelar);
        botonesPanel.add(btnGuardar);
        
        panel.add(camposPanel, BorderLayout.CENTER);
        panel.add(botonesPanel, BorderLayout.SOUTH);
        
        add(panel);
        // Bloquea campos si el usuario no es el autor
        if (perfil == Usuario.Perfil.USUARIO && 
            incidencia.getAutor().getIdUsuario() != usuarioActual.getIdUsuario()) {
            bloquearTodosLosCampos();
            btnGuardar.setEnabled(false);
            JOptionPane.showMessageDialog(this,
                "Solo puede modificar sus propias incidencias",
                "Permiso denegado",
                JOptionPane.WARNING_MESSAGE);
        }
        // Bloquea si la incidencia no está PENDIENTE (para usuarios solo)
        if (perfil == Usuario.Perfil.USUARIO && 
            incidencia.getEstado() != Incidencia.Estado.PENDIENTE) {
            bloquearTodosLosCampos();
            btnGuardar.setEnabled(false);
            JOptionPane.showMessageDialog(this,
                "Solo puede modificar incidencias en estado PENDIENTE",
                "Incidencia no modificable",
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void cargarTecnicosEnCombo() {
        try {
            cbTecnico.removeAllItems();
            cbTecnico.addItem("-- Sin asignar --");
            UsuarioDao usuarioDao = new UsuarioDao();
            List<Usuario> tecnicos = usuarioDao.obtenerTecnicos();
            Usuario tecnicoActual = incidencia.getTecnicoAsignado();
            Usuario tecnicoSeleccionado = null;
            for (Usuario tecnico : tecnicos) {
                cbTecnico.addItem(tecnico);
                if (tecnicoActual != null && tecnico.getIdUsuario() == tecnicoActual.getIdUsuario()) {
                    tecnicoSeleccionado = tecnico;
                }
            }
            if (tecnicoSeleccionado != null) {
                cbTecnico.setSelectedItem(tecnicoSeleccionado);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            cbTecnico.addItem("Error cargando técnicos");
        }
    }
    
    private void bloquearTodosLosCampos() {
        txtTitulo.setEnabled(false);
        txtDescripcion.setEnabled(false);
        cbPrioridad.setEnabled(false);
        if (cbEstado != null) cbEstado.setEnabled(false);
        if (cbTecnico != null) cbTecnico.setEnabled(false);
    }
    
    private boolean validarCampos() {
        if (txtTitulo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título no puede estar vacío");
            return false;
        }
        if (txtDescripcion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La descripción no puede estar vacía");
            return false;
        }
        return true;
    }
    
    private void actualizarIncidencia() {
        // Guarda cambios básicos (todos los perfiles)
        incidencia.setTitulo(txtTitulo.getText().trim());
        incidencia.setDescripcion(txtDescripcion.getText().trim());
        incidencia.setPrioridad(Incidencia.Prioridad.valueOf((String) cbPrioridad.getSelectedItem()));
        // Cambios solo para técnicos y administradores
        if (usuarioActual.getPerfil() == Usuario.Perfil.TECNICO || 
            usuarioActual.getPerfil() == Usuario.Perfil.ADMINISTRADOR) {
            // Cambiar estado
            if (cbEstado != null) {
                Incidencia.Estado nuevoEstado = Incidencia.Estado.valueOf((String) cbEstado.getSelectedItem()); 
                // Si cambia a EN_PROCESO y no tenía fecha inicio, asignarla
                if (nuevoEstado == Incidencia.Estado.EN_PROCESO && incidencia.getFechaInicio() == null) {
                    incidencia.setFechaInicio(java.time.LocalDateTime.now());
                }
                // Si cambia a CERRADA y no tenía fecha fin, asignarla
                if (nuevoEstado == Incidencia.Estado.CERRADA && incidencia.getFechaFin() == null) {
                    incidencia.setFechaFin(java.time.LocalDateTime.now());
                    // Asignarse como técnico si no hay uno
                    if (incidencia.getTecnicoAsignado() == null) {
                        incidencia.setTecnicoAsignado(usuarioActual);
                    }
                }
                incidencia.setEstado(nuevoEstado);
            }
            // Cambia técnico asignado
            if (cbTecnico != null && cbTecnico.getSelectedItem() instanceof Usuario) {
                incidencia.setTecnicoAsignado((Usuario) cbTecnico.getSelectedItem());
            } else if (cbTecnico != null && cbTecnico.getSelectedItem().equals("-- Sin asignar --")) {
                incidencia.setTecnicoAsignado(null);
            }
        }
    }
    
    public Incidencia getIncidenciaModificada() {
        return modificado ? incidencia : null;
    }
}