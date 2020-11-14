package application;

import application.mvc.controller.Controller;
import application.mvc.model.databaseAdmin.DatabaseCustomizer;

public class Test {
    public static void main(String[] args) throws InterruptedException {

        //DatabaseCustomizer uruchamiany przy pierwszym uruchomieniu programu tylko
        DatabaseCustomizer.checkConnection();
        DatabaseCustomizer.customizeDatabase();

        Controller controller = new Controller();
        controller.start();


    }
}