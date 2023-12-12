package controller;
import logic.Structure;
import model.Product;
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
        sView.addAddProvButtonListener(new ProvButtonListener());
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

    //Metodo de compra
    class buyButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            structure.printBought();
            structure.clearItemArray();
            sView.refreshTableAfterBuy();

        }
    }
    class ProvButtonListener implements ActionListener {

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
                // manejar la información ingresada en los JTextField
                String provName = sView.getAddProvName().getText();
                String productName = sView.getAddProductName().getText();

                // Realizar las acciones necesarias con la información...
                System.out.println("Provider Name: " + provName);
                System.out.println("Provider Product´s Name: " + productName);
            }
            System.out.println("Mostrando agregar");

        }
    }
}