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

        // Productos para ensayo *ver json inventario"
        Product tomate = new Product("tomate", 0, 1000.0);
        Product cebolla = new Product("cebolla", 0, 1200.0);
        Product arroz = new Product("arroz", 0, 2000.0);

        inventoryItems.put(tomate.getItemName(), tomate);
        inventoryItems.put(cebolla.getItemName(), cebolla);
        inventoryItems.put(arroz.getItemName(), arroz);
    }

    //Compra del producto y añadir al arreglo de productos
    public boolean addBought(String itemName, int amount, double price) {

        Product product = searchProduct(itemName);
        // Verifica si se agregó el producto al carrito
        if (product != null){
            System.out.println("Se encontró el producto");
            productArrayList.add(new Product(inventoryItems.get(itemName).getItemName(), amount, price));
            System.out.println(productArrayList); // Probar si el arreglo sirve
            return true;
        } else {
            System.out.println("No hay stock del producto");
            return false;
        }


    }

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
}
