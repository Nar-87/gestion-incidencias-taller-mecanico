/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import Modelo.dao.HistoricoResolucionDao;
import Modelo.dao.IncidenciaDao;
import Modelo.dao.UsuarioDao;
import Modelo.entidades.HistoricoResolucion;
import Modelo.entidades.Incidencia;
import Modelo.entidades.Usuario;
import static Modelo.entidades.Usuario.Perfil.ADMINISTRADOR;
import static Modelo.entidades.Usuario.Perfil.TECNICO;
import static Modelo.entidades.Usuario.Perfil.USUARIO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.JTextArea;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

/**
 * @author Portege Z930
 */

public class FTaller_Principal extends JFrame {
    /*declaro las variables y algunas de ellas las instancio aquí (siguiendo un poco el estilo de los programas hechos en clase)
    aunque, finalmente, el número de líneas se ha aumentado consideradamente. Intenté reorganizar y optimizar el programa en otro proyecto pero a estás horas de domingo ya no consigo*/
    
    //Variables componentes del menú
    JMenuBar menuBar = new JMenuBar();
    JMenu menuFile = new JMenu("File");
        JMenuItem miInicio = new JMenuItem("Inicio");
    JMenu menuIncidencias = new JMenu("Incidencias");
        JMenuItem miAlta = new JMenuItem("Alta");
        JMenuItem miModificar = new JMenuItem("Modificar");
        JMenuItem miSolucionar = new JMenuItem("Solucionar");
        JMenuItem miReabrir = new JMenuItem("Reabrir");
        JMenuItem miCerrar = new JMenuItem("Cerrar");
        JMenuItem miPrioridad = new JMenuItem("Prioridad");
        JMenuItem miDetalles = new JMenuItem("Detalles");
        JMenuItem miEliminar = new JMenuItem("Eliminar");
    JMenu menuUsuarios = new JMenu("Usuarios");
        JMenuItem miGestionUsuarios = new JMenuItem("Gestionar usuarios");
        JMenuItem miCrearUsuarios = new JMenuItem("Crear usuarios");
        JMenuItem miModificarUsuarios = new JMenuItem("Modificar usuarios");    
    JMenu menuInformes = new JMenu("Informes");
        JMenuItem miInciAbiertas = new JMenuItem("Incidencias abiertas");
        JMenuItem miInciCerradas = new JMenuItem("Incidencias cerradas");
        JMenuItem miInformeGeneral = new JMenuItem("Informe general");
    JMenu menuSalir = new JMenu("Salir");
        JMenuItem miSalirApp = new JMenuItem("Salir");
     
    //variables paneles
    JPanel pLateralIzq = new JPanel();
    JPanel pCentro = new JPanel();
    JPanel pUsuario = new JPanel();
    JPanel tablasInci = new JPanel();
    
    //botones panel lateral izquierdo
    JButton bLatIncidencias = new JButton("📁 Incidencias");
    JButton bLatTecnicos = new JButton("🛠 Técnicos");
    JButton bLatUsuarios = new JButton("👩‍👩‍ Usuarios");
    JButton bLatCategorias = new JButton("📂 Categorías");
    JButton bLatInforme = new JButton("📊 Informe");
    JButton bLatSalir = new JButton("Cerrar sesión");
    
    //variables panel usuario
    JLabel lImagenUser = new JLabel();
    JLabel lNombreUser = new JLabel();
    JLabel lEmailUser = new JLabel();
    //imagén del avatar
    ImageIcon iconoUsuario = new ImageIcon("src/imagenes/iconos/ic_user.jpg");

    //variable toolbar
    JToolBar tb = new JToolBar();
    
    //iconos toolbar
    /* aquí estoy siguiendo el mismo formato que en el programa hecho en clase*/
    int anchoboton = 35;
    int altoboton = 35;
    Image imgadd = new ImageIcon ("src/imagenes/iconos/ic_add.png").getImage();
    Image newimagadd = imgadd.getScaledInstance(anchoboton, altoboton, java.awt.Image.SCALE_SMOOTH);
    ImageIcon iconadd = new ImageIcon(newimagadd);
    
    Image imgdelete = new ImageIcon("src/imagenes/iconos/ic_edit.png").getImage();
    Image newimgdelete = imgdelete.getScaledInstance(anchoboton, altoboton, java.awt.Image.SCALE_SMOOTH);
    ImageIcon iconmodif = new ImageIcon(newimgdelete);
    
    Image imgsave = new ImageIcon ("src/imagenes/iconos/ic_check.png").getImage();
    Image newimagsave = imgsave.getScaledInstance(anchoboton, altoboton, java.awt.Image.SCALE_SMOOTH);
    ImageIcon iconsolu = new ImageIcon(newimagsave);
    
    Image imgprev = new ImageIcon ("src/imagenes/iconos/ic_refresh.png").getImage();
    Image newimagprev = imgprev.getScaledInstance(anchoboton, altoboton, java.awt.Image.SCALE_SMOOTH);
    ImageIcon iconreab = new ImageIcon(newimagprev);
    
    Image imgnext = new ImageIcon ("src/imagenes/iconos/ic_delete.png").getImage();
    Image newimagnext = imgnext.getScaledInstance(anchoboton, altoboton, java.awt.Image.SCALE_SMOOTH);
    ImageIcon iconeli = new ImageIcon(newimagnext);
    
    Image imgCerrar = new ImageIcon("src/imagenes/iconos/ic_close.png").getImage();
    Image newImgCerrar = imgCerrar.getScaledInstance(anchoboton, altoboton, java.awt.Image.SCALE_SMOOTH);
    ImageIcon iconCerrar = new ImageIcon(newImgCerrar);
    
    Image imgPriority = new ImageIcon("src/imagenes/iconos/ic_priority.png").getImage();
    Image newImgPriority = imgPriority.getScaledInstance(anchoboton, altoboton, java.awt.Image.SCALE_SMOOTH);
    ImageIcon iconPriority = new ImageIcon(newImgPriority);
    
    Image imgDetails = new ImageIcon("src/imagenes/iconos/ic_details.png").getImage();
    Image newImgDetails = imgDetails.getScaledInstance(anchoboton, altoboton, java.awt.Image.SCALE_SMOOTH);
    ImageIcon iconDetails = new ImageIcon(newImgDetails);
    //botones toolbar
    JButton bAlta = new JButton(iconadd);
    JButton bModificar = new JButton(iconmodif);
    JButton bSolucionar = new JButton(iconsolu);
    JButton bReabrir = new JButton(iconreab);
    JButton bEliminar = new JButton(iconeli);
    JButton bCerrar = new JButton(iconCerrar);
    JButton bPrioridad = new JButton(iconPriority);
    JButton bDetalles = new JButton(iconDetails);

    //Variables del panel de los filtros
    JPanel pFiltros = new JPanel();
    JLabel lEstadoFiltro = new JLabel("Estado");
    JComboBox cbEstadoFiltro = new JComboBox<>();
    JLabel lPrioridadFiltro = new JLabel("Prioridad");
    JComboBox cbPrioridadFiltro = new JComboBox<>();
    JLabel lTecnicoFiltro = new JLabel("Técnico");
    JComboBox cbTecnicoFiltro = new JComboBox<>();
    JLabel lBuscarFiltro = new JLabel("Buscar");
    JTextField tfBuscarFiltro = new JTextField();
    
    //Variables componentes de las pestañas
    JTabbedPane pestaniasInci = new JTabbedPane();
    JPanel panelInciAbiertas = new JPanel();
    JPanel panelInciCerradas = new JPanel();
    
    //Variables componentes tablas de incidencias abiertas y de incidencias cerradas
    JTable tablaInciAbiertas = new JTable();
    JScrollPane scrollAbiertas = new JScrollPane();
    
    JTable tablaInciCerradas = new JTable();
    JScrollPane scrollCerradas = new JScrollPane();
    
    /*modelos de datos que almacenan y gestionan la información que muestras las JTables
    (definen la estructura -columna- y el contenido -fila- de cada tabla)*/
    DefaultTableModel modeloAbiertas;
    DefaultTableModel modeloCerradas;
    
    //Variables componentes incidencias abiertas
    JLabel lIdInciAbierta = new JLabel();
    JLabel lTituloInciAbierta = new JLabel();
    JLabel lDescInciAbierta = new JLabel();
    JLabel lAutorInciAbierta = new JLabel();
    JLabel lPrioridadInciAbierta = new JLabel();
    JLabel lEstadoInciAbierta = new JLabel();
    JLabel lFechaAltaInciAbierta = new JLabel();
    JLabel lIdAnteriorInciAbierta = new JLabel();
    
    //Variables componentes incidencias cerradas 
    JLabel lIdIncidenciaCerradas = new JLabel();
    JLabel lTituloInciCerrada = new JLabel();
    JLabel lFechaAltaInciCerrada = new JLabel();
    JLabel lFechaFinInciCerrada = new JLabel();
    JLabel lTecnicoInciCerrada = new JLabel();
    JLabel lIdAnteriorInciCerrada = new JLabel();
    
    //Variables panel derecho    
    JPanel pDetalles = new JPanel();
    JLabel lDetalleId = new JLabel();
    JLabel lDetalleTitulo = new JLabel();
    JLabel lDetalleEstado = new JLabel();
    JLabel lDetallePrioridad = new JLabel();
    JLabel lDetalleTecnico = new JLabel();
    JTextArea txtDetHistorial = new JTextArea();
    
    //variable del objeto Usuario (el que inició la sesión)
    private Usuario usuarioActual;
    //variables para los botones del panel Izquierdo
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private ArrayList<Usuario> tecnicos = new ArrayList<>();

