package logic;

import model.Product;

import java.io.*;
import java.util.HashMap;

public class InventoryFileDB implements Serializable {

    public static final long serialVersionUID = 1L;

    private static final String FILE_PATH = "src/files/inventory_data.txt";

    public static void loadInventoryData(Structure structure, String filePath) {
        FileReader file = null;
        try {
            file = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try (BufferedReader reader = new BufferedReader(file)) {
            String line;
            HashMap<String, Product> productsFromFile = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                // Dividir la línea por "=" para obtener el nombre y el resto de la información.
                String[] parts = line.split("=");

                if (parts.length == 2) {
                    String productName = parts[0].trim();
                    String productInfo = parts[1].trim();

                    // Crear un nuevo producto a partir de la información.
                    Product product = createProductFromInfo(productInfo);

                    // Agregar el producto al HashMap.
                    productsFromFile.put(productName, product);
                }
            }

            // Actualizar el HashMap en la instancia de Structure.
            structure.updateInventoryItems(productsFromFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Product createProductFromInfo(String productInfo) {
        // Dividir la información del producto por las líneas y procesar cada línea.
        String[] lines = productInfo.split(", ");

        String itemName = null;
        double price = 0.0;

        for (String line : lines) {
            // Dividir cada línea por ": " para obtener el nombre del atributo y su valor.
            String[] attribute = line.split(": ");
            if (attribute.length == 2) {
                String attributeName = attribute[0].trim();
                String attributeValue = attribute[1].trim();

                if (attributeName.equals("Item name")) {
                    itemName = attributeValue;
                } else if (attributeName.equals("Price")) {
                    price = Double.parseDouble(attributeValue);
                }
            }
        }

        // Crear y devolver un nuevo producto.
        return new Product(itemName, 0, price);
    }

    public static void saveInventoryData(HashMap<String, Product> inventoryItems) {
        System.out.println(inventoryItems);
    }
}
