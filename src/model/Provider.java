/*
Juan Pablo Puerta Gavira 2240033
Yeimer Armando Mendez Sanchez 2243583
Miguel Angel Soto
* */
package model;

public class Provider {
    private String name;
    private Product product;

    public Provider(String name, Product product) {
        this.name = name;
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    @Override
    public String toString() {
        return "name: " + name + '\n' +
                product + '\n';
    }
}
