/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import Modelo.entidades.Incidencia;
import Modelo.entidades.Usuario;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Portege Z930
 */
public class DAltaIncidencia extends JDialog {
    private Incidencia incidencia;
    private boolean aceptado = false;
    
    // Componentes de la interfaz
    private JTextField txtTitulo;
    private JTextArea txtDescripcion;
    private JComboBox<Incidencia.Prioridad> cbPrioridad;
    private JComboBox<Incidencia.Estado> cbEstado;
    
    public DAltaIncidencia(JFrame parent, Usuario autor) {
        super(parent, "Nueva Incidencia", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        initComponents(autor);
    }
    
    private void initComponents(Usuario autor) {
        // Panel principal con BorderLayout
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de campos con GridBagLayout para mejor control
        JPanel camposPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        
        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        camposPanel.add(new JLabel("Título:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1;
        txtTitulo = new JTextField(30);
        camposPanel.add(txtTitulo, gbc);
        
        // Descripción
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        camposPanel.add(new JLabel("Descripción:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        txtDescripcion = new JTextArea(5, 30);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        camposPanel.add(new JScrollPane(txtDescripcion), gbc);
        
        // Prioridad
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        camposPanel.add(new JLabel("Prioridad:"), gbc);
        
        gbc.gridx = 1;
        cbPrioridad = new JComboBox<>(Incidencia.Prioridad.values());
        cbPrioridad.setSelectedItem(Incidencia.Prioridad.MEDIA); // Valor por defecto
        camposPanel.add(cbPrioridad, gbc);
        
        // Estado - solo para técnicos/administradores
        Usuario.Perfil perfil = autor.getPerfil();
        if (perfil == Usuario.Perfil.TECNICO || perfil == Usuario.Perfil.ADMINISTRADOR) {
            gbc.gridx = 0;
            gbc.gridy = 3;
            camposPanel.add(new JLabel("Estado:"), gbc);
            
            gbc.gridx = 1;
            cbEstado = new JComboBox<>(Incidencia.Estado.values());
            cbEstado.setSelectedItem(Incidencia.Estado.PENDIENTE); // Valor por defecto
            camposPanel.add(cbEstado, gbc);
        } else {
            // Para usuarios normales, estado siempre PENDIENTE
            cbEstado = null;
        }
        
        // Panel de botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAceptar = new JButton("Crear Incidencia");
        JButton btnCancelar = new JButton("Cancelar");
        
        // Estilo para los botones
        btnAceptar.setBackground(new Color(70, 130, 180));
        btnAceptar.setForeground(Color.WHITE);
            // Acción del botón Aceptar
            btnAceptar.addActionListener(e -> {
                if (validarCampos()) {
                    crearIncidencia(autor);
                    aceptado = true;
                    dispose();
                }
            });
            // Acción del botón Cancelar
            btnCancelar.addActionListener(e -> cancelar());
        //añado los botones al panel    
        botonesPanel.add(btnCancelar);
        botonesPanel.add(btnAceptar);
        
        // Añado todo al panel principal
        panel.add(camposPanel, BorderLayout.CENTER);
        panel.add(botonesPanel, BorderLayout.SOUTH);
        
        add(panel);
        
        // Hago que el campo de título tenga el foco inicial
        txtTitulo.requestFocusInWindow();
        
        // Permite cerrar con ESC
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke("ESCAPE"), "cerrar");
        panel.getActionMap().put("cerrar", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                cancelar();
            }
        });
    }
    
    private boolean validarCampos() {
        // Validar título
        if (txtTitulo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "El título no puede estar vacío",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE);
            txtTitulo.requestFocus();
            return false;
        }
        
        // Validar descripción
        if (txtDescripcion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "La descripción no puede estar vacía",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE);
            txtDescripcion.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void crearIncidencia(Usuario autor) {
        // Crea la incidencia con los datos del formulario
        incidencia = new Incidencia(
            txtTitulo.getText().trim(),
            txtDescripcion.getText().trim(),
            autor,
            (Incidencia.Prioridad) cbPrioridad.getSelectedItem()
        );
        
        // Si el usuario puede elegir estado, asignarlo
        if (cbEstado != null) {
            incidencia.setEstado((Incidencia.Estado) cbEstado.getSelectedItem());
        } else {
            // Para usuarios normales, siempre PENDIENTE
            incidencia.setEstado(Incidencia.Estado.PENDIENTE);
        }
    }
    
    private void cancelar() {
        incidencia = null;
        dispose();
    }
    
    public Incidencia getIncidencia() {
        return aceptado ? incidencia : null;
    }
    
    public boolean isAceptado() {
        return aceptado;
    }
}