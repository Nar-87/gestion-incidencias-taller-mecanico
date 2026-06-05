/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.UIManager;

/**
 *
 * @author Portege Z930
 */
public class MainTallerMecanico {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf()); 
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf: " + ex);
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new LoginFrame();
                
                // Configuraciones de la ventana principal
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
               
                frame.setVisible(true);
            }
        }); 
    }
}
