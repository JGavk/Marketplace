package view;

import controller.StructureController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class StructureView extends JFrame {
    private final JTable tableProvider;
    private JPanel agregarPanel;
    private JTextField addProvName;
    private JTextField addProductName;
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
    private JButton btnCharge;
    private JPanel providPanel;
    private JButton abastecerProdcutoButton;
    private JButton agregarProveedorButton;
    private JButton actualizarProveedorButton;
    private JButton eliminarProveedorButton;

    private DefaultTableModel itemTable, inventoryTable, providorTable;
    private JTable table;
    private String nameField, selectedName;
    private int selectedRow, selectedQ;
    private JTextField addProductPrice;
    private JPanel actualizarPanel;
    private JLabel infoProvName;
    private JLabel infoProdName;
    private JLabel infoProdPrice;
    private JPanel clientePanel;
    private JTextField clienteName;
    private JTextField clienteId;

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
        inventoryTable.addColumn("Price");
        JTable table1 = new JTable(inventoryTable);
        JScrollPane scrollPane2 = new JScrollPane(table1);
        inventoryPanel.add(scrollPane2);
        table1.setModel(inventoryTable);
        table1.setDefaultEditor(Object.class, null);

        providorTable = new DefaultTableModel();
        providorTable.addColumn("Name");
        providorTable.addColumn("Product");
        providorTable.addColumn("Price");
        tableProvider = new JTable(providorTable);
        JScrollPane scrollPane3 = new JScrollPane(tableProvider);
        providPanel.add(scrollPane3);
        tableProvider.setModel(providorTable);
        tableProvider.setDefaultEditor(Object.class, null);

        addButton.addActionListener(this::actionPerformed);
        //buyButton.addActionListener(this::actionPerformed);
        buyButton.setEnabled(false);
        deleteButton.addActionListener(this::actionDone);
        btnCharge.addActionListener(this::actionPerformed3);
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
                }
            }
        });




        quantityField.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
    }

    private void actionPerformed3(ActionEvent e4) {
        controller.chargeInventory(inventoryTable);
    }

    private void actionPerformed2(ActionEvent e3) {
        String quantityInput = JOptionPane.showInputDialog("Ingrese cantidad ");
        try {
            int intValue = Integer.parseInt(quantityInput);
            controller.updateThing(this.selectedName, intValue, itemTable, selectedRow);
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

    public void showAddProvPanel(){
        agregarPanel = new JPanel();
        agregarPanel.add(new JLabel("Provider Name:"));
        addProvName = new JTextField(10);
        agregarPanel.add(addProvName);
        agregarPanel.add(new JLabel("Product:"));
        addProductName = new JTextField(10);
        agregarPanel.add(addProductName);
        agregarPanel.add(new JLabel("Product Price:"));
        addProductPrice = new JTextField(10);
        agregarPanel.add(addProductPrice);

    }
    public void showActProvPanel(){
        actualizarPanel = new JPanel();
        actualizarPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        infoProvName = new JLabel("");
        actualizarPanel.add(infoProvName, gbc);

        gbc.gridy = 1;
        infoProdName = new JLabel("");
        actualizarPanel.add(infoProdName, gbc);

        gbc.gridy = 2;
        infoProdPrice = new JLabel("");
        actualizarPanel.add(infoProdPrice, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        addProvName = new JTextField(10);
        controller.setPlaceholder(addProvName, "Update proveedor");
        actualizarPanel.add(addProvName, gbc);

        gbc.gridy = 1;
        addProductName = new JTextField(10);
        controller.setPlaceholder(addProductName, "Update producto");
        actualizarPanel.add(addProductName, gbc);

        gbc.gridy = 2;
        addProductPrice = new JTextField(10);
        controller.setPlaceholder(addProductPrice, "Update precio");
        actualizarPanel.add(addProductPrice, gbc);
    }
    public void showCLientForm(){
        clientePanel = new JPanel();
        clientePanel.add(new JLabel("Name:"));
        clienteName = new JTextField(10);
        clientePanel.add(clienteName);
        clientePanel.add(new JLabel("ID:"));
        clienteId = new JTextField(10);
        clientePanel.add(clienteId);
    }

    public JPanel getClientePanel() {
        return clientePanel;
    }

    public JTextField getClienteName() {
        return clienteName;
    }

    public JTextField getClienteId() {
        return clienteId;
    }

    public JPanel getActualizarPanel() {
        return actualizarPanel;
    }

    public JPanel getAgregarPanel() {
        return agregarPanel;
    }

    public JLabel getInfoProvName() {
        return infoProvName;
    }

    public JLabel getInfoProdName() {
        return infoProdName;
    }

    public JLabel getInfoProdPrice() {
        return infoProdPrice;
    }

    public JTextField getAddProvName() {
        return addProvName;
    }

    public JTextField getAddProductName() {
        return addProductName;
    }

    public JTextField getAddProductPrice() {
        return addProductPrice;
    }

    public DefaultTableModel getProvidorTable() {
        return providorTable;
    }

    public JTable getTableProvider() {
        return tableProvider;
    }

    public void addBuyButtonListener(ActionListener listener){
        buyButton.addActionListener(listener);
    }

    public void addAddProvButtonListener(ActionListener listener){
        agregarProveedorButton.addActionListener(listener);
    }
    public void addAbastecerButtonListener(ActionListener listener){
        abastecerProdcutoButton.addActionListener(listener);
    }
    public void addActProvButtonListener(ActionListener listener){
        actualizarProveedorButton.addActionListener(listener);
    }
    public void addEliProvButtonListener(ActionListener listener){
        eliminarProveedorButton.addActionListener(listener);
    }
}