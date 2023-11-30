package view;

import javax.swing.*;
import java.awt.*;

public class StructureView extends JFrame {

    private JPanel mainSPanel;
    public JLabel nameLabel;
    private JTabbedPane tabbedPane1;
    private JPanel compradorPanel;
    private JPanel proveedorPanel;
    private String nameField;

    public StructureView(){
        setSize(700,700);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainSPanel);
        setResizable(false);

        //inputPanel.setBackground(Color.CYAN);

    }

}
