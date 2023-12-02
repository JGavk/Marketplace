package logic;
import model.*;
import view.StructureView;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

public class Structure implements Serializable {
    public static final long serialVersionUID = 1L;
    private ArrayList<Product> productArrayList;

    public Structure(){
        productArrayList = new ArrayList<>();
    }

    //Compra del producto y añadir al arreglo de productos
    public void addBought(Product product) throws IOException{
        productArrayList.add(product);
        printBought(product);
    }
    //Creador de txt para archivo de compra de uno o más objetos
    private void printBought(Product product) throws IOException {
        FileWriter dataText = new FileWriter("src/files/sell_" +
                product.getNumberSell() + ".txt");
        PrintWriter pw = new PrintWriter(dataText);
        pw.println("Transaction number  : " + product.getNumberSell());
        pw.println("Item Name           : " + product.getItemName());
        pw.println("Quantity            : " + product.getQuantity());
        pw.println("Price               : " + product.getPrice());
        ArrayList<TxtData> dataArrayList = product.getDataArchiveList();
        try {
            if (dataText != null)
                dataText.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
