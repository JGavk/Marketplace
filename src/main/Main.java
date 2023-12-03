package main;

import controller.WelcomeController;
import view.WelcomeView;

public class Main {
    public static void main(String[] args) {

        WelcomeView view= new WelcomeView();
        WelcomeController controller = new WelcomeController(view);
        view.setWelcomeController(controller);



    }
}