    // Frame constructor, recibe un usuario autenticado desde login
    public FTaller_Principal(Usuario usuario) {
        //guarda el usuario recibido en una variable de la clase para usarlo dentro de la ventana
        this.usuarioActual = usuario;
        configurarPermisos();//guarda el usuario y llama al método para habilitar/deshabilitar funciones según el perfil
        //le asigno el título en la barra superior de la ventana
        setTitle("Taller Mecánico");
//***** modificación en el tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //termina completamente la aplicación al cerrar la ventana
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizado por defecto
        setMinimumSize(new Dimension(1270, 600)); // Tamaño mínimo para que todo se vea bien
        setLayout(new BorderLayout());// establece el sistema de organización interna de la ventana en 5 zonas: norte, sur, este, oeste y centro
        // Añado todos los componentes, empiezo por la funcionalidad del menú
        setJMenuBar(menuBar);
        // Menú 
        menuBar.add(menuFile);
        menuBar.add(menuIncidencias);
        menuBar.add(menuUsuarios);
        menuBar.add(menuInformes);
        menuBar.add(menuSalir);
        // Menú File
        menuFile.add(miInicio);
        menuFile.setMnemonic('F');
        // Menú Incidencias
        menuIncidencias.add(miAlta);
        menuIncidencias.add(miModificar);
        menuIncidencias.add(miSolucionar);
        menuIncidencias.add(miReabrir);
        menuIncidencias.add(miCerrar);
        menuIncidencias.add(miPrioridad);
        menuIncidencias.add(miDetalles);
        menuIncidencias.add(miEliminar);
        menuIncidencias.setMnemonic('I');
        // Muenú Usuarios
        menuUsuarios.add(miGestionUsuarios);
        menuUsuarios.add(miCrearUsuarios);
        menuUsuarios.add(miModificarUsuarios);
        menuUsuarios.setMnemonic('U');
        // Menú Informes
        menuInformes.add(miInciAbiertas);
        menuInformes.add(miInciCerradas);
        menuInformes.add(miInformeGeneral);
        menuInformes.setMnemonic('n');
        // Menú Salir
        menuSalir.add(miSalirApp);
        menuSalir.setMnemonic('S');
        
        // Accelerators alternativos, los he buscado para que no tengan conflicto con otros del Sistema
        miAlta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK)); // Ctrl+N (Nueva)
        miModificar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK)); // Ctrl+E (Edit)
        miSolucionar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK)); // Ctrl+T (Trabajar)
        miReabrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK)); // Ctrl+A (Abrir)
        miCerrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK)); // Ctrl+X (eXit/cerrar)
        miSalirApp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK)); // Ctrl+Q (Quit)
        miInicio.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK)); // Ctrl+H (Home)
        miPrioridad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK)); // Ctrl+P (Priority)
        miDetalles.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK)); // Ctrl+D (Details)
        miEliminar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0)); // Delete (eliminar)
        miGestionUsuarios.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK)); // Ctrl+G (Gestionar)
        miCrearUsuarios.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK)); // Ctrl+U (User)
        miModificarUsuarios.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK)); // Ctrl+M (Modify)
        miInciAbiertas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_DOWN_MASK)); // Ctrl+1 (Reporte 1)
        miInciCerradas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_DOWN_MASK)); // Ctrl+2 (Reporte 2)
        miInformeGeneral.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, InputEvent.CTRL_DOWN_MASK)); // Ctrl+3 (Reporte 3)
        
