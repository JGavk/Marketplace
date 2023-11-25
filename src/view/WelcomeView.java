package view;

import javax.swing.*;
import java.awt.*;

public class WelcomeView extends JFrame{
    private JPanel mainPanel;
    private JLabel label1;

   public WelcomeView (){
       setTitle("Product Manager");
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       setSize(500, 700);
       setVisible(true);
       setContentPane(mainPanel);

       label1.setPreferredSize(new Dimension(400, 200));
   }
}
