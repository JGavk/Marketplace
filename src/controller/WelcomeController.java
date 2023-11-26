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

    public void StructureOpen () {
        sView = new StructureView();
        wView.dispose();
    }
}
