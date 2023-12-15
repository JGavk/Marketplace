package controller;
import logic.InventoryFileDB;
import logic.ProviderFileDB;
import logic.Structure;
import model.Client;
import model.Product;
import model.Provider;
import view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Map;

import static logic.Structure.inventoryItems;

public class StructureController {
    public StructureView sView;
    public Structure structure;
    public StructureController(StructureView sView){
        this.sView = sView;
        this.structure = new Structure();

        listarProviders();
        sView.addBuyButtonListener(new BuyButtonListener());
        sView.addAddProvButtonListener(new AddProvButtonListener());
        sView.addAbastecerButtonListener(new AbastecerButtonListener());
        sView.addActProvButtonListener(new ActProvButtonListener());
        sView.addEliProvButtonListener(new EliProvButtonListener());
        sView.addAddProdButtonListener(new AddProdButtonListener());
        sView.addEliProdButtonListener(new EliProdButtonListener());
        sView.addActProdButtonListener(new ActProdButtonListener());
    }

//Metodo de añadir item al arreglo
    public void addItemToCart(JTextField quantityField, JTextField productField, DefaultTableModel table) {

        //
        try{
        int quantity = Integer.parseInt(quantityField.getText());
        String itemName = productField.getText();

        // Verificar si en el txtField nombre si hay un producto con esa key en el hashmap
        Product product = structure.searchProduct(itemName);
        if (product != null){
            structure.addBought(itemName, quantity ,product.getPrice()*quantity);
            table.addRow(new Object[]{product.getItemName(), quantity, product.getPrice()*quantity});
            JOptionPane.showMessageDialog(null, "Producto Agregado!");
            sView.getBuyButton().setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(sView.getComponent(0), "No hay stock de tal item!", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(sView.getComponent(0), "Error: Quantity must be a valid integer", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteAThing(String selectedName) {
        structure.popProductArray(selectedName);
        sView.deleteSelectedItem();

    }
//Metodo update controlado
    public void updateThing(String selectedName, int intValue, DefaultTableModel itemTable, int selectedRow) {
        Product item2 = null;
        Product product = structure.getProductByName(selectedName); //Toma un producto y compara para saber su existencia, envia datos
        if (product != null) {
            item2 = structure.updateProduct(selectedName, intValue);//Envia datos de cambio
            if(item2 !=null) {
                itemTable.setValueAt(item2.getCantidad(),selectedRow,1);
                itemTable.setValueAt(item2.getPrice(),selectedRow,2);
            }

        }

    }

    public void chargeInventory(DefaultTableModel inventoryTable) {
        // Elimina todas las filas existentes en la tabla
        inventoryTable.setRowCount(0);

        for (Map.Entry<String, Product> entry : inventoryItems.entrySet()) {
            Object[] rowData = {
                    entry.getKey(),
                    entry.getValue().getPrice()
            };
            inventoryTable.addRow(rowData);
        }
    }
    public void listarProviders(){
        while (sView.getProvidorTable().getRowCount() > 0) {
            sView.getProvidorTable().removeRow(0);
        }
        if (!structure.getProviders().isEmpty()){
            for (Map.Entry<String, Provider> entry : structure.getProviders().entrySet()) {
                String prodName = entry.getValue().getProduct().getItemName();
                String prodPrice = Double.toString(entry.getValue().getProduct().getPrice());
                sView.getProvidorTable().addRow(new Object[]{entry.getKey(), prodName, prodPrice});
           }
        }
    }
    public void listarProducts(){
        while (sView.getInventoryTableD().getRowCount() > 0) {
            sView.getInventoryTableD().removeRow(0);
        }
        if (!Structure.getInventoryItems().isEmpty()){
            for (Map.Entry<String, Product> entry : Structure.getInventoryItems().entrySet()) {
                String prodName = entry.getValue().getItemName();
                String prodPrice = Double.toString(entry.getValue().getPrice());
                sView.getInventoryTableD().addRow(new Object[]{entry.getKey(), prodName, prodPrice});
            }
        }
    }

    public void addProvider(String providerName, String productName, double productPrice){
        if (structure.getProviders().containsKey(providerName)) {
            JOptionPane.showMessageDialog(sView.getComponent(0), "Ya existe el proveedor", "Input Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir de la función para evitar la duplicación
        }
        Product providerProduct = new Product(productName, 0, productPrice);
        structure.addProvider(new Provider(providerName,providerProduct));
        listarProviders();
    }
    public void abastecerInventario(){
        // Obtener la fila seleccionada
        int selectedRow = sView.getTableProvider().getSelectedRow();

        // Verificar si hay una fila seleccionada
        if (selectedRow != -1) {

            String name = (String) sView.getTableProvider().getValueAt(selectedRow, 0);
            String product = (String) sView.getTableProvider().getValueAt(selectedRow, 1);
            String stringPrice = (String) sView.getTableProvider().getValueAt(selectedRow, 2);

            // Utilizar los datos para abastecer el producto
            Product producto = new Product(product,0, Double.parseDouble(stringPrice));
            inventoryItems.put(producto.getItemName(),producto);
            chargeInventory(sView.getInventoryTableD());
            InventoryFileDB.saveProductsToFile(inventoryItems);
            System.out.println("Abasteciendo producto: " + name + ", " + product + ", " + stringPrice);
        } else {
            JOptionPane.showMessageDialog(sView.getComponent(0), "Error: No provider has been selected", "Select Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void delProvider(){
        // Obtener la fila seleccionada
        int selectedRow = sView.getTableProvider().getSelectedRow();

        // Verificar si hay una fila seleccionada
        if (selectedRow != -1) {
            // Datos que tiene la fila para mostrar el prov eliminando
            String name = (String) sView.getTableProvider().getValueAt(selectedRow, 0);

            // funcion para eliminar un proveedor de la lista y del hashmap
            sView.getProvidorTable().removeRow(selectedRow);
            structure.delProvider(name);
            listarProviders();
            ProviderFileDB.saveProviderToFile(structure.getProviders());
        } else {
            JOptionPane.showMessageDialog(sView.getComponent(0), "Error: No provider has been selected", "Select Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void addProduct(String productName, double productPrice){
        if (inventoryItems.containsKey(productName)) {
            JOptionPane.showMessageDialog(sView.getComponent(0), "Ya existe el Producto", "Input Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir de la función para evitar la duplicación
        }
        Product product = new Product(productName, 0, productPrice);
        structure.addProduct(product);
    }
    public void delProduct(){
        // Obtener la fila seleccionada
        int selectedRow = sView.getInventoryTable().getSelectedRow();

        // Verificar si hay una fila seleccionada
        if (selectedRow != -1) {
            // Datos que tiene la fila para mostrar el prov eliminando
            String name = (String) sView.getInventoryTable().getValueAt(selectedRow, 0);

            // funcion para eliminar un proveedor de la lista y del hashmap
            sView.getInventoryTableD().removeRow(selectedRow);
            structure.delProduct(name);
            chargeInventory(sView.getInventoryTableD());
            InventoryFileDB.saveProductsToFile(inventoryItems);
        } else {
            JOptionPane.showMessageDialog(sView.getComponent(0), "Error: No Product has been selected", "Select Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void setPlaceholder(JTextField textField, String placeholder) {
        // Establecer el color de texto y el texto del marcador de posición
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Eliminar el texto del marcador de posición al ganar el foco
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restaurar el texto del marcador de posición si el campo está vacío al perder el foco
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
    }
    //Metodo de compra
    class BuyButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            sView.showClientForm();
            int result = JOptionPane.showConfirmDialog(
                    sView.getContentPane(),
                    sView.getClientePanel(),
                    "Ingrese la Información del Cliente",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    // manejar la información ingresada en los JTextField
                    String clientName = sView.getClienteName().getText();
                    int clientId = Integer.parseInt(sView.getClienteId().getText());

                    // Realizar las acciones necesarias con la información...
                    ArrayList<Product> clientProducts = new ArrayList<>();
                    DefaultTableModel dtm = sView.getItemTable();

                    for (int row = 0; row < dtm.getRowCount(); row++){
                        String name = (String) dtm.getValueAt(row, 0);
                        int quantity = (int) dtm.getValueAt(row, 1);
                        double price = (double) dtm.getValueAt(row, 2);


                        Product product = new Product(name, quantity, price);
                        clientProducts.add(product);
                    }
                    for (Product product : clientProducts) {
                        System.out.println(product);
                    }
                    Client cliente = new Client(clientId, clientName, clientProducts);
                    structure.printBought(cliente);
                    structure.clearItemArray();
                    sView.refreshTableAfterBuy();
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(sView.getComponent(0), "Error: Price must be a valid Number", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }
    class AddProvButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            sView.showAddProvPanel();
            int result = JOptionPane.showConfirmDialog(
                    sView.getContentPane(),
                    sView.getAgregarPanel(),
                    "Ingrese la Información del Proveedor",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    // manejar la información ingresada en los JTextField
                    String provName = sView.getAddProvName().getText();
                    String productName = sView.getAddProductName().getText();
                    int productPrice = Integer.parseInt(sView.getAddProductPrice().getText());

                    // Realizar las acciones necesarias con la información...
                    addProvider(provName, productName, productPrice);
                    chargeInventory(sView.getInventoryTableD());
                    ProviderFileDB.saveProviderToFile(structure.getProviders());

                } catch (NumberFormatException exception){
                    JOptionPane.showMessageDialog(sView.getComponent(0), "Error: Price must be a valid Number", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    class AbastecerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            abastecerInventario();
        }
    }
    class ActProvButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            // Obtener la fila seleccionada
            int selectedRow = sView.getTableProvider().getSelectedRow();

            // Verificar si hay una fila seleccionada
            if (selectedRow != -1) {
                sView.showActProvPanel();
                // Datos que tiene la fila para mostrar el prov actualizando
                String name = (String) sView.getTableProvider().getValueAt(selectedRow, 0);
                String product = (String) sView.getTableProvider().getValueAt(selectedRow, 1);
                String stringPrice = (String) sView.getTableProvider().getValueAt(selectedRow, 2);

                sView.getInfoProvName().setText("Nombre: " + name);
                sView.getInfoProdName().setText("Producto: " + product);
                sView.getInfoProdPrice().setText("Precio: " + stringPrice);

                Provider provider = structure.getProvider(name);

                int result = JOptionPane.showConfirmDialog(
                        sView.getContentPane(),
                        sView.getActualizarPanel(),
                        "Ingrese la Información del Proveedor",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    if(provider != null){
                        try {
                            // manejar la información ingresada en los JTextField para actualizar el proveedor
                            String provName = sView.getAddProvName().getText();
                            String productName = sView.getAddProductName().getText();
                            int productPrice = Integer.parseInt(sView.getAddProductPrice().getText());

                            // Realizar las acciones necesarias con la información...
                            Product prod = new Product(productName,0,productPrice);

                            // Remueve el proveedor anterior
                            structure.getProviders().remove(name);

                            // Pone el proveedor actualizado
                            Provider prov = new Provider(provName, prod);
                            structure.getProviders().put(prov.getName(),prov);

                            // debuggear el hashmap de proveedores, que no se repita ni se modifique al agregar repetido
                            System.out.println("Contenido de proveedores:");

                            for (Map.Entry<String, Provider> entry : structure.getProviders().entrySet()) {
                                System.out.println("Proveedor: " + entry.getKey() + ", Producto: " + entry.getValue().getProduct().getItemName());
                            }
                            listarProviders();
                            ProviderFileDB.saveProviderToFile(structure.getProviders());
                        } catch (NumberFormatException exception){
                            JOptionPane.showMessageDialog(sView.getComponent(0), "Error: Price must be a valid Number", "Input Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }else{
                        JOptionPane.showMessageDialog(sView.getComponent(0), "Error: Provider doesn't exist", "Provider Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(sView.getComponent(0), "Error: No provider has been selected", "Select Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    class EliProvButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            delProvider();
        }
    }
    class AddProdButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            sView.showAddProdPanel();
            int result = JOptionPane.showConfirmDialog(
                    sView.getContentPane(),
                    sView.getAddProdPane(),
                    "Ingrese la Información del Producto",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    // manejar la información ingresada en los JTextField
                    String prodName = sView.getAddProdName().getText();
                    int prodPrice = Integer.parseInt(sView.getAddProdPrice().getText());

                    // Realizar las acciones necesarias con la información...
                    addProduct(prodName,prodPrice);
                    chargeInventory(sView.getInventoryTableD());
                    InventoryFileDB.saveProductsToFile(inventoryItems);
                } catch (NumberFormatException exception){
                    JOptionPane.showMessageDialog(sView.getComponent(0), "Error: Price must be a valid Number", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    class ActProdButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // Obtener la fila seleccionada
            int selectedRow = sView.getInventoryTable().getSelectedRow();

            // Verificar si hay una fila seleccionada
            if (selectedRow != -1) {
                sView.showActProdPanel();
                // Datos que tiene la fila para mostrar el prod actualizando
                String name = (String) sView.getInventoryTable().getValueAt(selectedRow, 0);
                double price = (double) sView.getInventoryTable().getValueAt(selectedRow, 1);

                sView.getProdName().setText("Nombre: " + name);
                sView.getProdPrice().setText("Precio: " + price);

                Product product = structure.getProduct(name);

                int result = JOptionPane.showConfirmDialog(
                        sView.getContentPane(),
                        sView.getActProdPane(),
                        "Ingrese la Nueva Información del Producto",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    if(product != null){
                        try {
                            // manejar la información ingresada en los JTextField para actualizar el proveedor
                            String prodName = sView.getTfProdName().getText();
                            int prodPrice = Integer.parseInt(sView.getTfProdPrice().getText());

                            // Realizar las acciones necesarias con la información...

                            // Remueve el producto anterior
                            inventoryItems.remove(name);

                            // Pone el proveedor actualizado
                            Product prod = new Product(prodName,0,prodPrice);
                            inventoryItems.put(prod.getItemName(),prod);

                            // debuggear el hashmap de productos, que no se repita ni se modifique al agregar repetido
                            System.out.println("Contenido de producto:");

                            for (Map.Entry<String, Provider> entry : structure.getProviders().entrySet()) {
                                System.out.println("producto: " + entry.getKey() + ", precio: " + entry.getValue().getProduct().getItemName());
                            }
                            chargeInventory(sView.getInventoryTableD());
                            InventoryFileDB.saveProductsToFile(inventoryItems);
                        } catch (NumberFormatException exception){
                            JOptionPane.showMessageDialog(sView.getComponent(0), "Error: Price must be a valid Number", "Input Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }else{
                        JOptionPane.showMessageDialog(sView.getComponent(0), "Error: Product doesn't exist", "Product Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(sView.getComponent(0), "Error: No Product has been selected", "Select Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    class EliProdButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            delProduct();
        }
    }
}