package logic;
import model.*;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Structure implements Serializable {
    public static final long serialVersionUID = 1L;
    private ArrayList<Product> productArrayList;


    public Structure(){
        productArrayList = new ArrayList<>();
    }

    //Compra del producto y añadir al arreglo de productos
    public void addBought(Product product) {
        productArrayList.add(product);
        System.out.println(productArrayList); //Probar si el arreglo sirve
        //printBought(product);
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
