package controller;
import logic.Structure;
import model.Product;
import model.Provider;
import view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import static logic.Structure.inventoryItems;

public class StructureController {
    public StructureView sView;
    public Structure structure;
    public StructureController(StructureView sView){
        this.sView = sView;
        this.structure = new Structure();

        sView.addBuyButtonListener(new buyButtonListener());
        sView.addAddProvButtonListener(new AddProvButtonListener());
        sView.addAbastecerButtonListener(new abastecerButtonListener());
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
        for (Map.Entry<String, Product> entry : inventoryItems.entrySet()) {
            Object[] rowData = {
                    entry.getKey(),
                    entry.getValue().getPrice()
            };
            inventoryTable.addRow(rowData);
        }
    }

    public void addProvider(String providerName, String productName, double productPrice){
        if (structure.getProviders().containsKey(providerName)) {
            JOptionPane.showMessageDialog(sView.getComponent(0), "Ya existe el proveedor", "Input Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir de la función para evitar la duplicación
        }
        Product providerProduct = new Product(productName, 0, productPrice);
        structure.addProvider(new Provider(providerName,providerProduct));
        while (sView.getProvidorTable().getRowCount() > 0) {
            sView.getProvidorTable().removeRow(0);
        }
        for (Map.Entry<String, Provider> entry : structure.getProviders().entrySet()) {
            String prodName = entry.getValue().getProduct().getItemName();
            String prodPrice = Double.toString(entry.getValue().getProduct().getPrice());
            sView.getProvidorTable().addRow(new Object[]{entry.getKey(), prodName, prodPrice});
        }
    }

    public void abastecerInventario(){

    }

    //Metodo de compra
    class buyButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            structure.printBought();
            structure.clearItemArray();
            sView.refreshTableAfterBuy();

        }
    }
    class AddProvButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            sView.showAddProvPanel();
            int result = JOptionPane.showConfirmDialog(
                    sView.getContentPane(),
                    sView.getFormularioPanel(),
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

                    // debuggear el hashmap de proveedores, que no se repita ni se modifique al agregar repetido
                    System.out.println("Contenido de proveedores:");

                    for (Map.Entry<String, Provider> entry : structure.getProviders().entrySet()) {
                        System.out.println("Proveedor: " + entry.getKey() + ", Producto: " + entry.getValue().getProduct().getItemName());
                    }
                } catch (NumberFormatException exception){
                    JOptionPane.showMessageDialog(sView.getComponent(0), "Error: Price must be a valid Number", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            System.out.println("Mostrando agregar");

        }
    }

    class abastecerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Obtener la fila seleccionada
            int selectedRow = sView.getTableProvider().getSelectedRow();

            // Verificar si hay una fila seleccionada
            if (selectedRow != -1) {
                String name = (String) sView.getTableProvider().getValueAt(selectedRow, 0);
                String product = (String) sView.getTableProvider().getValueAt(selectedRow, 1);
                String price = (String) sView.getTableProvider().getValueAt(selectedRow, 2);

                // Utilizar los datos para abastecer el producto
                System.out.println("Abasteciendo producto: " + name + ", " + product + ", " + price);
            } else {
                JOptionPane.showMessageDialog(sView.getComponent(0), "Error: No product has been selected", "Select Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}