package view;

import controller.WelcomeController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
       setTitle("Product Manager");
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       setSize(500, 600);
       setLocationRelativeTo(null);
       setVisible(true);
       setContentPane(mainPanel);

       label1.setSize(getWidth(),getHeight());
       //label1.setPreferredSize(new Dimension(400, 200));
       label1.setIcon(new ImageIcon("src/images/Frutas.jpg"));
       //nombreLabel.setSize(getWidth()/2,getHeight()/2);
       //nombreLabel.setIcon(new ImageIcon("src/images/ImagenMercado.jpg"));
       nombreField.setPreferredSize(new Dimension(100,100));
       nombreField.setMinimumSize(null);
       ingresarButton.addActionListener(this::actionPerformed);

       //Mensaje Prueba
    }
        public void setWelcomeController(WelcomeController controller){
            this.controller = controller;
        }

        public void actionPerformed (ActionEvent e){
            if(e.getSource()==ingresarButton){
                String input = nombreField.getText();
                controller.StructureOpen(input);
                dispose();
            }

        }


}
