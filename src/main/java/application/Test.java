package application;


import database.utils.HibernateUtils;
import org.hibernate.Session;

public class Test {
    public static void main(String[] args) throws InterruptedException {

        application.MainMethod.mainMethod();
        // HibernateUtils.oneInstance().getSessionFactory().close();

     //   Session session = HibernateUtils.oneInstance() // ustawienie kolejnosci kolumn dla tabeli clients_data
//                .getSessionFactory()
//                .getCurrentSession();
//        session.beginTransaction();
//        session.createSQLQuery("ALTER TABLE accounts_data MODIFY client_id BIGINT NOT NULL AFTER number").executeUpdate();
////        session.createSQLQuery("ALTER TABLE accounts_data CHANGE Account_number account_number VARCHAR(255)").executeUpdate(); // zmiana nazwy kolumny poleceniem CHANGE
//        session.createSQLQuery("ALTER TABLE accounts_data MODIFY account_number decimal(19,2) AFTER balance").executeUpdate();
//        session.createSQLQuery("ALTER TABLE clients_data MODIFY surname VARCHAR(255) AFTER name").executeUpdate();
//        session.getTransaction().commit();
//        session.close();
    }
}