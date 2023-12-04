package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    public static final long serialVersionUID = 1L;
    private double price;
    private String itemName;
    private int cantidad;
    public Product(String itemName, int cantidad, double price){
        this.itemName = itemName;
        this.price = price;
        this.cantidad = cantidad;
    }
    //Metodo Setter y getter de la cantidad, precio y nombre del item a comprar para utilizar en archivos .txt
    public void setPrice(int price) {
        this.price = price;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    //-------------------------------------------------------------------------------------------------
    public double getPrice() {
        return price;
    }
    public String getItemName() {
        return itemName;
    }
    public int getCantidad(){
        return cantidad;
    }
    public String listToString(ArrayList<Product> list) {
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(obj -> {
            stringBuilder.append(obj).append(", ");
        });

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        //String nameString = listToString(getItemName());
        //String quantityString = listToString(getQuantity());

        return
                "Item name: " + getItemName() + '\n' +
                        "Price: " + getPrice()+ '\n';
    }


}