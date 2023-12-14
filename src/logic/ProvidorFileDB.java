package logic;

import model.Product;
import model.Provider;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ProvidorFileDB implements Serializable {

        public static final long serialVersionUID = 1L;

        private static final String FILE_PATH = "src/files/provider_data.txt";

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
                HashMap<String, Provider> providerFromFile = parseProviderFromFile(fileContent.toString());

                // Actualizar el HashMap en la instancia de Structure.
                structure.updateProvidor(providerFromFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static HashMap<String, Provider> parseProviderFromFile(String fileContent) {
            HashMap<String, Provider> prov = new HashMap<>();

            // Eliminar caracteres innecesarios y dividir por líneas.
            String[] providerEntries = fileContent.replaceAll("[{}]", "").split("\n");

            String currentProvider = null;
            StringBuilder currentProviderInfo = new StringBuilder();

            for (String entry : providerEntries) {
                // Ignorar líneas vacías o que contengan solo espacios.
                if (entry.trim().isEmpty()) {
                    continue;
                }

                // Verificar si la línea contiene un nombre de producto.
                if (entry.contains("=")) {
                    // Si ya estamos procesando un producto, almacenar la información en el HashMap.
                    if (currentProvider != null) {
                        prov.put(currentProvider, createProviderFromInfo(currentProviderInfo.toString().trim()));
                    }

                    // Iniciar un nuevo producto.
                    String[] parts = entry.split("=");
                    currentProvider = parts[0].trim();
                    currentProviderInfo = new StringBuilder();
                }

                // Agregar la línea al StringBuilder del producto actual.
                currentProviderInfo.append(entry).append("\n");
            }

            // Asegurarse de agregar el último producto al HashMap.
            if (currentProvider != null) {
                prov.put(currentProvider, createProviderFromInfo(currentProviderInfo.toString().trim()));
            }

            return prov;
        }


        private static Provider createProviderFromInfo(String providerInfo) {
            String[] lines = providerInfo.split("\n");

            String providerName = null;
            double price = 0.0;
            String product = null;

            for (String line : lines) {
                String[] parts = line.split(": ");

                if (parts.length == 2) {
                    String attributeName = parts[0].trim();
                    String attributeProduct = parts[1].trim();
                    String attributePrice = parts[2].trim();
                    if (attributeName.contains("Name")){
                        attributeName = "Name";
                    }

                    if (attributeName.equals("Name")) {
                        providerName = attributeName;
                    } else if (attributeName.equals("Product")) {
                        product = attributeProduct;
                    } else if (attributeName.equals("Price")) {
                        price = Integer.parseInt(attributePrice);
                    }
                }
            }
            return null; //Esto debe cambiar a return new Provider (parameters);
        }


        public static void saveInventoryData(HashMap<String, Provider> providers) {
            System.out.println(providers);
        }

        public static void saveProductsToFile(HashMap<String, Provider> providers, String filePath) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
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
}


