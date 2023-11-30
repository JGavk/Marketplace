package controller;

import view.StructureView;
import view.WelcomeView;



public class WelcomeController {
    public StructureView sView;

    private WelcomeView wView;

    public WelcomeController(){
        this.wView=wView;
        this.sView=sView;
    }

    public void StructureOpen (String input) {
        sView = new StructureView();
        sView.nameLabel.setText(input);
    }
}
