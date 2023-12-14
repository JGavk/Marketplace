package logic;
import model.*;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Structure implements Serializable {
    public static final long serialVersionUID = 1L;
    private ArrayList<Product> productArrayList; // muestra los datos en la patalla
    public static HashMap<String, Product> inventoryItems;
    private HashMap<String, Provider> providers;


    public Structure(){
        productArrayList = new ArrayList<>();
        inventoryItems = new HashMap<>();
        providers = new HashMap<>();


<<<<<<< HEAD
        /*
        Provider providerTest = new Provider("Granjas S.A.", new Product("pollo",0,12000.0));
        providers.put(providerTest.getName(),providerTest);
        Provider providerTests = new Provider("Farms", new Product("fresa",0,600.0));
        providers.put(providerTests.getName(),providerTests);
        Provider providerTestsS = new Provider("ORO", new Product("oro",0,20000.0));
        providers.put(providerTestsS.getName(),providerTestsS);
         */

        // File para inventario
        //InventoryFileDB.saveProductsToFile(inventoryItems, "src/files/inventory_data.txt");
=======
>>>>>>> 5cb1fbcfa51c364b7194f58812f7190e32c77d06
        InventoryFileDB.loadInventoryData(this,"src/files/inventory_data.txt");

        // File para proveedores
        try{
        providers.putAll(ProviderFileDB.procesarArchivo());
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    //Compra del producto y añadir al arreglo de productos
    public void addBought(String itemName, int amount, double price) {

        Product product = searchProduct(itemName);
        // Verifica si se agregó el producto al carrito
        if (product != null){
            productArrayList.add(new Product(inventoryItems.get(itemName).getItemName(), amount, price));
        }
    }
    //Metodo para remover un item del arreglo
    public void popProductArray(String itemName){
        Product product = searchCarProduct(itemName);
        if (product != null){
            boolean removed = productArrayList.remove(product);
        }
    }

//Metodo de update item del arreglo dando nueva cantidad y precio
    public Product updateProduct(String itemName, int quantity) {
        Product item = null;
        for (Product product : productArrayList) {
            if (product != null && product.getItemName().equals(itemName)) {

                double price = inventoryItems.get(itemName).getPrice();
                product.setCantidad(quantity);
                product.setPrice(price * quantity);
                item = product;

            }
        }
        return item;
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
            return producto;
        } else {
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

    public HashMap<String, Provider> getProviders() {
        return providers;
    }

    public void addProvider(Provider provider){
        if (provider != null){
            providers.put(provider.getName(), provider);
        }
    }
    public void delProvider(String providerName){
        providers.remove(providerName);
    }
    public Provider getProvider(String providerName){
        return providers.getOrDefault(providerName, null);
    }

    //Creador de txt para archivo de compra de uno o más objetos
    public void printBought(Client cliente)  {

        try {
            UUID transactionNum = UUID.randomUUID();
            FileWriter dataText = new FileWriter("src/files/sell_" +
                    transactionNum + ".txt");
            PrintWriter pw = new PrintWriter(dataText);
            pw.println("Transaction number  : " + transactionNum);
            pw.println("Cliente Id: " + cliente.getId() + "\n" +
                        "Cliente Name: " + cliente.getName() + "\n");
            pw.println("Item             |      Amount              |    Price  " );

            productArrayList.forEach(obj -> {
                pw.println(obj.getItemName() +  "                    " + obj.getCantidad() + "                    " + obj.getPrice());
            });
            if (dataText != null){
                dataText.close();
            }

        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void updateInventoryItems(HashMap<String, Product> newItems) {
        inventoryItems.putAll(newItems);
    }
<<<<<<< HEAD
    public void updateProviders(HashMap<String, Provider> newItems){
        providers.putAll(newItems);
=======
    public void updateProvidor(HashMap<String, Provider> newProvidor ){
        providers.putAll(newProvidor);
>>>>>>> 5cb1fbcfa51c364b7194f58812f7190e32c77d06
    }

}
