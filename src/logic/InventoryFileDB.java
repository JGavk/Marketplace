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
            StringBuilder fileContent = new StringBuilder();
            String line;

            // Leer el contenido completo del archivo en un StringBuilder.
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }

            // Parsear el contenido del archivo.
            HashMap<String, Product> productsFromFile = parseProductsFromFile(fileContent.toString());

            // Actualizar el HashMap en la instancia de Structure.
            structure.updateInventoryItems(productsFromFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HashMap<String, Product> parseProductsFromFile(String fileContent) {
        HashMap<String, Product> products = new HashMap<>();

        // Eliminar caracteres innecesarios y dividir por comas.
        String[] productEntries = fileContent.replaceAll("[{}]", "").split(",");

        for (String entry : productEntries) {
            // Dividir por "=" para obtener el nombre y la información del producto.
            String[] parts = entry.split("=");

            if (parts.length == 2) {
                String productName = parts[0].trim();
                String productInfo = parts[1].trim();

                // Crear un nuevo producto a partir de la información.
                Product product = createProductFromInfo(productInfo);

                // Agregar el producto al HashMap.
                products.put(productName, product);
            }
        }

        return products;
    }

    private static Product createProductFromInfo(String productInfo) {
        String[] lines = productInfo.split("\n");

        String itemName = null;
        double price = 0.0;

        for (String line : lines) {
            String[] parts = line.split(": ");
            if (parts.length == 2) {
                String attributeName = parts[0].trim();
                String attributeValue = parts[1].trim();

                if (attributeName.equals("Item name")) {
                    itemName = attributeValue;
                } else if (attributeName.equals("Price")) {
                    price = Double.parseDouble(attributeValue);
                }
            }
        }

        return new Product(itemName, 0, price);
    }

    public static void saveInventoryData(HashMap<String, Product> inventoryItems) {
        System.out.println(inventoryItems);
    }
}
