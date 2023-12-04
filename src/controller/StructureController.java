package controller;
import logic.Structure;
import model.Product;
import view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StructureController {
    public StructureView sView;
    public Product product;
    public Structure structure;
    public StructureController(StructureView sView){
        this.sView = sView;
        this.structure = new Structure();

        sView.addBuyButtonListener(new buyButtonListener());
    }


    public void addItemToCart(JTextField quantityField, JTextField productField, DefaultTableModel table) {

        //
        int quantity = Integer.parseInt(quantityField.getText()); //Se necesita un cambio para unirlo a la clase Producto
        String itemName = productField.getText();

        // Verificar si en el txtField nombre si hay un producto con esa key en el hashmap
        Product product = structure.searchProduct(itemName);
        if (product != null){
            structure.addBought(itemName, quantity ,product.getPrice()*quantity);
            System.out.println("Product added to shopping cart");
            table.addRow(new Object[]{product.getItemName(), quantity, product.getPrice()*quantity});
        } else {
            System.out.println("Error, no agregado");
        }
        System.out.println(itemName);  // Souts de prueba
        System.out.println(quantity);

    }


    class buyButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Quantity field: " + sView.getQuantityField().getText());
            structure.printBought();
            structure.clearItemArray();

        }
    }
}