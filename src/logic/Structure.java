package logic;
import model.*;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
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

        Product tomate = new Product("tomate", 1000.0);
        Product cebolla = new Product("cebolla", 1200.0);
        Product arroz = new Product("arroz", 2000.0);

        inventoryItems.put(tomate.getItemName(), tomate);
        inventoryItems.put(cebolla.getItemName(), cebolla);
        inventoryItems.put(arroz.getItemName(), arroz);
    }

    //Compra del producto y añadir al arreglo de productos
    public boolean addBought(String itemName) {

        Product product = searchProduct(itemName);
        // Hay que verficar
        if (product != null){
            System.out.println("Se encontró el producto");
            productArrayList.add(inventoryItems.get(itemName));
            System.out.println(productArrayList); // Probar si el arreglo sirve
            return true;
        } else {
            System.out.println("No hay stock del producto");
            return false;
        }


    }

    public Product searchProduct(String itemName){
        Product producto = inventoryItems.get(itemName);
        if (producto != null){
            System.out.println("Se encontró el producto");
            return producto;
        } else {
            System.out.println("No hay stock del producto");
            return null;
        }
    }

    //Creador de txt para archivo de compra de uno o más objetos
    public void printBought() throws IOException {

        UUID transactionNum = UUID.randomUUID();
        FileWriter dataText = new FileWriter("src/files/sell_" +
                transactionNum + ".txt");
        PrintWriter pw = new PrintWriter(dataText);
        pw.println("Transaction number  : " + transactionNum);
        pw.println("Item             |      Amount              |    Price  " );

        productArrayList.forEach(obj -> {
            pw.println(obj.getItemName() +  "                    "  + obj.getQuantity()+ "                    "+"0");
        });


        try {
            if (dataText != null)
                dataText.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
