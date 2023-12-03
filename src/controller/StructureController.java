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
        int quantity = Integer.parseInt(quantityField.getText()); //Se necesita un cambio para unirlo a la clase Producto
        String itemName = productField.getText();
        this.product = new Product(quantity, itemName);
        structure.addBought(product);
        System.out.println(itemName);  // Souts de prueba
        System.out.println(quantity);
        System.out.println(product);

    }

    public void updateItemsFromTable(DefaultTableModel table) {
        table.addRow(new Object[]{product.getItemName(), product.getQuantity()
        });
    }

    public void printTxt() throws IOException {
        structure.printBought();

    }
}
