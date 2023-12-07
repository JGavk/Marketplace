package view;

import controller.WelcomeController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class WelcomeView extends JFrame{
    private JPanel mainPanel;
    private JLabel label1;
    public JTextField nombreField;
    private JPanel DPanel;
    private JButton ingresarButton;
    private JPanel entracePanel;
    private JLabel nombreLabel;
    private WelcomeController controller;

    public WelcomeView (){
        //Caracteristicas de la ventana
       setTitle("Product Manager");
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       setSize(500, 600);
       setLocationRelativeTo(null);
       setVisible(true);
       setContentPane(mainPanel);

       label1.setSize(getWidth(),getHeight());
       label1.setIcon(new ImageIcon("src/images/Frutas.jpg"));
       nombreField.setPreferredSize(new Dimension(100,100));
       nombreField.setMinimumSize(null);
       ingresarButton.addActionListener(this::actionPerformed);

    }
    //Setter del primer controlador
        public void setWelcomeController(WelcomeController controller){
            this.controller = controller;
        }
    //Accion del boton ingresar mediante el controlador
        public void actionPerformed (ActionEvent e){
            if(e.getSource()==ingresarButton){
                String input = nombreField.getText();

                if (input.isEmpty() || !input.matches("^[a-zA-Z]+$")) {
                    JOptionPane.showMessageDialog(this, "Por favor, ingresa un nombre válido (solo letras).");
                    nombreField.setText("");
                } else {
                    controller.StructureOpen(input);
                    dispose();
                }
            }

        }


}
