package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    public static final long serialVersionUID = 1L;
    private double price;
    private String itemName;

    public Product(String itemName, double price){
        this.itemName = itemName;
        this.price = price;
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

<<<<<<< HEAD
=======
 /*   public int getNumberSell() {
        return numberSell;
    } */

>>>>>>> 1a06ab3b257b30eed3b7f385b83d5d1d73b04394
    public String listToString(ArrayList<Product> list) {
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(obj -> {
            stringBuilder.append(obj).append(", ");
        });

        return stringBuilder.toString();
    }

    @Override
    public String toString() {

        return
                        "Item name: " + getItemName() + '\n' +
                        "Price: " + getPrice()+ '\n';
    }

}
