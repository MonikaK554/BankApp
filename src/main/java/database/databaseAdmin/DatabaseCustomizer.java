package database.databaseAdmin;

import database.utils.HibernateUtils;
import org.hibernate.Session;

public class DatabaseCustomizer {

    public static void checkConnection() {
        HibernateUtils.oneInstance().getSessionFactory().close();
    }

    public static void customizeDatabase() {

        Session session = HibernateUtils.oneInstance() // //Do zmodyfikowania, zcustomizowania tabeli na poczatku(przestawienie kolejnosci tabel itp)
                .getSessionFactory()
                .getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("ALTER TABLE accounts_data MODIFY client_id BIGINT NOT NULL AFTER account_Id").executeUpdate();
        session.createSQLQuery("ALTER TABLE bankdatabase.accounts_data MODIFY COLUMN account_number VARCHAR(255)").executeUpdate(); // WPISAC W WORKBENCH I PIORUN zmiana nazwy kolumny poleceniem CHANGE
        session.createSQLQuery("ALTER TABLE accounts_data MODIFY account_number decimal(19,2) AFTER balance").executeUpdate();
        session.createSQLQuery("ALTER TABLE clients_data MODIFY surname VARCHAR(255) AFTER name").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }


}
