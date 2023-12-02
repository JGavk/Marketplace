package view;

import controller.StructureController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;

public class StructureView extends JFrame {
    private StructureController controller;
    private JPanel mainSPanel;
    public JLabel nameLabel;
    private JTabbedPane tabbedPane1;
    private JPanel compradorPanel;
    private JPanel proveedorPanel;
    private JPanel cbtnPanel, tablePanel;
    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;
    private JButton buyButton;
    private JTextField productField;
    private JTextField quantityField;
    private JPanel inventoryPanel;

    private DefaultTableModel itemTable, inventoryTable;
    private String nameField;

    public StructureView(){
        //Caracteristicas de la ventana
        setSize(700,700);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainSPanel);
        setResizable(false);

        //Caracteristicas de la tabla de productos
        itemTable = new DefaultTableModel();
        itemTable.addColumn("Product");
        itemTable.addColumn("Quantity");
        itemTable.addColumn("Total Price");
        JTable table = new JTable(itemTable);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane);
        table.setModel(itemTable);
        table.setDefaultEditor(Object.class, null);

        //Tabla de inventario en la TabPanel de inventario
        inventoryTable = new DefaultTableModel();
        inventoryTable.addColumn("Product");
        inventoryTable.addColumn("Quantity");
        inventoryTable.addColumn("Price");
        JTable table1 = new JTable(inventoryTable);
        JScrollPane scrollPane2 = new JScrollPane(table1);
        inventoryPanel.add(scrollPane2);
        table1.setModel(inventoryTable);
        table1.setDefaultEditor(Object.class, null);
        addButton.addActionListener(this::actionPerformed);
    }

    private void actionPerformed(ActionEvent e) {   //Agregar primer listener al borton de acción Añadir, recolectando el texto de los textFields
        if(e.getSource()==addButton){
            int input = Integer.parseInt(quantityField.getText());
            String itemName = productField.getText();
            System.out.println(itemName);   //Sout's de prueba
            System.out.println(input);
            controller.addItemToCart();
        }

    }
    //Setter del segundo Controlador
    public void setStructureController(StructureController controller) {
        this.controller = controller;
    }
}
