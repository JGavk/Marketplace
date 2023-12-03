package controller;
import model.Product;
import view.*;

import javax.swing.*;

public class StructureController {
    public StructureView sView;
    public Product product;
    public StructureController(){
        this.sView = sView;
        this.product = product;
    }


    public void addItemToCart(JTextField quantityField, JTextField productField) {
        int input = Integer.parseInt(quantityField.getText()); //Se necesita un cambio para unirlo a la clase Producto
        String itemName = productField.getText();
        System.out.println(itemName);  // Souts de prueba
        System.out.println(input);

        //Product product = new Product();
    }
}