//conexión del Menú File (Inicio)- no tiene mucho sentido porque no hay varias páginas
        miInicio.addActionListener(e -> {
            cargarIncidenciasAbiertas(); 
            cargarIncidenciasCerradas();
            pestaniasInci.setSelectedIndex(0); 
            JOptionPane.showMessageDialog(this, 
                "Volviendo al inicio...", 
                "Inicio", 
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        //conexión del menú Incidencias llamando a todos los métodos usados en la sección 
        miAlta.addActionListener(e -> altaInci());
        miModificar.addActionListener(e -> modificarInci());
        miSolucionar.addActionListener(e -> solucionarInci());
        miReabrir.addActionListener(e -> reabrirInci());
        miCerrar.addActionListener(e -> cerrarIncidencia());
        miPrioridad.addActionListener(e -> cambiarPrioridadIncidencia());
        miDetalles.addActionListener(e -> mostrarDetallesCompletos());
        miEliminar.addActionListener(e -> eliminarInci());
        
        //conexión del menú Usuarios
        // ---pendiente de:--- Gestionar Usuarios; Crear Usuarios; Modificar Usuarios
       
        //conexión del menú Informe
        miInciAbiertas.addActionListener(e -> abrirDialogoInformes("ABIERTAS"));
        miInciCerradas.addActionListener(e -> abrirDialogoInformes("CERRADAS"));
        miInformeGeneral.addActionListener(e -> abrirDialogoInformes("GENERAL"));
      
        //conexión del menú Salir
        miSalirApp.addActionListener(e -> confirmarSalida());
        
        // Layout principal - establece que el panel principal de la ventana use BorderLayout
        getContentPane().setLayout(new BorderLayout());

        // Panel lateral izquierdo
        pLateralIzq = new JPanel(new GridBagLayout()); // GridBag permite posicionar componentes con gran precisión
        pLateralIzq.setPreferredSize(new Dimension(200, getHeight())); //---responsivo
        pLateralIzq.setMinimumSize(new Dimension(180, 0)); // Ancho mínimo
        pLateralIzq.setMaximumSize(new Dimension(250, Integer.MAX_VALUE)); // Ancho máximo
        pLateralIzq.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5)); //margenes internos de cada componente
        //constraints para poder definir los componentes
        GridBagConstraints gbcLatIzq = new GridBagConstraints();
        gbcLatIzq.insets = new Insets(0, 5, 7, 5); //margenes externos de cada componente
        gbcLatIzq.gridx = 0;//posición 0 de la columna donde se colocará el componente
        gbcLatIzq.fill = GridBagConstraints.HORIZONTAL; //expansión del componente dentro de su celda
        gbcLatIzq.anchor = GridBagConstraints.NORTH;//alineación del componente dentro de la celda

        // Panel Usuario
        pUsuario = new JPanel(new GridBagLayout()); //se crea un nuevo panel que usará un GridBagLayout
        pUsuario.setBackground(new Color(230, 230, 230));//gris
        pUsuario.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        //definir cómo se colocan los componentes dentro del GridBagLayout
        GridBagConstraints gbcUser = new GridBagConstraints();
        gbcUser.insets = new Insets(5, 5, 5, 5);
        gbcUser.gridx = 0;
        gbcUser.fill = GridBagConstraints.VERTICAL;

        // Avatar     
        lImagenUser = new JLabel(iconoUsuario, SwingConstants.CENTER);//crea una etiqueta que muestra una imagen, centrando la imagen dentro del label
        lImagenUser.setPreferredSize(new Dimension(150, 150));
        lImagenUser.setOpaque(true);
        lImagenUser.setBackground(new Color(230, 230, 230)); //gris
        lImagenUser.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        gbcUser.gridy = 0;
        pUsuario.add(lImagenUser, gbcUser);

        // Nombre
        lNombreUser = new JLabel(usuario.getNombre() + " " + usuario.getApellidos(), SwingConstants.CENTER);//crea una etiqueta con el nombre concatenado con el apellido con un espacio entre ellos
        gbcUser.gridy = 1;
        pUsuario.add(lNombreUser, gbcUser);

        // Email
        lEmailUser = new JLabel(usuario.getEmail(), SwingConstants.CENTER);
        lEmailUser.setFont(new Font("Arial", Font.PLAIN, 12));
        gbcUser.gridy = 2;
        pUsuario.add(lEmailUser, gbcUser);

        // Añadir panel usuario al panel lateral
        gbcLatIzq.gridy = 0; //fila 0
        gbcLatIzq.weighty = 0;
        pLateralIzq.add(pUsuario, gbcLatIzq);
        
        gbcLatIzq.gridy = 1;//espaciador
        pLateralIzq.add(Box.createVerticalStrut(10), gbcLatIzq);//crea un componente invisible de 10 px de alto, tipo espaciador o un <br>

        // Botones laterales
        //creo un tamaño estándar para todos los botones del panel izq
        Dimension sideBtnSize = new Dimension(150, 40);//ancho , alto
        //los pongo en una lista para tratarlos a todos igual
        JButton[] btns = { bLatIncidencias, bLatTecnicos, bLatUsuarios, bLatCategorias, bLatInforme };
        //configuración de cada botón
        for (JButton b : btns) {
            b.setPreferredSize(sideBtnSize); //tamaño estandar
            b.setFocusable(false);//sin foco
            b.setHorizontalAlignment(SwingConstants.CENTER);//texto del botón centrado horizontalmente
            b.setBackground(Color.WHITE);
            b.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        }
        //colocando los botones en el panel
        gbcLatIzq.weighty = 0;//no se estiran verticalmente
        gbcLatIzq.gridy = 2; //el primero botón va en la fila 2
        pLateralIzq.add(bLatIncidencias, gbcLatIzq);
        gbcLatIzq.gridy = 3; 
        pLateralIzq.add(bLatTecnicos, gbcLatIzq);
        gbcLatIzq.gridy = 4; 
        pLateralIzq.add(bLatUsuarios, gbcLatIzq);
        gbcLatIzq.gridy = 5; 
        pLateralIzq.add(bLatCategorias, gbcLatIzq);
        gbcLatIzq.gridy = 6; 
        pLateralIzq.add(bLatInforme, gbcLatIzq);
        
        //accciones de los botones del panel izquierdo
        //botón Incidencias
        bLatIncidencias.addActionListener(e -> {
            try {
                IncidenciaDao incidenciaDao = new IncidenciaDao();
                //lista con todas las incidencias que se mostrarán
                List<Incidencia> todas = new ArrayList<>();
                if (usuarioActual.getPerfil() == Usuario.Perfil.USUARIO) {
                    // Usuario ve solo sus incidencias
                    //si es usuario normal: obtén la lista de incidencias que pertenecen a este usuario
                    List<Incidencia> propias =
                        incidenciaDao.obtenerIncidenciasPorUsuario(usuarioActual.getIdUsuario());
                    //añade todas esas incidencias a la lista general que se mostrará
                    todas.addAll(propias);
                } else {
                    // Técnico/Admin ve todas las incidencias
                    todas.addAll(incidenciaDao.obtenerIncidenciasAbiertas());
                    todas.addAll(incidenciaDao.obtenerIncidenciasCerradas());
                }
                //creando la tabla con las incidencias
                String[] columnas = { "ID", "Título", "Estado", "Prioridad", "Técnico", "Fecha" };
                //matriz bidimensional con tantas filas como incidencias tenga, y 6 columnas
                Object[][] datos = new Object[todas.size()][columnas.length];

                for (int i = 0; i < todas.size(); i++) {
                    Incidencia in = todas.get(i);
                    datos[i][0] = in.getIdIncidencia();
                    datos[i][1] = in.getTitulo();
                    datos[i][2] = in.getEstado();
                    datos[i][3] = in.getPrioridad();
                    datos[i][4] = in.getTecnicoAsignado() != null
                            ? in.getTecnicoAsignado().getNombre()
                            : "Sin asignar";
                    datos[i][5] = formatearFecha(in.getFechaAlta());
                }

                JTable tabla = new JTable(datos, columnas);
                tabla.setEnabled(false); //solo lectura, no se podrá editar
                tabla.setAutoCreateRowSorter(true);//el usuario podrá ordenar las columnas haciendo clic en los encabezados
                //añado un scroll por si hay muchas incidencias
                JScrollPane scroll = new JScrollPane(tabla);
                scroll.setPreferredSize(new Dimension(800, 400));
                //mostrar la ventana de incidencias
                JOptionPane.showMessageDialog(
                    FTaller_Principal.this,
                    scroll,
                    "Listado de incidencias",
                    JOptionPane.INFORMATION_MESSAGE
                );

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    FTaller_Principal.this,
                    "Error al cargar incidencias:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        //botón Técnicos
        bLatTecnicos.addActionListener(e -> {
            UsuarioDao usuarioDao = new UsuarioDao();
            try {
                java.util.List<Usuario> tecnicos = usuarioDao.obtenerTecnicos();
                String[] columnas = {"Nombre", "Apellidos", "Email", "Perfil", "Estado", "Activo"};
                Object[][] datos = new Object[tecnicos.size()][6];
                for (int i = 0; i < tecnicos.size(); i++) {
                    Usuario t = tecnicos.get(i);
                    datos[i][0] = t.getNombre();
                    datos[i][1] = t.getApellidos();
                    datos[i][2] = t.getEmail();
                    datos[i][3] = t.getPerfil();
                    datos[i][4] = t.isActivo() ? "🟢 Online" : "🔴 Offline";
                }
                JTable tablaTecnicos = new JTable(datos, columnas);
                tablaTecnicos.setEnabled(false);
                tablaTecnicos.setAutoCreateRowSorter(true);

                JScrollPane scroll = new JScrollPane(tablaTecnicos);
                scroll.setPreferredSize(new Dimension(600, 300));

                JOptionPane.showMessageDialog(
                    FTaller_Principal.this,
                    scroll,
                    "Listado de Técnicos",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    FTaller_Principal.this,
                    "Error al obtener técnicos: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
        //botón Usuario
        bLatUsuarios.addActionListener(e -> {
            try {
                List<Usuario> usuarios = new UsuarioDao().obtenerUsuariosNormales();
                // Crea JTable para mostrar usuarios
                String[] columnas = {"Nombre", "Apellidos", "Email", "Perfil", "Estado"};
                Object[][] datos = new Object[usuarios.size()][5];

                for (int i = 0; i < usuarios.size(); i++) {
                    Usuario u = usuarios.get(i);
                    datos[i][0] = u.getNombre();
                    datos[i][1] = u.getApellidos();
                    datos[i][2] = u.getEmail();
                    datos[i][3] = u.getPerfil();
                    datos[i][4] = u.isActivo() ? "🟢 Activo" : "🔴 Inactivo";
                }

                JTable tablaUsuarios = new JTable(datos, columnas);
                tablaUsuarios.setEnabled(false);
                JScrollPane scroll = new JScrollPane(tablaUsuarios);
                scroll.setPreferredSize(new Dimension(500, 300));
                JOptionPane.showMessageDialog(
                    FTaller_Principal.this,
                    scroll,
                    "Usuarios",
                    JOptionPane.INFORMATION_MESSAGE
                );

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                    FTaller_Principal.this,
                    "Error al obtener usuarios: " + ex.getMessage(),
                    "Error BD",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
        // Botón Categorías
        bLatCategorias.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                FTaller_Principal.this,
                "Aquí se podrían gestionar las categorías de incidencias.",
                "Categorías",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        // Botón Informe
        bLatInforme.addActionListener(e -> abrirDialogoInformes()); 
        
        // Espaciador
        gbcLatIzq.gridy = 7;
        gbcLatIzq.weighty = 1;
        //componente invisible, crece verticalmente; el Glue no hace nada sin weighty
        pLateralIzq.add(Box.createVerticalGlue(), gbcLatIzq);

        // Botón Salir/Cerrar sesión
        gbcLatIzq.gridy = 8;
        gbcLatIzq.weighty = 0;
        bLatSalir.setPreferredSize(new Dimension(160, 30));
        bLatSalir.setBackground(new Color(241, 153, 94));
        bLatSalir.setForeground(Color.WHITE);
        bLatSalir.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        bLatSalir.addActionListener(e -> logout()); //llamo al método creado abajo de todo en la clase
//        {        
//            int respuesta = JOptionPane.showConfirmDialog(
//                FTaller_Principal.this,
//                "¿Está seguro que desea salir?",
//                "Confirmar salida",
//                JOptionPane.YES_NO_OPTION
//            );
//            if (respuesta == JOptionPane.YES_OPTION) {
//                System.exit(0);
//            }
//        });
        pLateralIzq.add(bLatSalir, gbcLatIzq);

        // Añado panel lateral a la izquierda
        getContentPane().add(pLateralIzq, BorderLayout.WEST);

        // Panel central principal
        // BorderLayout para organizar toolbar, filtros y tablas
        JPanel panelCentralPrincipal = new JPanel(new BorderLayout());

        // --- Toolbar ---
        //Dimension btnSize = new Dimension(60, 60);
        tb.setFloatable(false);
        tb.setBackground(new Color(245, 245, 245));//gris claro
        tb.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        tb.setRollover(true);//(efecto)el botón cambia de color cuando se pasa el raton por encima

        // Configurar botones de toolbar
        bAlta.setText("Alta"); 
        bAlta.setPreferredSize(new Dimension(60, 60));
        bAlta.setMinimumSize(new Dimension(50, 50));
        bAlta.setMaximumSize(new Dimension(90, 70));
        bAlta.setToolTipText("Crear nueva incidencia");
        bAlta.setHorizontalTextPosition(SwingConstants.CENTER);
        bAlta.setVerticalTextPosition(SwingConstants.BOTTOM);
        //bAlta.setPreferredSize(btnSize);
        bAlta.setFocusable(false);
            bAlta.addActionListener(e -> altaInci());

        bModificar.setText("Modificar"); 
        bModificar.setPreferredSize(new Dimension(60, 60));
        bModificar.setMinimumSize(new Dimension(50, 50));
        bModificar.setMaximumSize(new Dimension(90, 70));
        bModificar.setToolTipText("Modificar incidencia seleccionada");
        bModificar.setHorizontalTextPosition(SwingConstants.CENTER);
        bModificar.setVerticalTextPosition(SwingConstants.BOTTOM);
        //bModificar.setPreferredSize(btnSize);
        bModificar.setFocusable(false);
            bModificar.addActionListener(e -> modificarInci());

        bSolucionar.setText("Solucionar"); 
        bSolucionar.setPreferredSize(new Dimension(60, 60));
        bSolucionar.setMinimumSize(new Dimension(50, 50));
        bSolucionar.setMaximumSize(new Dimension(90, 70));
        bSolucionar.setToolTipText("Marcar incidencia como solucionada");
        bSolucionar.setHorizontalTextPosition(SwingConstants.CENTER);
        bSolucionar.setVerticalTextPosition(SwingConstants.BOTTOM);
        //bSolucionar.setPreferredSize(btnSize);
        bSolucionar.setFocusable(false);
            bSolucionar.addActionListener(e -> solucionarInci());

        bReabrir.setText("Reabrir"); 
        bReabrir.setPreferredSize(new Dimension(60, 60));
        bReabrir.setMinimumSize(new Dimension(50, 50));
        bReabrir.setMaximumSize(new Dimension(90, 70));
        bReabrir.setToolTipText("Reabrir incidencia cerrada");
        bReabrir.setHorizontalTextPosition(SwingConstants.CENTER);
        bReabrir.setVerticalTextPosition(SwingConstants.BOTTOM);
        //bReabrir.setPreferredSize(btnSize);
        bReabrir.setFocusable(false);
            bReabrir.addActionListener(e -> reabrirInci());
            
        bCerrar.setText("Cerrar");
        bCerrar.setPreferredSize(new Dimension(60, 60));
        bCerrar.setMinimumSize(new Dimension(50, 50));
        bCerrar.setMaximumSize(new Dimension(90, 70));
        bCerrar.setToolTipText("Cerrar incidencia");
        bCerrar.setHorizontalTextPosition(SwingConstants.CENTER);
        bCerrar.setVerticalTextPosition(SwingConstants.BOTTOM);
        bCerrar.setFocusable(false);
            bCerrar.addActionListener(e -> cerrarIncidencia()); 
            
        bPrioridad.setText("Prioridad");
        bPrioridad.setPreferredSize(new Dimension(60, 60));
        bPrioridad.setMinimumSize(new Dimension(50, 50));
        bPrioridad.setMaximumSize(new Dimension(90, 70));
        bPrioridad.setToolTipText("Cambiar prioridad de la incidencia");
        bPrioridad.setHorizontalTextPosition(SwingConstants.CENTER);
        bPrioridad.setVerticalTextPosition(SwingConstants.BOTTOM);
        bPrioridad.setFocusable(false);
            bPrioridad.addActionListener(e -> cambiarPrioridadIncidencia());

        bDetalles.setText("Detalles");
        bDetalles.setPreferredSize(new Dimension(60, 60));
        bDetalles.setMinimumSize(new Dimension(50, 50));
        bDetalles.setMaximumSize(new Dimension(90, 70));
        bDetalles.setToolTipText("Ver detalles completos e histórico");
        bDetalles.setHorizontalTextPosition(SwingConstants.CENTER);
        bDetalles.setVerticalTextPosition(SwingConstants.BOTTOM);
        bDetalles.setFocusable(false);
            bDetalles.addActionListener(e -> mostrarDetallesCompletos());
  
        bEliminar.setText("Eliminar"); 
        bEliminar.setPreferredSize(new Dimension(60, 60));
        bEliminar.setMinimumSize(new Dimension(50, 50));
        bEliminar.setMaximumSize(new Dimension(90, 70));
        bEliminar.setToolTipText("Eliminar incidencia seleccionada");
        bEliminar.setHorizontalTextPosition(SwingConstants.CENTER);
        bEliminar.setVerticalTextPosition(SwingConstants.BOTTOM);
        //bEliminar.setPreferredSize(btnSize);
        bEliminar.setFocusable(false);
            bEliminar.addActionListener(e -> eliminarInci());

        // Añado botones al toolbar
        tb.add(bAlta);
        tb.add(bModificar);
        tb.addSeparator(new Dimension(15, 30)); //agregando espacio
        tb.add(bSolucionar);
        tb.add(bReabrir);
        tb.add(bCerrar);
        tb.addSeparator(new Dimension(15, 30));
        tb.add(bPrioridad);
        tb.add(bDetalles);
        tb.addSeparator(new Dimension(15, 30));
        tb.add(bEliminar);

        // Añado toolbar al norte del panel central
        panelCentralPrincipal.add(tb, BorderLayout.NORTH);

        // --- Panel de filtros ---
        pFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5)); //FlowLayout -- en fila
        pFiltros.setBackground(new Color(245, 245, 245));//gris claro
        pFiltros.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Añado filtros en línea horizontal
        lEstadoFiltro = new JLabel("Estado:");
        pFiltros.add(lEstadoFiltro);

        cbEstadoFiltro = new JComboBox<>(new String[]{"Todos", "Pendiente", "En proceso", "Espera"});
        cbEstadoFiltro.setPreferredSize(new Dimension(120, 25));
        pFiltros.add(cbEstadoFiltro);
            cbEstadoFiltro.addActionListener(e -> filtrarTabla());
        pFiltros.add(new JLabel("   ")); // Espaciador

        lPrioridadFiltro = new JLabel("Prioridad:");
        pFiltros.add(lPrioridadFiltro);

        cbPrioridadFiltro = new JComboBox<>(new String[]{"Todos", "Baja", "Media", "Alta"});
        cbPrioridadFiltro.setPreferredSize(new Dimension(120, 25));
        pFiltros.add(cbPrioridadFiltro);
            cbPrioridadFiltro.addActionListener(e -> filtrarTabla());
            
        pFiltros.add(new JLabel("   ")); // Espaciador

        lTecnicoFiltro = new JLabel("Técnico:");
        pFiltros.add(lTecnicoFiltro);

        cbTecnicoFiltro = new JComboBox<>(new String[]{"Todos", "Técnico 1", "Técnico 2"});
        cbTecnicoFiltro.setPreferredSize(new Dimension(120, 25));
        pFiltros.add(cbTecnicoFiltro);
            cbTecnicoFiltro.addActionListener(e -> filtrarTabla());

        pFiltros.add(new JLabel("   ")); // Espaciador

        lBuscarFiltro = new JLabel("Buscar:");
        pFiltros.add(lBuscarFiltro);

        tfBuscarFiltro = new JTextField(15);
        pFiltros.add(tfBuscarFiltro);
        tfBuscarFiltro.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filtrarTabla(); }
            @Override public void removeUpdate(DocumentEvent e) { filtrarTabla(); }
            @Override public void changedUpdate(DocumentEvent e) { filtrarTabla(); }
        });

        // Añadir filtros al centro del panel central (debajo del toolbar)
        panelCentralPrincipal.add(pFiltros, BorderLayout.CENTER);

        // --- Pestañas de incidencias ---
        // Creo las pestañas primero
        pestaniasInci = new JTabbedPane();
        pestaniasInci.addTab("Incidencias Abiertas", panelInciAbiertas());
        pestaniasInci.addTab("Incidencias Cerradas", panelInciCerradas());
        pestaniasInci.setFont(new Font("Arial", Font.PLAIN, 14));

        // --- Panel de detalles derecha ---
        //Creo el panel de detalles 
        pDetalles = new JPanel(new BorderLayout()); //el panel usará el diseño BorderLayout para organizar los componentes internos en 5 áreas
        pDetalles.setBackground(new Color(245, 245, 245));//gris claro
        pDetalles.setPreferredSize(new Dimension(300, getHeight())); //---responsivo (300 ancho, toda la altura de la ventana)
        pDetalles.setMinimumSize(new Dimension(250, 0)); // Ancho mínimo
        pDetalles.setMaximumSize(new Dimension(400, Integer.MAX_VALUE)); // Ancho máximo de 400 pero la altura, sin limite(lo de Integer)
        pDetalles.setBorder(BorderFactory.createTitledBorder("Detalles de Incidencia"));//borde con el título incluido
        
        // Título del panel de detalles
        JLabel tituloDetalles = new JLabel("Detalles de Incidencia", SwingConstants.CENTER);
        tituloDetalles.setFont(new Font("Arial", Font.BOLD, 14));
        tituloDetalles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pDetalles.add(tituloDetalles, BorderLayout.NORTH);

        // Mensaje inicial
        JLabel lblInicial = new JLabel("Seleccione una incidencia", SwingConstants.CENTER);
        lblInicial.setFont(new Font("Arial", Font.ITALIC, 12));
        lblInicial.setForeground(Color.GRAY);
        pDetalles.add(lblInicial, BorderLayout.CENTER);

        // Creo otro panel para contenido de tablas y detalles
        JPanel panelTablasYDetalles = new JPanel(new BorderLayout());
        panelTablasYDetalles.add(pestaniasInci, BorderLayout.CENTER);
        panelTablasYDetalles.add(pDetalles, BorderLayout.EAST);

        // Añado las tablas más los detalles al panel central
        panelCentralPrincipal.add(panelTablasYDetalles, BorderLayout.SOUTH);

        // Añado panel central principal al frame
        getContentPane().add(panelCentralPrincipal, BorderLayout.CENTER);

        // da un tamaño al frame -- aunque al usar el setSize del comienzo del constructor algo está impidiendo el cambio de tamaño... no sé si el Bounds
        pack(); 
        setLocationRelativeTo(null); //Método estándar para centrar
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza
//      Dimension screenSize = 
//      Toolkit.getDefaultToolkit().getScreenSize();
//      setBounds((int) (0.5*(screenSize.width - 
//      getWidth())), (int)(0.5*(screenSize.height - getHeight())), (int) 
//      getWidth(), (int) getHeight()); 

        //configura los Listeners para las tablas
        configurarListenersSeleccion();    
        // Cargar datos
        cargarIncidenciasAbiertas();
        cargarIncidenciasCerradas();
        //cargar colores de los perfiles
        configurarColoresPorPerfil();
    }
    
    //creando la pestaña de Incidencias Abiertas
    public JPanel panelInciAbiertas() {
        // Panel principal de las incidencias abiertas
        panelInciAbiertas = new JPanel(new BorderLayout(10, 10));//con espacio interno entre componentes
        panelInciAbiertas.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        panelInciAbiertas.setBackground(new Color(250, 250, 250));//casi blanco
        // Columnas del modelo de datos
        String[] columnasAbiertas = {"ID", "Título", "Descripción", "Autor", "Prioridad", "Estado", "Fecha Alta", "ID Anterior"};
        //creo la estructura de datos para la tabla, solo para leer
        modeloAbiertas = new DefaultTableModel(columnasAbiertas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No editable
            }
        };
//************** Tabla con colores según prioridad y estado
        tablaInciAbiertas = new JTable(modeloAbiertas) {
            //las columnas 0 y 7 contendrán números, todas las demás contendrán texto
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0 || column == 7) return Integer.class;
                return String.class;
            }
            //personalizar cómo se ve cada celda de la tabla
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (isRowSelected(row)) {
                    c.setForeground(Color.WHITE); // Texto blanco si está seleccionada la fila
                } else {
                    // Colorear columna Prioridad (4)
                    if (column == 4) {
                        //mirando el valor que tiene, se cambia el color
                        String valor = String.valueOf(getValueAt(row, column));
                        switch (valor.toUpperCase()) {
                            case "BAJA":  c.setForeground(new Color(34, 139, 34)); break;   // verde
                            case "MEDIA": c.setForeground(new Color(255, 140, 0)); break;   // naranja
                            case "ALTA":  c.setForeground(Color.RED); break; 
                            default: c.setForeground(Color.BLACK);
                        }
                    }
                    // Colorear columna Estado (5)
                    else if (column == 5) {
                        //en función del valor que tiene, se cambia el color
                        String valor = String.valueOf(getValueAt(row, column));
                        switch (valor.toUpperCase()) {
                            case "PENDIENTE": c.setForeground(new Color(255, 140, 0)); break; // naranja
                            case "EN PROCESO": c.setForeground(new Color(30, 144, 255)); break; // azul
                            case "ESPERA": c.setForeground(Color.GRAY); break;
                            default: c.setForeground(Color.BLACK);
                        }
                    } else {
                        c.setForeground(Color.BLACK); // Resto de celdas
                    }
                }
                return c; //al terminar de personalizar la celda, la devuelvo para que se muestre
            }
        };
        // Propiedades de la tabla
        tablaInciAbiertas.setFillsViewportHeight(true);//ocupa toda la altura disponible
        tablaInciAbiertas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//configuración para que se pueda seleccionar una fila a la vez, no varias
        tablaInciAbiertas.getTableHeader().setReorderingAllowed(false);//desactivo que el usuario pueda arrastrar y reordenar las columnas
        // Ajustar ancho de columnas
        int[] anchos = {50, 150, 250, 100, 80, 100, 120, 80};
        for (int i = 0; i < anchos.length; i++) {
            tablaInciAbiertas.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        // Altura de fila
        tablaInciAbiertas.setRowHeight(25);
        // Tooltips para columna descripción
        //cuando se pasa el ratón sobre la columna 2, aprezca un cuadro de info mostrando todo el contenido
        tablaInciAbiertas.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                int row = tablaInciAbiertas.rowAtPoint(e.getPoint());
                int col = tablaInciAbiertas.columnAtPoint(e.getPoint());
                if (row >= 0 && col == 2) {
                    Object value = modeloAbiertas.getValueAt(row, col);
                    tablaInciAbiertas.setToolTipText(value != null ? value.toString() : null);
                } else {
                    tablaInciAbiertas.setToolTipText(null);
                }
            }
        });
        // Click para mostrar detalles
        //cuando se hace clic en cualquier fila de la tabla que muestre la información completa en el panel derecho
        tablaInciAbiertas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tablaInciAbiertas.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    Object idObj = modeloAbiertas.getValueAt(row, 0);
                    if (idObj != null) {
                        mostrarDetallesIncidencia((int) idObj);
                    }
                }
            }
        });
        // Scroll , si hay muchas incidencias
        JScrollPane scrollAbiertas = new JScrollPane(tablaInciAbiertas);
        panelInciAbiertas.add(scrollAbiertas, BorderLayout.CENTER);

        return panelInciAbiertas; //devuelve el panel completamente construido para que se pueda usar
    }
 
    //creando la pestaña de incidencias cerradas
    public JPanel panelInciCerradas() {
        //panel principal de las incidencias cerradas
        panelInciCerradas = new JPanel(new BorderLayout(10, 10));
        panelInciCerradas.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        panelInciCerradas.setBackground((new Color(250, 250, 250)));
        //modelo tabla
        String[] columnasCerradas = {"ID", "Título", "Fecha Alta", "Fecha Fin", "Técnico", "Id Anterior"};
        modeloCerradas = new DefaultTableModel(columnasCerradas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabla no editable 
            }
            @Override
            public Class<?> getColumnClass(int column) {
                // Para que las columnas IDs se manejen como Integer
                if (column == 0 || column == 5) return Integer.class;
                return String.class;
            }
        };  
        //configurando la tabla
