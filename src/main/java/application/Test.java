package application;

import database.databaseAdmin.DatabaseCustomizer;
import database.utils.HibernateUtils;
import org.hibernate.Session;

public class Test {
    public static void main(String[] args) throws InterruptedException {

       // DatabaseCustomizer.checkConnection();
         DatabaseCustomizer.customizeDatabase();
       // application.MainMethod.mainMethod();


    }
}