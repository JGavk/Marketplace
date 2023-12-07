package logic;

import model.Product;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

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

        // Eliminar caracteres innecesarios y dividir por líneas.
        String[] productEntries = fileContent.replaceAll("[{}]", "").split("\n");

        String currentProduct = null;
        StringBuilder currentProductInfo = new StringBuilder();

        for (String entry : productEntries) {
            // Ignorar líneas vacías o que contengan solo espacios.
            if (entry.trim().isEmpty()) {
                continue;
            }

            // Verificar si la línea contiene un nombre de producto.
            if (entry.contains("=")) {
                // Si ya estamos procesando un producto, almacenar la información en el HashMap.
                if (currentProduct != null) {
                    products.put(currentProduct, createProductFromInfo(currentProductInfo.toString().trim()));
                }

                // Iniciar un nuevo producto.
                String[] parts = entry.split("=");
                currentProduct = parts[0].trim();
                currentProductInfo = new StringBuilder();
            }

            // Agregar la línea al StringBuilder del producto actual.
            currentProductInfo.append(entry).append("\n");
        }

        // Asegurarse de agregar el último producto al HashMap.
        if (currentProduct != null) {
            products.put(currentProduct, createProductFromInfo(currentProductInfo.toString().trim()));
        }

        return products;
    }


    private static Product createProductFromInfo(String productInfo) {
        String[] lines = productInfo.split("\n");

        String itemName = null;
        double price = 0.0;
        int quantity = 0; // Agregamos la cantidad como propiedad del producto.

        for (String line : lines) {
            String[] parts = line.split(": ");

            if (parts.length == 2) {
                String attributeName = parts[0].trim();
                String attributeValue = parts[1].trim();

                if (attributeName.contains("Item name")){
                    attributeName = "Item name";
                }

                if (attributeName.equals("Item name")) {
                    itemName = attributeValue;
                } else if (attributeName.equals("Price")) {
                    price = Double.parseDouble(attributeValue);
                } else if (attributeName.equals("Quantity")) {
                    quantity = Integer.parseInt(attributeValue);
                }
            }
        }

        return new Product(itemName, quantity, price);
    }


    public static void saveInventoryData(HashMap<String, Product> inventoryItems) {
        System.out.println(inventoryItems);
    }

    public static void saveProductsToFile(HashMap<String, Product> products, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("{\n");

            for (Map.Entry<String, Product> entry : products.entrySet()) {
                String productName = entry.getKey();
                Product product = entry.getValue();

                // Escribir cada entrada en el formato esperado.
                writer.write("  " + productName + "=" + product + "\n");
            }

            writer.write("}\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
