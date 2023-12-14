package logic;

import model.Product;
import model.Provider;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ProviderFileDB implements Serializable {

    public static final long serialVersionUID = 1L;

    private static final String FILE_PATH = "src/files/Provider_data.txt";

    //---------------------- PRUEBAS PARA LEER INFO DE ARCHIVO Y PASARLO AL HASHMAP ---------------------------------

    // Esta función toma lo que hay en el hashmap de proveedores y lo guarda al txt(provider_data.txt)
    public static void saveProviderToFile(HashMap<String, Provider> providers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("{\n");

            for (Map.Entry<String, Provider> entry : providers.entrySet()) {
                String providerName = entry.getKey();
                Provider provider = entry.getValue();

                // Escribir cada entrada en el formato esperado.
                writer.write("  " + providerName + "=" + provider + "\n");
            }

            writer.write("}\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Esta funcion ya guarda lo que hay en el txt(provider_data.txt) segun la estructura que tiene
    public static HashMap<String, Provider> procesarArchivo() throws IOException {
        HashMap<String, Provider> proveedores = new HashMap<>();
        // Abre el archivo de entrada
        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));

        String line;

        String currentFarm = null;
        String currentProductName = null;
        double currentProductPrice = 0.0;

        // Lee el archivo de entrada línea por línea
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }

            if (line.contains("name:") && line.contains("=")) {
                // Busca la línea que comienza con el nombre de la granja
                currentFarm = line.trim().split("=")[0].trim();
                System.out.println("Current Farm: " + currentFarm);
            } else if (line.startsWith("Item name:")) {
                currentProductName = line.trim().split(":")[1].trim();
            } else if (line.startsWith("Price:")) {
                currentProductPrice = Double.parseDouble(line.trim().split(":")[1].trim());

                // Crea el producto y el proveedor y agrégalo al HashMap
                Product producto = new Product(currentProductName, 0, currentProductPrice);
                Provider proveedor = new Provider(currentFarm, producto);
                proveedores.put(currentFarm, proveedor);
            }
        }

        // Cierra los recursos
        br.close();
        return proveedores;
    }

    /* CEMENTERIO FUNCIONES ANTIGUAS ---------------------------------------------------------------------------------
    public static void loadProviderData(Structure structure) {
        FileReader file = null;
        try {
            file = new FileReader(FILE_PATH);
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
            HashMap<String, Provider> providersFromFile = parseProvidersFromFile(fileContent.toString());
            // Actualizar el HashMap en la instancia de Structure.
            structure.updateProviders(providersFromFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static HashMap<String, Provider> parseProvidersFromFile(String fileContent) {
        HashMap<String, Provider> providers = new HashMap<>();
        // Eliminar caracteres innecesarios y dividir por líneas.
        String[] providerEntries = fileContent.split("(?<=\\})\\s*(?=[A-Za-z])");
        for (String entry : providerEntries) {
            // Ignorar líneas vacías o que contengan solo espacios.
            if (entry.trim().isEmpty()) {
                continue;
            }
            // Parsear cada entrada de proveedor.
            Provider provider = parseProviderEntry(entry);
            if (provider != null) {
                providers.put(provider.getName(), provider);
            }
        }
        return providers;
    }
    private static Provider parseProviderEntry(String entry) {
        String[] lines = entry.split("\n");
        String providerName = null;
        String productName = null;
        double productPrice = 0.0;
        for (String line : lines) {
            // Ignorar líneas vacías o que contengan solo espacios.
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] parts = line.split(": ");
            if (parts.length == 3) {
                String attributeName = parts[0].trim();
                String attributeValue = parts[1].trim();
                String attri = parts[2].trim();
                if (attributeName.equals("name")) {
                    providerName = attributeValue;
                } else if (attributeName.equals("product")) {
                    // Extraer información adicional del producto.
                    String[] productInfoParts = attributeValue.split("\n");
                    for (String productInfo : productInfoParts) {
                        String[] productAttributeParts = productInfo.split(": ");
                        if (productAttributeParts.length == 2) {
                            String productAttribute = productAttributeParts[0].trim();
                            String productAttributeValue = productAttributeParts[1].trim();
                            if (productAttribute.equals("Item name")) {
                                productName = productAttributeValue;
                            } else if (productAttribute.equals("Price")) {
                                productPrice = Double.parseDouble(productAttributeValue);
                            }
                        }
                    }
                }
            }
        }
        // Crear el objeto Provider con la información extraída.
        if (providerName != null && productName != null) {
            Product product = new Product(productName, 0, productPrice);
            return new Provider(providerName, product);
        }
        return null;
    }
    private static Provider createProviderFromInfo(String providerInfo) {
        String[] lines = providerInfo.split("\n");
        String providerName = null;
        String productName = null;
        double productPrice = 0.0;
        for (String line : lines) {
            String[] parts = line.split(": ");
            if (parts.length == 2) {
                String attributeName = parts[0].trim();
                String attributeValue = parts[1].trim();
                if (attributeName.contains("Name")) {
                    attributeName = "Name";
                }
                if (attributeName.equals("Name")) {
                    providerName = attributeValue;
                } else if (attributeName.equals("Product")) {
                    // Extraer información adicional del producto.
                    String[] productInfoParts = attributeValue.split(": ");
                    for (int i = 0; i < productInfoParts.length; i += 2) {
                        String productAttribute = productInfoParts[i].trim();
                        String productAttributeValue = productInfoParts[i + 1].trim();
                        if (productAttribute.equals("Item name")) {
                            productName = productAttributeValue;
                        } else if (productAttribute.equals("Price")) {
                            productPrice = Double.parseDouble(productAttributeValue);
                        }
                    }
                }
            }
        }
        // Crear el objeto Provider con la información extraída.
        Product product = new Product(productName, 0, productPrice);
        return new Provider(providerName, product);
    }
    public static void printProviders(HashMap<String, Provider> providers) {
        System.out.println(providers);
    }
     */
}