/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import Modelo.dao.UsuarioDao;
import Modelo.entidades.Usuario;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.prefs.Preferences;

/**
 * @author Portege Z930
 */

public class LoginFrame extends JFrame {
    //variables de clase o atributos que representan el estado de la ventana
    private JTextField txtEmail; //necesito leerlo en validarLogin()
    private JPasswordField txtPassword;
    private JComboBox<String> cbTipoCuenta;
    private JButton btnLogin, btnClose;
    private Usuario usuarioAutenticado;//lo paso a la ventana principal
    private JCheckBox chkRemember; //necesito saber si está marcado al autenticar
    private Preferences prefs; //se usa en varios métodos
    
    public LoginFrame() {
        setTitle("Login - Gestión de Incidencias");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 470); 
        setLocationRelativeTo(null); // centra la ventana en la pantalla
        setResizable(false); 
        
        //uso el Preferences porque no es un dato que debe estar en la base de datos, sino una preferencia local del usuario en el equipo, es una opción para recordar los datos
        prefs = Preferences.userRoot().node(this.getClass().getName());
        
        //método para crear y colocar los componentes
        initComponents();
    }
    
    //creando y colocando los componentes
    private void initComponents() {
        // Panel principal 
        JPanel logPanel = new JPanel(new GridBagLayout()); //GridBagLayout para formularios, flexible, y permite alinear todo perfectamente
        //añado el margen interno (padding) al panel por cada lado para que no quede pegado a los bordes
        logPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));//top, left, botton, right
        logPanel.setPreferredSize(new Dimension(400, 400)); //sugiere un tamaño preferido
        //indico donde y cómo coloco los componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); //define los márgentes externos dentro de la celda del grid
        gbc.fill = GridBagConstraints.HORIZONTAL; //se estira solo en ancho
        
        // Título
        JLabel lblTitulo = new JLabel("LOGIN", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(90, 185, 193));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 20, 0);
        logPanel.add(lblTitulo, gbc);
        
        // Etiqueta Tipo de cuenta
        JLabel lblSelectAccount = new JLabel("Selecciona el tipo de cuenta", SwingConstants.CENTER);
        lblSelectAccount.setFont(new Font("Arial", Font.BOLD, 16));
        lblSelectAccount.setForeground(new Color(70, 70, 70));
        
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        logPanel.add(lblSelectAccount, gbc);
        
        // Tipo de cuenta
        String[] tipos = {"USUARIO", "TECNICO", "ADMINISTRADOR"};
        cbTipoCuenta = new JComboBox<>(tipos);
        cbTipoCuenta.setFont(new Font("Arial", Font.PLAIN, 14));
        cbTipoCuenta.setPreferredSize(new Dimension(300, 35));
        cbTipoCuenta.setMaximumSize(new Dimension(300, 35));
        cbTipoCuenta.setMinimumSize(new Dimension(300, 35));
        
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        logPanel.add(cbTipoCuenta, gbc);
        
        // Separador visual , una línea visible 
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setPreferredSize(new Dimension(150, 2));
        
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 0, 20, 0);
        logPanel.add(separator, gbc);
        
        // Email
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 0, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 13));
        lblEmail.setPreferredSize(new Dimension(80, 25));
        logPanel.add(lblEmail, gbc);

        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        txtEmail.setPreferredSize(new Dimension(220, 35));
        txtEmail.setMaximumSize(new Dimension(220, 35));
        txtEmail.requestFocus();
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 0, 5, 0);
        logPanel.add(txtEmail, gbc);
        
        // Contraseña
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 5, 10);
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 13));
        lblPassword.setPreferredSize(new Dimension(80, 25));
        logPanel.add(lblPassword, gbc);
        
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPassword.setPreferredSize(new Dimension(220, 35));
        txtPassword.setMaximumSize(new Dimension(220, 35));
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 5, 0);
        logPanel.add(txtPassword, gbc);
        
        // Checkbox Remember me
        chkRemember = new JCheckBox("Recordar mis datos");
        chkRemember.setFont(new Font("Arial", Font.PLAIN, 12));
        chkRemember.setForeground(new Color(80, 80, 80));
        chkRemember.setOpaque(false);

        gbc.gridx = 0;
        gbc.gridy = 6;  
        gbc.gridwidth = 2; 
        gbc.anchor = GridBagConstraints.CENTER;  
        gbc.insets = new Insets(15, 0, 10, 0);
        logPanel.add(chkRemember, gbc);
        
        // Panel de botones 
        //el FlowLayout para colocar los componentes en fila
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));//centra los botones horizontalmente
        panelBotones.setPreferredSize(new Dimension(300, 50)); 
        panelBotones.setMaximumSize(new Dimension(300, 50));//evita que el panel no aumente en pantallas grandes
        panelBotones.setOpaque(false); //para que no pinte el fondo, deja ver el fondo del panel principal
        //defino el texto, el color de fondo del botón, la anchura y la altura del botón
        btnClose = crearBoton("Cerrar", new Color(241, 153, 94), 120, 40);
        btnLogin = crearBoton("Acceder", new Color(90, 185, 193), 120, 40);
        
        panelBotones.add(btnClose);
        panelBotones.add(btnLogin);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        logPanel.add(panelBotones, gbc);
        
        // Espaciador para empujar todo hacia arriba
        gbc.gridy = 7;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        //verticalGlue - espacio dinámico
        logPanel.add(Box.createVerticalGlue(), gbc); //componente invisible, crece verticalmente; el Glue no hace nada sin weighty
        
        // Añado el panel principal al frame
        add(logPanel);
        
        // Acciones de botones
        btnClose.addActionListener(e -> System.exit(0));
        btnLogin.addActionListener(e -> validarLogin());
        
        // Permite login con Enter en el campo de contraseña
        txtPassword.addActionListener(e -> validarLogin());
        
        // Cargar datos guardados (Remember me)
        //si existen datos, se cargan; si no existen, se usan valores vacíos
        String emailGuardado = prefs.get("email", ""); 
        String passwordGuardado = prefs.get("password", "");

        if (!emailGuardado.isEmpty()) {
            txtEmail.setText(emailGuardado);
            txtPassword.setText(passwordGuardado);
            chkRemember.setSelected(true);
        }
    }
    //método para la creación de los botones
    private JButton crearBoton(String texto, Color color, int ancho, int alto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Tamaño fijo del botón (definidos más arriba)
        Dimension size = new Dimension(ancho, alto);
        boton.setPreferredSize(size);
        boton.setMinimumSize(size);
        boton.setMaximumSize(size);
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(color.darker()); //para efecto claro 
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
            }
        }); 
        return boton;
    }
    
    private void validarLogin() {
        //lee los datos de la interfaz
        //se obtienen los valores escritos por el usuario en los campos del formulario
        String email = txtEmail.getText().trim(); //obtiene el texto de JTextField
        String password = new String(txtPassword.getPassword()); //getPassword devuelve un char, se convierte a String para poder compararlo y enviarlo al DAO
        String tipoSeleccionado = (String) cbTipoCuenta.getSelectedItem(); //se obtiene el valor seleccionado del JCombo, se castea a String porque getSelected devuelve Object
        
        // Validación básica antes de acceder a la base de datos
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, complete todos los campos", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Valida formato de email
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
            "Por favor, ingrese un email válido (ejemplo: usuario@dominio.com)",
            "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Autentica usuario usando el UsuarioDao
            UsuarioDao usuarioDao = new UsuarioDao(); //se crea el objeto DAO
            Usuario usuario = usuarioDao.autenticar(email, password); //se intenta autenticar el usuario
            if (usuario != null) {
                // Verifica que el perfil coincida con el seleccionado
                if (!usuario.getPerfil().name().equals(tipoSeleccionado)) {
                    JOptionPane.showMessageDialog(this,
                        "El tipo de cuenta seleccionado no coincide con el perfil del usuario.\n" +
                        "Usuario registrado como: " + usuario.getPerfil().name(),
                        "Error de autenticación",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Verifica que el usuario esté activo(uno inactivo no podría entrar)
                if (!usuario.isActivo()) { //aunque el usuario exista y la contraseña sea correcta, si no está activo, no puede usar la app
                    JOptionPane.showMessageDialog(this,
                        "El usuario está inactivo. Contacte al administrador.",
                        "Usuario inactivo",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // Login exitoso
                this.usuarioAutenticado = usuario;
                
                // Guardar o borrar credenciales según checkbox
                if (chkRemember.isSelected()) {
                    prefs.put("email", email);
                    prefs.put("password", password);
                } else {
                    prefs.remove("email");
                    prefs.remove("password");
                }
                
                // Muestra mensaje de bienvenida
                String nombreCompleto = usuario.getNombre() + " " + usuario.getApellidos();
                JOptionPane.showMessageDialog(this,
                    "¡Bienvenido, " + nombreCompleto + "!\n" +
                    "Perfil: " + usuario.getPerfil().name(),
                    "Login exitoso",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Abre ventana principal y pasa al usuario
                abrirVentanaPrincipal();
                this.dispose(); // Cierra ventana de login   
            } else {
                JOptionPane.showMessageDialog(this,
                    "Credenciales incorrectas. Por favor, verifique su email y contraseña.",
                    "Error de autenticación",
                    JOptionPane.ERROR_MESSAGE);
                //limpia solo la constraseña
                txtPassword.setText("");
                txtEmail.requestFocus();
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error de conexión a la base de datos: " + ex.getMessage(),
                "Error del sistema",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void abrirVentanaPrincipal() {
        // Crea y muestra la ventana principal
        FTaller_Principal mainFrame = new FTaller_Principal(usuarioAutenticado);
        mainFrame.setVisible(true);
    }
    //permite consultar qué usuario inició sesión, desde fuera del login
    public Usuario getUsuarioAutenticado() {
        //devuelve el usuario
        return usuarioAutenticado;
    }
}