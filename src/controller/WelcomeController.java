/*
Juan Pablo Puerta Gavira 2240033
Yeimer Armando Mendez Sanchez 2243583
Miguel Angel Soto
* */
package controller;

import view.StructureView;
import view.WelcomeView;



public class WelcomeController {
    public StructureView sView;

    private WelcomeView view;

    public WelcomeController(WelcomeView view){
        this.view = view;
    }
    //Apertura de la ventana de structura y agregarle el segundo controlador
    public void StructureOpen (String input) {
        sView = new StructureView();
        StructureController controller = new StructureController(sView);
        sView.setStructureController(controller);
        sView.nameLabel.setText(input);
    }
}
