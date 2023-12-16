/*
Juan Pablo Puerta Gavira 2240033
Yeimer Armando Mendez Sanchez 2243583
Miguel Angel Soto
* */
package model;

import java.util.ArrayList;

public class Client {
    private int id;
    private String name;
    private ArrayList<Product> products;

    public Client(int id, String name,ArrayList<Product> products){
        this.id = id;
        this.name = name;
        products = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
