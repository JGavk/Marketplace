/*
Juan Pablo Puerta Gavira 2240033
Yeimer Armando Mendez Sanchez 2243583
Miguel Angel Soto
* */
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
}