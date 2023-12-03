package controller;
import logic.Structure;
import model.Product;
import view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

public class StructureController {
    public StructureView sView;
    public Product product;
    public Structure structure;
    public StructureController(StructureView sView){
        this.sView = sView;
        this.structure = new Structure();
    }


    public void addItemToCart(JTextField quantityField, JTextField productField) {

        //
        int quantity = Integer.parseInt(quantityField.getText()); //Se necesita un cambio para unirlo a la clase Producto
        String itemName = productField.getText();

        // Verificar si en el txtField nombre si hay un producto con esa key en el hashmap
        if (structure.addBought(itemName)){
            System.out.println("Product added to shopping cart");
        } else {
            System.out.println("Error, no agregado");
        }
        System.out.println(itemName);  // Souts de prueba
        System.out.println(quantity);

    }

    public void updateItemsFromTable(DefaultTableModel table) {

        table.addRow(new Object[]{product.getItemName(), product.getQuantity()
        });
    }

    public void printTxt() throws IOException {
        structure.printBought();

    }
}
