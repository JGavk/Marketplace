package logic;
import model.*;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Structure implements Serializable {
    public static final long serialVersionUID = 1L;
    private ArrayList<Product> productArrayList; // muestra los datos en la patalla
    private HashMap<String, Product> inventoryItems;


    public Structure(){
        productArrayList = new ArrayList<>();
        inventoryItems = new HashMap<>();

        //InventoryFileDB.saveProductsToFile(inventoryItems, "src/files/inventory_data.txt");
        InventoryFileDB.loadInventoryData(this,"src/files/inventory_data.txt");
    }

    //Compra del producto y añadir al arreglo de productos
    public void addBought(String itemName, int amount, double price) {

        Product product = searchProduct(itemName);
        // Verifica si se agregó el producto al carrito
        if (product != null){
            productArrayList.add(new Product(inventoryItems.get(itemName).getItemName(), amount, price));
            System.out.println(productArrayList); // Probar si el arreglo sirve
        } else {
            System.out.println("No hay stock del producto");
        }
    }
    //Metodo para remover un item del arreglo
    public void popProductArray(String itemName){
        Product product = searchCarProduct(itemName);
        if (product != null){
            boolean removed = productArrayList.remove(product);
            System.out.println(productArrayList);
            System.out.println("removed   " + removed);
        }else {
            System.out.println("No se pudo eliminar");
        }
    }

//Metodo de update item del arreglo dando nueva cantidad y precio
    public void updateProduct(String itemName, int quantity) {
        for (Product product : productArrayList) {
            if (product != null && product.getItemName().equals(itemName)) {
                double price = inventoryItems.get(itemName).getPrice();
                product.setCantidad(quantity);
                product.setPrice(price * quantity);
                System.out.println("que tal");
                System.out.println(productArrayList);
                break;
            }
        }
    }
//getter del arreglo para pruebas
    public ArrayList<Product> getItemArray(){
        System.out.println(productArrayList);
        return productArrayList;
    }
    //Metodo de limpieza
    public void clearItemArray(){
        productArrayList.clear();
    }

    //Metodo de busqueda en HashMap
    public Product searchProduct(String itemName){
        Product producto = inventoryItems.get(itemName);
        // verifica si existe el producto
        if (producto != null){
            System.out.println("Se encontró el producto");
            return producto;
        } else {
            System.out.println("No hay stock del producto");
            return null;
        }
    }
//Metodo de deleteo item en arreglo
    public Product searchCarProduct(String itemName){
        ArrayList<Product> exist = new ArrayList<>();
        productArrayList.forEach(obj -> {
            if ((obj.getItemName()).equals(itemName)) {
                exist.add(obj);
            }
        });

        if (!exist.isEmpty()) {
            return exist.get(0);
        } else {
            return  null;
        }
    }

    //Busqueda en el arreglo
    public Product getProductByName(String name) {
        for (Product product : productArrayList) {
            if (product != null && product.getItemName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    //Creador de txt para archivo de compra de uno o más objetos
    public void printBought()  {

        try {
            UUID transactionNum = UUID.randomUUID();
            FileWriter dataText = new FileWriter("src/files/sell_" +
                    transactionNum + ".txt");
            PrintWriter pw = new PrintWriter(dataText);
            pw.println("Transaction number  : " + transactionNum);
            pw.println("Item             |      Amount              |    Price  " );

            productArrayList.forEach(obj -> {
                pw.println(obj.getItemName() +  "                    " + obj.getCantidad() + "                    " + obj.getPrice());
            });
            if (dataText != null){
                dataText.close();
            }else {
                System.out.println("hola");
            }

        } catch (IOException e2) {
            System.out.println("si es este");
            e2.printStackTrace();
        }
    }

    public void updateInventoryItems(HashMap<String, Product> newItems) {
        inventoryItems.putAll(newItems);
    }

}
