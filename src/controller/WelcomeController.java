package controller;

import view.StructureView;
import view.WelcomeView;



public class WelcomeController {
    public StructureView sView;

    private WelcomeView wView;

    public WelcomeController(){

    }
    //Apertura de la ventana de structura y agregarle el segundo controlador
    public void StructureOpen (String input) {
        sView = new StructureView();
        StructureController controller = new StructureController();
        sView.setStructureController(controller);
        sView.nameLabel.setText(input);
    }
}
