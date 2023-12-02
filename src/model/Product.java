package model;

import java.io.Serializable;

public class Product implements Serializable {
    public static final long serialVersionUID = 1L;
    private int quantity, price;
    private String itemName;

    public Product(int quantity, int price, String itemName){
        this.quantity = quantity;
        this.price = price;
        this.itemName = itemName;
    }
//Metodo Setter y getter de la cantidad, precio y nombbre del item a comprar para utilizar en archivos .txt
    public void setPrice(int price) {
        this.price = price;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
//-------------------------------------------------------------------------------------------------
    public int getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }
    public String getItemName() {
        return itemName;
    }
}
