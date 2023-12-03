package controller;
import logic.Structure;
import model.Product;
import view.*;

import javax.swing.*;

public class StructureController {
    public StructureView sView;
    public Product product;
    public Structure structure;
    public StructureController(StructureView sView){
        this.sView = sView;
        this.product = product;
        this.structure = new Structure();
    }


    public void addItemToCart(JTextField quantityField, JTextField productField) {
        int quantity = Integer.parseInt(quantityField.getText()); //Se necesita un cambio para unirlo a la clase Producto
        String itemName = productField.getText();
        Product product = new Product(quantity, itemName);
        structure.addBought(product);
        System.out.println(itemName);  // Souts de prueba
        System.out.println(quantity);
        System.out.println(product);

        //Product product = new Product();
    }
}