//**********añadido colores a los estados de la tabla        
        tablaInciCerradas = new JTable(modeloCerradas);
        tablaInciCerradas.setFillsViewportHeight(true);//ocupa toda la altura disponible
        tablaInciCerradas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//configurado para que se pueda seleccionar solo una fila, no varias a la vez
        tablaInciCerradas.getTableHeader().setReorderingAllowed(false);//desactivo que el usuario pueda arrastrar y reordenar las columnas
        tablaInciCerradas.setRowHeight(25);  //altura de 25px     
//***** Tooltips para columnas largas, en este caso el Título
        tablaInciCerradas.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                int row = tablaInciCerradas.rowAtPoint(e.getPoint());
                int col = tablaInciCerradas.columnAtPoint(e.getPoint());
                if (row >= 0 && col == 1) {
                    Object value = modeloCerradas.getValueAt(row, col);
                    tablaInciCerradas.setToolTipText(value != null ? value.toString() : null);
                } else {
                    tablaInciCerradas.setToolTipText(null);
                }
            }
        });
        // Añado MouseListener para clic en la tabla
        tablaInciCerradas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tablaInciCerradas.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    Object idObj = modeloCerradas.getValueAt(row, 0);
                    if (idObj != null) {
                        mostrarDetallesIncidencia((int) idObj);
                    }
                }
            }
        });
        // Ajusta ancho de columnas
        int[] anchos = {50, 150, 120, 120, 100, 80};
        for (int i = 0; i < anchos.length; i++) {
            tablaInciCerradas.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        //Scroll
        scrollCerradas.setViewportView(tablaInciCerradas);
        panelInciCerradas.add(scrollCerradas, BorderLayout.CENTER);

        return panelInciCerradas;
    }
    
    // Método para configurar permisos según el perfil 
    private void configurarPermisos() {
        if (usuarioActual == null) return; //si no hay usuario loguado, salgo del método
        //pido al usuario actual que diga qué tipo de perfil tiene y se guarda esa información e una variable llamada perfil
        Usuario.Perfil perfil = usuarioActual.getPerfil();
        switch (perfil) {
            case USUARIO:
                // Usuarios pueden crear, modificar y ver detalles e historico de sus incidencias
                bAlta.setEnabled(true);
                miAlta.setEnabled(true);
                bModificar.setEnabled(true); 
                miModificar.setEnabled(true);
                bReabrir.setEnabled(true);
                miReabrir.setEnabled(true);
                bDetalles.setEnabled(true);
                miDetalles.setEnabled(true);
                // Pero NO pueden solucionar, eliminar o gestionar usuarios
                bSolucionar.setEnabled(false);
                miSolucionar.setEnabled(false);
                bEliminar.setEnabled(false);
                miEliminar.setEnabled(false);
                bCerrar.setEnabled(false);
                miCerrar.setEnabled(false);
                bPrioridad.setEnabled(false);
                miPrioridad.setEnabled(false);
                miSolucionar.setEnabled(false);
                miGestionUsuarios.setEnabled(false);
                miCrearUsuarios.setEnabled(false);
                miModificarUsuarios.setEnabled(false);
                bLatTecnicos.setEnabled(false);
                bLatUsuarios.setEnabled(false);
                break;

            case TECNICO:
                // Técnicos pueden TODO menos gestionar usuarios
                bModificar.setEnabled(true);
                bSolucionar.setEnabled(true);
                bReabrir.setEnabled(true);
                bEliminar.setEnabled(false); // Solo admin puede eliminar
                miModificar.setEnabled(true);
                miSolucionar.setEnabled(true);
                miReabrir.setEnabled(true);
                miCerrar.setEnabled(true);
                miEliminar.setEnabled(false);
                miGestionUsuarios.setEnabled(false);
                miCrearUsuarios.setEnabled(false);
                miModificarUsuarios.setEnabled(false);
                bLatUsuarios.setEnabled(false);
                break;

            case ADMINISTRADOR:
                // Administradores tienen TODOS los permisos
                // Todos los botones y menús ya están habilitados por defecto
                break;
        }
    }
    
    //Métodos para cargar datos desde la base de datos
    private void cargarIncidenciasAbiertas() {
        try {
            IncidenciaDao dao = new IncidenciaDao();//consigo la información de la bd
            List<Incidencia> lista;//preparo la lista vcia donde se guardan las incidencias
            // Usar diferentes métodos según el perfil
            if (usuarioActual.getPerfil() == Usuario.Perfil.USUARIO) {
                // Usuario normal: solo ve sus incidencias 
                lista = dao.obtenerIncidenciasPorUsuario(usuarioActual.getIdUsuario());
                // Filtrar para mostrar solo las abiertas en la primera tabla
                List<Incidencia> incidenciasAbiertas = new ArrayList<>();
                for (Incidencia incidencia : lista) {
                    //si esta incidencia no está cerrada, la añado a las incidencias abiertas
                    if (incidencia.getEstado() != Incidencia.Estado.CERRADA) {
                        incidenciasAbiertas.add(incidencia);
                    }
                }
                lista = incidenciasAbiertas;
            } else {
                // Técnico y Admin: verán todas las incidencias abiertas
                lista = dao.obtenerIncidenciasAbiertas();
            }
            //antes de añadir datos nuevos, la vacío completamente
            modeloAbiertas.setRowCount(0); // Limpia tabla existente
            for (Incidencia incidencia : lista) {
                // Prepara datos para mostrar (al final se queda cómo "desconocido" porque iba a gestionar de otra forma el programa y se quedó así
                String autor = "Desconocido";
                if (incidencia.getAutor() != null) {
                    autor = incidencia.getAutor().getNombre() + " " + 
                            incidencia.getAutor().getApellidos();
                }
                // Acorta descripción si es muy larga
                String descripcion = incidencia.getDescripcion();
                if (descripcion == null) {
                    descripcion = "";
                } else if (descripcion.length() > 100) {
                    descripcion = descripcion.substring(0, 100) + "...";
                }
//******** devuelve la incidencia anterior
                Object inci_anterior = null;
                //si tiene una incidencia anterior, guardo su ID. Si no, queda en null
                if(incidencia.getIncidenciaAnterior() != null ) {
                    inci_anterior = incidencia.getIncidenciaAnterior().getIdIncidencia();
                }
                // Formatear fecha
                String fecha = formatearFecha(incidencia.getFechaAlta());
                // Añadir fila a la tabla
                modeloAbiertas.addRow(new Object[]{
                incidencia.getIdIncidencia(),
                incidencia.getTitulo(),
                descripcion,
                autor,
                incidencia.getPrioridad().toString(),
                incidencia.getEstado().toString(),
                fecha,
                inci_anterior
                });
            }
        } catch (SQLException e) {
            manejarErrorBD("Error al cargar incidencias abiertas", e);
        }
    }
   
    private void cargarIncidenciasCerradas() {
        try {
            IncidenciaDao dao = new IncidenciaDao();
            List<Incidencia> lista;
            if (usuarioActual.getPerfil() == Usuario.Perfil.USUARIO) {
                // Usuario: solo verá sus incidencias cerradas
                lista = dao.obtenerIncidenciasPorUsuario(usuarioActual.getIdUsuario());
                // Filtrar solo Cerradas
                List<Incidencia> incidenciasCerradas = new ArrayList<>();
                for (Incidencia incidencia : lista) {
                    if (incidencia.getEstado() == Incidencia.Estado.CERRADA) {
                        incidenciasCerradas.add(incidencia);
                    }
                }
                lista = incidenciasCerradas;
            } else {
                // Técnico/Admin: veran todas las incidencias cerradas
                lista = dao.obtenerIncidenciasCerradas();
            }
            modeloCerradas.setRowCount(0); // Limpiar la tabla
            for (Incidencia incidencia : lista) {
                String tecnico = "No asignado";
                if (incidencia.getTecnicoAsignado() != null) {
                    tecnico = incidencia.getTecnicoAsignado().getNombre() + " " +
                              incidencia.getTecnicoAsignado().getApellidos();
                }
                String fechaAlta = formatearFecha(incidencia.getFechaAlta());
                String fechaFin = incidencia.getFechaFin() != null ? formatearFecha(incidencia.getFechaFin()) : "—";
                
                Object idAnterior = null;
                if (incidencia.getIncidenciaAnterior() != null) {
                    idAnterior = incidencia.getIncidenciaAnterior().getIdIncidencia();
                }
                modeloCerradas.addRow(new Object[]{
                incidencia.getIdIncidencia(),
                incidencia.getTitulo(),
                fechaAlta,
                fechaFin,
                tecnico,
                idAnterior
                });
            }
        } catch (SQLException e) {
            manejarErrorBD("Error al cargar incidencias cerradas", e);
        }
    }
    //Métodos de operaciones principales
    private void altaInci() {
        // Verificar que hay un usuario logueado
        //si no hay usuario logueado, muestro un cadro de diálogo..
        if (usuarioActual == null) {
            JOptionPane.showMessageDialog(this,
                "Debe iniciar sesión para crear incidencias",
                "Error de autenticación",
                JOptionPane.ERROR_MESSAGE);
            return; //si no existe, muestra el error y se sale 
        }
        //si hay usuario logueado
        // Crea y muestra el diálogo de alta
        DAltaIncidencia dialog = new DAltaIncidencia(this, usuarioActual);//se crea la ventana emergente para que el usuario escriba los datos de la nueva incidencia
        //se le pasa this-la ventana actual y el 'usuarioAtual' -quién está creando la incidencia
        dialog.setVisible(true);
        // Procesa el resultado del diálogo
        if (dialog.isAceptado()) {
            //si se hizo clic en Aceptar, pido los datos que el usuario escribió
            Incidencia nuevaIncidencia = dialog.getIncidencia();
            //si la incidencia es válida
            if (nuevaIncidencia != null) {
                try {
                    // Guarda la incidencia en base de datos
                    IncidenciaDao dao = new IncidenciaDao();
                    dao.crear(nuevaIncidencia);
                    // Aquí recargar las tablas para mostrar los cambios una vez guardada la nueva incidencia
                    cargarIncidenciasAbiertas();
                    cargarIncidenciasCerradas();
                    // Buscar y selecciona la nueva incidencia en la tabla
                    seleccionarIncidencia(nuevaIncidencia.getIdIncidencia(), true);
                    // para los mensaje de confirmación
                    JOptionPane.showMessageDialog(this,
                        "✅ Incidencia creada exitosamente\nID: " + nuevaIncidencia.getIdIncidencia(),
                        "Operación exitosa",
                        JOptionPane.INFORMATION_MESSAGE);     
                } catch (SQLException ex) {
                    manejarErrorBD("Error al crear la incidencia", ex);
                }
            }
        }
    }
    
    private void modificarInci() {
        // Verifica si hay una incidencia seleccionada
        //si no hay ninguna fila seleccionada muestra el cuadro..
        if (tablaInciAbiertas.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this,
                "Seleccione una incidencia para modificar",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return; //se sale automaticamente del  método
        }  
        //verificando en que pestaña está el usuario
        //si está en la pestaña número 0 entonces la variable esAbireta será true..
        boolean esAbierta = pestaniasInci.getSelectedIndex() == 0;
        if (!esAbierta) { //si el usuario no está en la pestaña de incidencias abiertas , muestra ese mensaje
            JOptionPane.showMessageDialog(this,
                "No se pueden modificar incidencias cerradas.\n" +
                "Solo es posible reabrirlas.",
                "Acción no permitida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            // Obtiene el Id de la incidencia seleccionada
            int filaSeleccionada = tablaInciAbiertas.getSelectedRow();
            int idIncidencia = (int) modeloAbiertas.getValueAt(filaSeleccionada, 0);     
            // Obtiene la incidencia completa desde la BD
            IncidenciaDao incidenciaDao = new IncidenciaDao();
            Incidencia incidencia = incidenciaDao.obtenerPorId(idIncidencia);           
            if (incidencia == null) {
                JOptionPane.showMessageDialog(this,
                    "No se encontró la incidencia seleccionada",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }            
            if (incidencia.getEstado() == Incidencia.Estado.CERRADA) {
                JOptionPane.showMessageDialog(this,
                    "No se pueden modificar incidencias cerradas.\n" +
                    "Si necesita cambios, debe reabrir la incidencia.",
                    "Acción no permitida",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Verifica permisos del usuario
            if (!verificarPermisosModificacion(incidencia)) {
                JOptionPane.showMessageDialog(this,
                    "No tiene permisos para modificar esta incidencia",
                    "Permiso denegado",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }             
            // Guarda estado y prioridad para el histórico
            Incidencia.Estado estadoOriginal = incidencia.getEstado();
            Incidencia.Prioridad prioridadOriginal = incidencia.getPrioridad();        
            // Muestra diálogo de modificación
            DModificarIncidencia dialog = new DModificarIncidencia(this, incidencia, usuarioActual);
            dialog.setVisible(true);
            // Procesa los cambios
            Incidencia incidenciaModificada = dialog.getIncidenciaModificada();
            if (incidenciaModificada != null) {
                // Guarda cambios en la BD
                incidenciaDao.actualizar(incidenciaModificada);
                // Registra cambios en el histórico si corresponde
                registrarCambiosEnHistorico(incidenciaModificada, estadoOriginal, prioridadOriginal);
                // Recarga las tablas
                cargarIncidenciasAbiertas();
                cargarIncidenciasCerradas();
                // Actualiza el panel de detalles si está visible
                mostrarDetallesIncidencia(idIncidencia);
                // Muestra mensaje de confirmación
                JOptionPane.showMessageDialog(this,
                    "✅ Incidencia modificada exitosamente",
                    "Operación exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            manejarErrorBD("Error al modificar la incidencia", ex);
        }
    }
    
    private void solucionarInci() {
        // Verifica selección
        if (tablaInciAbiertas.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this,
                "Seleccione una incidencia para solucionar",
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        boolean esAbierta = pestaniasInci.getSelectedIndex() == 0;
            if (!esAbierta) {
                JOptionPane.showMessageDialog(this,
                    "No se pueden solucionar incidencias cerradas.",
                    "Acción no permitida",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
        try {
            int fila = tablaInciAbiertas.getSelectedRow();
            int id = (int) modeloAbiertas.getValueAt(fila, 0);

            IncidenciaDao incidenciaDao = new IncidenciaDao();
            Incidencia incidencia = incidenciaDao.obtenerPorId(id);

            if (incidencia == null) return;

            // Validá que esté PENDIENTE
            if (incidencia.getEstado() != Incidencia.Estado.PENDIENTE) {
                JOptionPane.showMessageDialog(this,
                    "Solo se pueden solucionar incidencias PENDIENTES",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Muestra opciones 
            String[] opciones = {"En Proceso", "Poner en Espera"};
            int opcion = JOptionPane.showOptionDialog(this,
                "Seleccione acción para la incidencia:\n" +
                "ID: " + incidencia.getIdIncidencia() + "\n" +
                "Título: " + incidencia.getTitulo(),
                "Solucionar Incidencia",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, opciones, opciones[0]);
            if (opcion == -1) return; // Usuario canceló
            // Prepara cambio según opción seleccionada
            Incidencia.Estado nuevoEstado = (opcion == 0) ? Incidencia.Estado.EN_PROCESO : Incidencia.Estado.ESPERA;
            String comentario = (opcion == 1) ? 
                JOptionPane.showInputDialog(this, "Motivo de la espera:", "Poner en Espera", JOptionPane.QUESTION_MESSAGE) :
                "Incidencia en proceso de resolución";
            if (opcion == 1 && (comentario == null || comentario.trim().isEmpty())) {
                JOptionPane.showMessageDialog(this, "Debe especificar un motivo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Guarda estado anterior
            Incidencia.Estado estadoAnterior = incidencia.getEstado();
            // Aplica cambios según especificaciones
            incidencia.setEstado(nuevoEstado);
            incidencia.setTecnicoAsignado(usuarioActual);
            // Pone fecha de inicio cuando se comienza a solucionar (según enunciado)
            if (incidencia.getFechaInicio() == null) {
                incidencia.setFechaInicio(java.time.LocalDateTime.now());
            }
            // Guarda en BD
            incidenciaDao.actualizar(incidencia);
            // Registra en histórico
            HistoricoResolucion historico = new HistoricoResolucion();
            historico.setIncidencia(incidencia);
            historico.setTecnico(usuarioActual);
            historico.setFechaCambio(java.time.LocalDateTime.now());
            historico.setEstadoAnterior(estadoAnterior);
            historico.setEstadoNuevo(nuevoEstado);
            historico.setComentario(comentario);
            // Crea el registro
            new HistoricoResolucionDao().crearRegistro(historico);
            // Recarga
            cargarIncidenciasAbiertas();

            JOptionPane.showMessageDialog(this,
                "Incidencia cambiada a: " + nuevoEstado,
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage(),
                "Error BD", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void reabrirInci() {
        // Verifica que hay una incidencia cerrada seleccionada
        if (tablaInciCerradas.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this,
                "Seleccione una incidencia cerrada para reabrir",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }  
        
        boolean esCerrada = pestaniasInci.getSelectedIndex() == 1; // 1 = cerradas
        if (!esCerrada) {
            JOptionPane.showMessageDialog(this,
                "Solo se pueden reabrir incidencias cerradas.",
                "Acción no permitida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            // Obtiene el ID de la incidencia seleccionada
            int filaSeleccionada = tablaInciCerradas.getSelectedRow();
            int idIncidencia = (int) modeloCerradas.getValueAt(filaSeleccionada, 0);            
            // Obtene la incidencia completa
            IncidenciaDao incidenciaDao = new IncidenciaDao();
            Incidencia incidencia = incidenciaDao.obtenerPorId(idIncidencia);          
            if (incidencia == null) {
                JOptionPane.showMessageDialog(this,
                    "No se encontró la incidencia seleccionada",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }            
            // Verifica que la incidencia esté cerrada
            if (incidencia.getEstado() == null ||
                incidencia.getEstado() != Incidencia.Estado.CERRADA) {

                JOptionPane.showMessageDialog(this,
                    "Esta incidencia no está cerrada y no se puede reabrir",
                    "Acción no permitida",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
          
            // Se pide la confirmación
            int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de reabrir la incidencia?\n\n" +
                "ID: " + incidencia.getIdIncidencia() + "\n" +
                "Título: " + incidencia.getTitulo(),
                "Confirmar Reapertura",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);           
            if (confirmacion == JOptionPane.YES_OPTION) {
                // Pide un comentario
                String comentario = JOptionPane.showInputDialog(this,
                    "Ingrese el motivo de la reapertura:",
                    "Reabrir Incidencia",
                    JOptionPane.PLAIN_MESSAGE);             
                if (comentario != null && !comentario.trim().isEmpty()) {
                    // Guarda estado anterior
                    Incidencia.Estado estadoAnterior = incidencia.getEstado();
                    // Crea Nueva incidencia 
                    Incidencia nuevaIncidencia = new Incidencia(
                        "[REABIERTA] " + incidencia.getTitulo(),
                        "\nMotivo: " + comentario + 
                        "\n\nDescripción original:\n" + incidencia.getDescripcion(),
                        incidencia.getAutor(),  // Mismo autor
                        incidencia.getPrioridad()
                    );    
                    // Vincula con la incidencia anterior
                    nuevaIncidencia.setIncidenciaAnterior(incidencia);
                    // Guarda nueva incidencia en la BD
                    incidenciaDao.crear(nuevaIncidencia);
                    // Registra en histórico de la incidencia original
                    HistoricoResolucion historico = new HistoricoResolucion();
                    historico.setIncidencia(incidencia);
                    historico.setTecnico(usuarioActual);
                    historico.setFechaCambio(java.time.LocalDateTime.now());
                    historico.setEstadoAnterior(estadoAnterior);
                    historico.setEstadoNuevo(Incidencia.Estado.CERRADA); //aquí sigue cerrada
                    historico.setComentario("REABIERTA como #" + nuevaIncidencia.getIdIncidencia() + 
                                          ": " + comentario);
                    HistoricoResolucionDao historicoDao = new HistoricoResolucionDao();
                    historicoDao.crearRegistro(historico); 
                    // Recarga tablas
                    cargarIncidenciasAbiertas();
                    cargarIncidenciasCerradas();
                    // Cambia a pestaña de abiertas y selecciona la nueva incidencia
                    pestaniasInci.setSelectedIndex(0);
                    seleccionarIncidencia(nuevaIncidencia.getIdIncidencia(), true);
                    JOptionPane.showMessageDialog(this,
                        "✅ Incidencia reabierta exitosamente\nNueva ID: " + nuevaIncidencia.getIdIncidencia(),
                        "Operación exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } 
        } catch (SQLException ex) {
            manejarErrorBD("Error al reabrir la incidencia", ex);
        }
    }
    
    private void cerrarIncidencia() {
        try {
            int fila = tablaInciAbiertas.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, 
                    "Seleccione una incidencia para cerrar", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = (int) modeloAbiertas.getValueAt(fila, 0);
            IncidenciaDao incidenciaDao = new IncidenciaDao();
            Incidencia incidencia = incidenciaDao.obtenerPorId(id);
            if (incidencia == null) return;
            boolean esAbierta = pestaniasInci.getSelectedIndex() == 0;
                if (!esAbierta) {
                    JOptionPane.showMessageDialog(this,
                        "No se pueden cerrar incidencias ya cerradas.",
                        "Acción no permitida",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
            // Solo cierra si está EN_PROCESO o ESPERA
            if (incidencia.getEstado() != Incidencia.Estado.EN_PROCESO && 
                incidencia.getEstado() != Incidencia.Estado.ESPERA) {
                JOptionPane.showMessageDialog(this,
                    "Solo puede cerrar incidencias en 'En Proceso' o 'Espera'",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Se pide un comentario
            String comentario = JOptionPane.showInputDialog(this,
                "Comentario de cierre:", "Cerrar Incidencia", JOptionPane.QUESTION_MESSAGE);
            if (comentario == null || comentario.trim().isEmpty()) return;
            // Cerrar
            Incidencia.Estado estadoAnterior = incidencia.getEstado();
            incidencia.setEstado(Incidencia.Estado.CERRADA);
            incidencia.setFechaFin(java.time.LocalDateTime.now());
            incidenciaDao.actualizar(incidencia);
            // Registrar histórico
            HistoricoResolucion historico = new HistoricoResolucion();
            historico.setIncidencia(incidencia);
            historico.setTecnico(usuarioActual);
            historico.setFechaCambio(java.time.LocalDateTime.now());
            historico.setEstadoAnterior(estadoAnterior);
            historico.setEstadoNuevo(Incidencia.Estado.CERRADA);
            historico.setComentario("CERRADA: " + comentario);
            new HistoricoResolucionDao().crearRegistro(historico);
            // Actualiza
            cargarIncidenciasAbiertas();
            cargarIncidenciasCerradas();
            pestaniasInci.setSelectedIndex(1); // Ir a cerradas
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarInci() {
        // Determina en qué pestaña está seleccionada la incidencia
        //añado alias para que apunten a la tabla existente
        JTable tablaActual;
        DefaultTableModel modeloActual;
        boolean esAbierta;       
        if (pestaniasInci.getSelectedIndex() == 0) {
            // Pestaña de abiertas
            tablaActual = tablaInciAbiertas;
            modeloActual = modeloAbiertas;
            esAbierta = true;
        } else {
            // Pestaña de cerradas
            tablaActual = tablaInciCerradas;
            modeloActual = modeloCerradas;
            esAbierta = false;
        }        
        // Verifica selección
        if (tablaActual.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this,
                "Seleccione una incidencia para eliminar",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }       
        // Verifica permisos (solo administradores pueden eliminar)
        if (usuarioActual.getPerfil() != Usuario.Perfil.ADMINISTRADOR) {
            JOptionPane.showMessageDialog(this,
                "Solo administradores pueden eliminar incidencias",
                "Permiso denegado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }        
        try {
            // Obtener el ID de la incidencia seleccionada
            int filaSeleccionada = tablaActual.getSelectedRow();
            int idIncidencia = (int) modeloActual.getValueAt(filaSeleccionada, 0);
            
            // Obtiene la incidencia para mostrar información
            IncidenciaDao incidenciaDao = new IncidenciaDao();
            Incidencia incidencia = incidenciaDao.obtenerPorId(idIncidencia);          
            if (incidencia == null) {
                JOptionPane.showMessageDialog(this,
                    "No se encontró la incidencia seleccionada",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }           
            // Se pide confirmación
            int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de ELIMINAR permanentemente esta incidencia?\n\n" +
                "ID: " + incidencia.getIdIncidencia() + "\n" +
                "Título: " + incidencia.getTitulo() + "\n" +
                "Estado: " + incidencia.getEstado() + "\n\n" +
                "⚠️ Esta acción NO se puede deshacer.",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);          
            if (confirmacion == JOptionPane.YES_OPTION) {
                // Realiza baja lógica (marca como no activa)
                incidencia.setActiva(false);
                incidenciaDao.actualizar(incidencia);
                // Recarga tablas
                cargarIncidenciasAbiertas();
                cargarIncidenciasCerradas();
                // Limpia panel de detalles
                ocultarDetalles();              
                JOptionPane.showMessageDialog(this,
                    "✅ Incidencia eliminada exitosamente",
                    "Operación exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
            }           
        } catch (SQLException ex) {
            manejarErrorBD("Error al eliminar la incidencia", ex);
        }
    }
    
    private void cambiarPrioridadIncidencia() {
        try {
            // Determina en qué pestaña se encuentra cada usuario
            boolean esAbierta = pestaniasInci.getSelectedIndex() == 0; // 0 = abiertas, 1 = cerradas
            int fila = esAbierta ? tablaInciAbiertas.getSelectedRow() : tablaInciCerradas.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(this, 
                    "Seleccione una incidencia", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id = esAbierta ? (int) modeloAbiertas.getValueAt(fila, 0) : (int) modeloCerradas.getValueAt(fila, 0);
            IncidenciaDao incidenciaDao = new IncidenciaDao();
            Incidencia incidencia = incidenciaDao.obtenerPorId(id);
            if (incidencia == null) return;

            // Verifica que la incidencia esté abierta
            if (!esAbierta) {
                JOptionPane.showMessageDialog(this,
                    "No se puede cambiar la prioridad de incidencias cerradas.\n" +
                    "Solo se pueden modificar incidencias abiertas.",
                    "Acción no permitida", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Solo técnicos y administradores pueden cambiar prioridad
            if (usuarioActual.getPerfil() != Usuario.Perfil.TECNICO &&
                usuarioActual.getPerfil() != Usuario.Perfil.ADMINISTRADOR) {
                JOptionPane.showMessageDialog(this,
                    "Solo técnicos y administradores pueden cambiar prioridades",
                    "Permiso denegado", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Guarda prioridad anterior
            Incidencia.Prioridad anterior = incidencia.getPrioridad();
            String nuevaPrioridadStr = (String) JOptionPane.showInputDialog(this,
                "Nueva prioridad para: " + incidencia.getTitulo(),
                "Cambiar Prioridad",
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"BAJA", "MEDIA", "ALTA"},
                anterior.toString());

            if (nuevaPrioridadStr == null) return;

            Incidencia.Prioridad nueva = Incidencia.Prioridad.valueOf(nuevaPrioridadStr);
            if (nueva == anterior) return; // No hubo cambio

            // Solicita motivo para registrar en histórico
            String motivo = JOptionPane.showInputDialog(this, "Motivo del cambio:", "Justificación");
            if (motivo == null || motivo.trim().isEmpty()) return;

            // Aplica el cambio
            incidencia.setPrioridad(nueva);
            incidenciaDao.actualizar(incidencia);

            // Registra histórico
            HistoricoResolucion historico = new HistoricoResolucion();
            historico.setIncidencia(incidencia);
            historico.setTecnico(usuarioActual);
            historico.setFechaCambio(java.time.LocalDateTime.now());
            historico.setPrioridadAnterior(anterior);
            historico.setPrioridadNueva(nueva);
            historico.setComentario("Cambio prioridad: " + motivo);
            new HistoricoResolucionDao().crearRegistro(historico);

            // Recarga tabla
            cargarIncidenciasAbiertas();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para mostrar detalles e historico
    private void configurarListenersSeleccion() {
        // Listener para tabla de incidencias abiertas
        tablaInciAbiertas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tablaInciAbiertas.getSelectedRow();
                if (fila >= 0) {
                    int idIncidencia = (int) modeloAbiertas.getValueAt(fila, 0);
                    mostrarDetallesIncidencia(idIncidencia);
                }
            }
        });       
        // Listener para tabla de incidencias cerradas
        tablaInciCerradas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tablaInciCerradas.getSelectedRow();
                if (fila >= 0) {
                    int idIncidencia = (int) modeloCerradas.getValueAt(fila, 0);
                    mostrarDetallesIncidencia(idIncidencia);
                }
            }
        });
    }
    
    private void mostrarDetallesIncidencia(int idIncidencia) {
        try {
            // Obtiene incidencia desde BD
            IncidenciaDao incidenciaDao = new IncidenciaDao();
            Incidencia incidencia = incidenciaDao.obtenerPorId(idIncidencia);       
            if (incidencia != null) {
                // Obtiene histórico desde BD
                HistoricoResolucionDao historicoDao = new HistoricoResolucionDao();
                List<HistoricoResolucion> historico = historicoDao.obtenerPorIncidencia(idIncidencia);      
                // Muestra detalles e histórico
                mostrarDetallesConHistorico(incidencia, historico);
            } else {
                mostrarMensajeNoEncontrado();
            }           
        } catch (SQLException ex) {
            manejarErrorBD("Error al cargar detalles de la incidencia", ex);
        }
    }
    
    private void mostrarDetallesConHistorico(Incidencia incidencia, List<HistoricoResolucion> historico) {
        // Limpia panel de detalles
        pDetalles.removeAll();
        pDetalles.setLayout(new BorderLayout(5, 5)); 
        // Panel superior con información básica
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));   
        // Añade información básica
        panelInfo.add(crearEtiquetaConTitulo("ID:", String.valueOf(incidencia.getIdIncidencia())));
        panelInfo.add(Box.createVerticalStrut(5));
        panelInfo.add(crearEtiquetaConTitulo("Título:", incidencia.getTitulo()));
        panelInfo.add(Box.createVerticalStrut(5));
        panelInfo.add(crearEtiquetaConTitulo("Estado:", incidencia.getEstado().toString()));
        panelInfo.add(Box.createVerticalStrut(5));
        panelInfo.add(crearEtiquetaConTitulo("Prioridad:", incidencia.getPrioridad().toString()));
        panelInfo.add(Box.createVerticalStrut(5));
        // Autor
        if (incidencia.getAutor() != null) {
            String autor = incidencia.getAutor().getNombre() + " " + 
                          incidencia.getAutor().getApellidos() + 
                          " (" + incidencia.getAutor().getEmail() + ")";
            panelInfo.add(crearEtiquetaConTitulo("Autor:", autor));
            panelInfo.add(Box.createVerticalStrut(5));
        }
        // Técnico asignado
        if (incidencia.getTecnicoAsignado() != null) {
            String tecnico = incidencia.getTecnicoAsignado().getNombre() + " " + 
                            incidencia.getTecnicoAsignado().getApellidos();
            panelInfo.add(crearEtiquetaConTitulo("Técnico:", tecnico));
            panelInfo.add(Box.createVerticalStrut(5));
        }
        // Fechas
        panelInfo.add(crearEtiquetaConTitulo("Fecha Alta:", formatearFechaCompleta(incidencia.getFechaAlta())));
        panelInfo.add(Box.createVerticalStrut(5)); 
        if (incidencia.getFechaInicio() != null) {
            panelInfo.add(crearEtiquetaConTitulo("Fecha Inicio:", formatearFechaCompleta(incidencia.getFechaInicio())));
            panelInfo.add(Box.createVerticalStrut(5));
        }   
        if (incidencia.getFechaFin() != null) {
            panelInfo.add(crearEtiquetaConTitulo("Fecha Fin:", formatearFechaCompleta(incidencia.getFechaFin())));
            panelInfo.add(Box.createVerticalStrut(5));
        }     
        // Descripción completa
        JTextArea txtDescripcion = new JTextArea(incidencia.getDescripcion());
        txtDescripcion.setEditable(false);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setBorder(BorderFactory.createTitledBorder("Descripción Completa"));
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        scrollDescripcion.setPreferredSize(new Dimension(280, 100));     
        //Historico
        JTextArea txtHistorico = new JTextArea();
        txtHistorico.setEditable(false);
        txtHistorico.setLineWrap(true);
        txtHistorico.setWrapStyleWord(true);
        if (historico.isEmpty()) {
            txtHistorico.setText("No hay registro histórico para esta incidencia.");
        } else {
            StringBuilder sb = new StringBuilder(); 
            for (HistoricoResolucion registro : historico) {
                sb.append("📅  ").append(formatearFechaCompleta(registro.getFechaCambio())).append("\n");         
                if (registro.getEstadoAnterior() != null && registro.getEstadoNuevo() != null) {
                    sb.append("   Estado: ").append(registro.getEstadoAnterior()).append(" → ").append(registro.getEstadoNuevo()).append("\n");
                }
                if (registro.getPrioridadAnterior() != null && registro.getPrioridadNueva() != null) {
                    sb.append("   Prioridad: ").append(registro.getPrioridadAnterior()).append(" → ").append(registro.getPrioridadNueva()).append("\n");
                }
                if (registro.getComentario() != null && !registro.getComentario().isEmpty()) {
                    sb.append("📝 ").append(registro.getComentario()).append("\n");
                }
                if (registro.getTecnico() != null) {
                    sb.append("👤 Perfil: ").append(registro.getTecnico().getNombre()).append("\n");
                }
                sb.append("──────────────────────────\n\n");
            }
            txtHistorico.setText(sb.toString());
        }
        JScrollPane scrollHistorico = new JScrollPane(txtHistorico);
        scrollHistorico.setBorder(BorderFactory.createTitledBorder("Histórico de Resolución"));
        scrollHistorico.setPreferredSize(new Dimension(280, 200));
        // Panel para descripción e histórico
        JPanel panelCentro = new JPanel(new BorderLayout(5, 5));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        panelCentro.add(scrollDescripcion, BorderLayout.NORTH);
        panelCentro.add(scrollHistorico, BorderLayout.CENTER); 
        // Añado todo al panel principal
        pDetalles.add(panelInfo, BorderLayout.NORTH);
        pDetalles.add(panelCentro, BorderLayout.CENTER);
        // Actualiza visualización
        pDetalles.revalidate();
        pDetalles.repaint();
    }
    
    private void mostrarDetallesCompletos() {
        int fila = -1;
        boolean esAbierta = true;
        if (pestaniasInci.getSelectedIndex() == 0) {
            fila = tablaInciAbiertas.getSelectedRow();
        } else {
            fila = tablaInciCerradas.getSelectedRow();
            esAbierta = false;
        }
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una incidencia", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = esAbierta ? 
            (int) modeloAbiertas.getValueAt(fila, 0) : 
            (int) modeloCerradas.getValueAt(fila, 0);
        mostrarDetallesIncidencia(id);
    }
    
    private void ocultarDetalles() {
        pDetalles.removeAll();
        JLabel lblInicial = new JLabel("Seleccione una incidencia", SwingConstants.CENTER);
        lblInicial.setFont(new Font("Arial", Font.ITALIC, 12));
        lblInicial.setForeground(Color.GRAY);
        pDetalles.add(lblInicial, BorderLayout.CENTER);
        pDetalles.revalidate();
        pDetalles.repaint();
    }
    
    //Métodos auxiliares de la app
    private void seleccionarIncidencia(int idIncidencia, boolean esAbierta) {
        DefaultTableModel modelo = esAbierta ? modeloAbiertas : modeloCerradas;
        JTable tabla = esAbierta ? tablaInciAbiertas : tablaInciCerradas;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if ((int) modelo.getValueAt(i, 0) == idIncidencia) {
                tabla.setRowSelectionInterval(i, i);
                tabla.scrollRectToVisible(tabla.getCellRect(i, 0, true));
                break;
            }
        }
    }
    
    private boolean verificarPermisosModificacion(Incidencia incidencia) {
        Usuario.Perfil perfil = usuarioActual.getPerfil();
        // Administradores pueden modificar todo
        if (perfil == Usuario.Perfil.ADMINISTRADOR) {
            return true;
        }
        // Técnicos pueden modificar cualquier incidencia
        if (perfil == Usuario.Perfil.TECNICO) {
            return true;
        }
        // Usuarios que solo pueden modificar sus propias incidencias
        if (perfil == Usuario.Perfil.USUARIO) {
            return incidencia.getAutor().getIdUsuario() == usuarioActual.getIdUsuario();
        }    
        return false;
    }
    
   private void registrarCambiosEnHistorico(
            Incidencia incidencia,
            Incidencia.Estado estadoOriginal,
            Incidencia.Prioridad prioridadOriginal) {

        try {
            HistoricoResolucion historico = new HistoricoResolucion();
            historico.setIncidencia(incidencia);
            historico.setTecnico(usuarioActual);
            historico.setFechaCambio(LocalDateTime.now());

            boolean hayCambios = false;

            if (!incidencia.getEstado().equals(estadoOriginal)) {
                historico.setEstadoAnterior(estadoOriginal);
                historico.setEstadoNuevo(incidencia.getEstado());
                hayCambios = true;
            }

            if (!incidencia.getPrioridad().equals(prioridadOriginal)) {
                historico.setPrioridadAnterior(prioridadOriginal);
                historico.setPrioridadNueva(incidencia.getPrioridad());
                hayCambios = true;
            }

            if (hayCambios) {
                historico.setComentario("Modificación realizada");
                new HistoricoResolucionDao().crearRegistro(historico);
            }

        } catch (SQLException ex) {
            System.err.println("Error al registrar histórico: " + ex.getMessage());
        }
    }

    private JPanel crearEtiquetaConTitulo(String titulo, String valor) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 12));
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(lblTitulo);
        panel.add(lblValor);
        return panel;
    }
    
    private String formatearFecha(LocalDateTime fecha) {
        if (fecha == null) return "—";
        return fecha.toLocalDate().toString(); // Solo la fecha
    }
    
    private String formatearFechaCompleta(LocalDateTime fecha) {
        if (fecha == null) return "—";
        java.time.format.DateTimeFormatter formatter = 
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return fecha.format(formatter); // Fecha y hora
    }
    
    private void manejarErrorBD(String contexto, SQLException ex) {
        ex.printStackTrace();
        
        String mensaje = contexto + ":\n" + ex.getMessage();
        JOptionPane.showMessageDialog(this,
            mensaje,
            "Error de Base de Datos",
            JOptionPane.ERROR_MESSAGE);
    }
    
    private void mostrarMensajeNoEncontrado() {
        pDetalles.removeAll();
        JLabel lblError = new JLabel("Incidencia no encontrada", SwingConstants.CENTER);
        lblError.setFont(new Font("Arial", Font.ITALIC, 14));
        lblError.setForeground(Color.RED);
        pDetalles.add(lblError, BorderLayout.CENTER);
        pDetalles.revalidate();
        pDetalles.repaint();
    }
    
//    private void sizeButton(JButton boton, Dimension dimension) {
//        boton.setPreferredSize(dimension);
//        boton.setMinimumSize(dimension);
//        boton.setMaximumSize(dimension);
//    }
    private void filtrarTabla(){
        try {
            // Obtiene valores de los filtros
            String estadoFiltro = (String) cbEstadoFiltro.getSelectedItem();
            String prioridadFiltro = (String) cbPrioridadFiltro.getSelectedItem();
            Object tecnicoFiltro = cbTecnicoFiltro.getSelectedItem();
            String textoBusqueda = tfBuscarFiltro.getText().trim().toLowerCase();
            // Determina qué tabla esta viendo
            boolean esTablaAbiertas = pestaniasInci.getSelectedIndex() == 0;
            JTable tablaActual = esTablaAbiertas ? tablaInciAbiertas : tablaInciCerradas;
            DefaultTableModel modeloActual = esTablaAbiertas ? modeloAbiertas : modeloCerradas;
            // Crear RowFilter para la tabla actual
            RowFilter<DefaultTableModel, Integer> filtro = new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    try {
                        // Obtiene valores de la fila
                        int id = (int) entry.getValue(0);
                        String titulo = (String) entry.getValue(1);
                        String descripcion = esTablaAbiertas ? (String) entry.getValue(2) : "";
                        String autor = esTablaAbiertas ? (String) entry.getValue(3) : "";
                        String prioridad = esTablaAbiertas ? (String) entry.getValue(4) : "";
                        String estado = esTablaAbiertas ? (String) entry.getValue(5) : "";
                        String fecha = esTablaAbiertas ? (String) entry.getValue(6) : (String) entry.getValue(2);
                        String tecnico = !esTablaAbiertas ? (String) entry.getValue(4) : "";
                        // Filtra por ESTADO (solo para las incidencias abiertas)
                        if (esTablaAbiertas && !estadoFiltro.equals("Todos")) {
                            String estadoTabla = estado.toUpperCase().replace(" ", "_");
                            String estadoFiltroBD = estadoFiltro.toUpperCase().replace(" ", "_");
                            if (!estadoTabla.equals(estadoFiltroBD)) {
                                return false;
                            }
                        }
                        // Filtra por PRIORIDAD
                        if (!prioridadFiltro.equals("Todos")) {
                            String prioridadTabla = prioridad.toUpperCase();
                            if (!prioridadTabla.equals(prioridadFiltro.toUpperCase())) {
                                return false;
                            }
                        }
                        // Filtra por TÉCNICO (solo para cerradas)
                        if (!esTablaAbiertas && !tecnicoFiltro.equals("Todos")) {
                            if (tecnicoFiltro instanceof Usuario) {
                                Usuario tecnicoSeleccionado = (Usuario) tecnicoFiltro;
                                String nombreTecnicoCompleto = tecnicoSeleccionado.getNombre() + " " + 
                                                              tecnicoSeleccionado.getApellidos();
                                if (!tecnico.contains(tecnicoSeleccionado.getNombre()) && 
                                    !tecnico.contains(nombreTecnicoCompleto)) {
                                    return false;
                                }
                            }
                        }
                        // Filtra por TEXTO de búsqueda
                        if (!textoBusqueda.isEmpty()) {
                            boolean encontrado = false;
                            // Busca en todos los campos de texto
                            String[] campos = {titulo, descripcion, autor, estado, prioridad, fecha, tecnico};
                            for (String campo : campos) {
                                if (campo != null && campo.toLowerCase().contains(textoBusqueda)) {
                                    encontrado = true;
                                    break;
                                }
                            }
                            // También buscar en ID
                            if (String.valueOf(id).contains(textoBusqueda)) {
                                encontrado = true;
                            }
                            if (!encontrado) {
                                return false;
                            }
                        }
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return true;
                    }
                }
            };
            // Aplica filtro a la tabla
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloActual);
            sorter.setRowFilter(filtro);
            tablaActual.setRowSorter(sorter);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error al aplicar filtros: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Método para la salida que aparece en la barra de menú
    private void confirmarSalida() {
        int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea salir de la aplicación?",
            "Confirmar salida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    // Método para la configuración de los colores para cada perfil
    private void configurarColoresPorPerfil() {
        Color colorBarra;
        switch (usuarioActual.getPerfil()) {
            case USUARIO: colorBarra = new Color(100, 180, 255); break; // Azul
            case TECNICO: colorBarra = new Color(60, 179, 113); break; // Verde
            case ADMINISTRADOR: colorBarra = new Color(11, 102, 128); break; // Rojo
            default: colorBarra = new Color(90, 185, 193);
        }
        pLateralIzq.setBackground(colorBarra);
    }

    private void abrirDialogoInformes() {
        abrirDialogoInformes("GENERAL");
    }
    
    private void abrirDialogoInformes(String tipo) {
        JDialog dialog = new JDialog(this, "Informes de Incidencias", true);
        dialog.setSize(520, 360);
        dialog.setLocationRelativeTo(this);//centrar respecto a la ventana principal
        dialog.setLayout(new BorderLayout(10, 10)); //con espacios horizontales y verticales, 10 en cada

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Informe de Incidencias", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titulo, BorderLayout.NORTH);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        try {
            IncidenciaDao dao = new IncidenciaDao();
            int total = dao.contarTotal(usuarioActual);
            int pendientes = dao.contarPorEstado("PENDIENTE", usuarioActual);
            int proceso = dao.contarPorEstado("EN_PROCESO", usuarioActual);
            int espera = dao.contarPorEstado("ESPERA", usuarioActual);
            int cerradas = dao.contarPorEstado("CERRADA", usuarioActual);
            switch (tipo) {
                case "ABIERTAS":
                    area.setText(
                        "📂 Incidencias Abiertas \n\n" +
                        "Pendientes : " + pendientes + "\n" +
                        "En proceso : " + proceso + "\n" +
                        "En espera  : " + espera + "\n"
                    );
                    break;
                case "CERRADAS":
                    area.setText(
                        "✅ Incidencias Cerradas\n\n" +
                        "Total cerradas : " + cerradas + "\n"
                    );
                    break;
                default:
                    area.setText(
                        "📊 Informe general\n\n" +
                        "Total incidencias : " + total + "\n" +
                        "Abiertas          : " + (total - cerradas) + "\n" +
                        "Cerradas          : " + cerradas + "\n"
                    );
            }
        } catch (SQLException ex) {
            area.setText("Error al generar el informe:\n" + ex.getMessage());
        }
        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> dialog.dispose());

        JPanel pie = new JPanel();
        pie.add(cerrar);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(pie, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    //método para cerrar la sesión del usuario
    private void logout() {
        int opcion = JOptionPane.showConfirmDialog(
            this,
            "¿Desea cerrar sesión?",
            "Cerrar sesión",
            JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            this.dispose(); // cerrar ventana principal
            new LoginFrame().setVisible(true); // volver al login
        }
    }

}
