package view;

import controller.StructureController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.awt.event.KeyListener;
import java.io.IOException;

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
    private JTable table;
    private String nameField, selectedName;
    private int selectedRow, selectedQ;
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
        table = new JTable(itemTable);
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
        //buyButton.addActionListener(this::actionPerformed);
        buyButton.setEnabled(false);
        deleteButton.addActionListener(this::actionDone);
        editButton.addActionListener(this::actionPerformed2);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = table.rowAtPoint(e.getPoint());
                selectedRow = table.getSelectedRow();

                // Obtener el objeto correspondiente del modelo
                if (row >= 0) {
                    Object rowData = itemTable.getDataVector().elementAt(row);
                    selectedName = (String) table.getValueAt(selectedRow,0);
                    selectedQ = (int) table.getValueAt(selectedRow, 1);
                    System.out.println(selectedName);
                    System.out.println(selectedQ);
                    System.out.println("Objeto seleccionado: " + rowData);
                }
            }
        });


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

    private void actionPerformed2(ActionEvent e3) {
        String quantityInput = JOptionPane.showInputDialog("Ingrese cantidad ");
        try {
            int intValue = Integer.parseInt(quantityInput);

            controller.updateThing(this.selectedName, intValue);
        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actionDone(ActionEvent e2) {
        if (this.selectedName != null) {
            controller.deleteAThing(this.selectedName);
            this.selectedName = null;
        } else {
            JOptionPane.showMessageDialog(null, "Select something!! ", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteSelectedItem(){
        itemTable.removeRow(selectedRow);
    }

    public void refreshTableAfterBuy(){
        itemTable.setRowCount(0);
    }

    private void actionPerformed(ActionEvent e) {   //Agregar primer listener al borton de acción Añadir, recolectando el texto de los textFields
        if(e.getSource()==addButton) {
            controller.addItemToCart(quantityField, productField, itemTable);
            quantityField.setText("");
            productField.setText("");
            buyButton.setEnabled(true);
        }

    }
    //Setter del segundo Controlador
    public void setStructureController(StructureController controller) {
        this.controller = controller;
    }

    public JTextField getQuantityField() {
        return quantityField;
    }

    public void addBuyButtonListener(ActionListener listener){
        buyButton.addActionListener(listener);
    }
}