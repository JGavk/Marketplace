package view;

import controller.WelcomeController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeView extends JFrame{
    private JPanel mainPanel;
    private JLabel label1;
    private JTextField nombreField;
    private JPanel DPanel;
    private JButton ingresarButton;
    private WelcomeController firstController;

    public WelcomeView (){
       setTitle("Product Manager");
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       setSize(500, 600);
       setLocationRelativeTo(null);
       setVisible(true);
       setContentPane(mainPanel);

       label1.setPreferredSize(new Dimension(400, 200));
       label1.setIcon(new ImageIcon("src/images/Frutas.jpg"));
       nombreField.setPreferredSize(new Dimension(100,100));
       ingresarButton.addActionListener(this::actionPerformed);
       //Mensaje Prueba
    }

        public void actionPerformed (ActionEvent e){
            if(e.getSource()==ingresarButton){

            }

        }
}
