package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    public static final long serialVersionUID = 1L;
    private int quantity, price, numberSell;
    private String itemName;
    private ArrayList<TxtData> dataArchiveList;

    public Product(int quantity, String itemName){
        this.quantity = quantity;
        //this.price = price;
        this.itemName = itemName;
        this.numberSell = numberSell;
    }
//Metodo Setter y getter de la cantidad, precio y nombre del item a comprar para utilizar en archivos .txt
    public void setPrice(int price) {
        this.price = price;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public void setDataArchiveList(ArrayList<TxtData> dataArchiveList) {
        this.dataArchiveList = dataArchiveList;
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

    public int getNumberSell() {
        return numberSell;
    }
    public ArrayList<TxtData> getDataArchiveList() {
        return dataArchiveList;
    }


}
