package view;

import javax.swing.*;
import java.awt.*;

public class StructureView extends JFrame {

    private JPanel mainSPanel;
    private JPanel inputPanel;

    public StructureView(){
        setSize(700,700);
        setLocationRelativeTo(null);
        setVisible(true);
        setContentPane(mainSPanel);

        inputPanel.setBackground(Color.black);
    }


}
