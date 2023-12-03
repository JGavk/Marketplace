package view;

import controller.StructureController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


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
        buyButton.addActionListener(this::actionDone);
        quantityField.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {
                //Metodos abstractos para que solo acepte numeros
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //Metodos abstractos para que solo acepte numeros
            }
        });


    }

//Creacion de archivo de texto con los datos de la tabla mediante el controlador
    private void actionDone(ActionEvent actionEvent)  {
        try {
            controller.printTxt();
        } catch (Exception e) {
            System.out.println("ERROR FILE");
        }
    }
//Lleva los datos de los textField's al controlador para ser utilizados en la actualización de la itemTable
    private void actionPerformed(ActionEvent e) {   //Agregar primer listener al borton de acción Añadir, recolectando el texto de los textFields
        if(e.getSource()==addButton){
            controller.addItemToCart(quantityField, productField);
            quantityField.setText("");
            productField.setText("");
            updateItemTable(itemTable);
        }

    }
    //Setter del segundo Controlador
    public void setStructureController(StructureController controller) {
        this.controller = controller;
    }
//Actualiza la tabla con los datos de los textFields
    public void updateItemTable(DefaultTableModel itemTable) {
        controller.updateItemsFromTable(itemTable);
    }
}
