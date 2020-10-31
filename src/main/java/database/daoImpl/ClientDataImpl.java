package database.daoImpl;

import database.dao.ClientDataDao;
import database.entity.ClientData;
import database.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;

public class ClientDataImpl implements ClientDataDao {
    @Override
    public ClientData save(ClientData clientData) { // mamy przekazany obiekt Client a musimy do tabeli zapisac wiersz czyli obiekt ClientData

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(clientData);
        session.getTransaction().commit();
        session.close();

        return clientData;

    }

    @Override
    public ClientData findByPin(Integer pin) {
        ClientData clientData = null; // dlaczego tu jest wyszarzone?

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        clientData = session.createQuery("FROM ClientData WHERE pin =:pin", ClientData.class)
                .setParameter("pin", pin)
                .getSingleResult();

        session.getTransaction().commit();
        session.close();

        return clientData;
    }

    @Override
    public List<ClientData> findAll() { //zwracamy tylko wiersze, nie potrzebujemy tu obiektu typu Client

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<ClientData> allClientsFromDatabase = session.createQuery("from ClientData", ClientData.class).list();
        session.getTransaction().commit();
        session.close();

        return allClientsFromDatabase;
    }


    @Override
    public void updateClient() { // na podstawie klienta będzie musiala odszukac w bazie danych odpowiadajacy mu wiersz(np p//pin bo to jest cecha wspolna i zarazem unikalna dla Client i ClientData- wiersz), zaktualizowac sam wiersz, jak i klienta,  a//na koniec wrzucic zaktualizowany wiesz(ClientData) do bazy danych

        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj id klienta.");
        Integer id = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Podaj nowe nazwisko."); // zakladamy, ze imie i pesel sa nie do zmiany (w tabeli clients_data)
        String newSurname = scanner.nextLine();

        ClientData clientData;
        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        clientData = session.createQuery("from ClientData where id =:id", ClientData.class)
                .setParameter("id", id)
                .getSingleResult();

        clientData.setSurname(newSurname);
        session.saveOrUpdate(clientData);  // zmiana  w bazie danych
        session.getTransaction().commit();
        session.close();

    }


    @Override
    public void deleteById(Integer id) {

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.createQuery("DELETE ClientData WHERE id=:id")
                .setParameter("id", id)
                .executeUpdate();

        session.getTransaction().commit();
        session.close();

    }

    @Override // nie trzeba w try/catch jak czegos nie znajdzie po id ? nie bo zwroci null?
    public ClientData findById(Integer id) { // nie musimy w ogole odwolywac się do obiektu client, po prostu wyswietlamy wiersz

        ClientData clientData = null; // dlaczego tu jest wyszarzone?

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        clientData = session.createQuery("FROM ClientData WHERE id =:id", ClientData.class)
                .setParameter("id", id)
                .getSingleResult();

        session.getTransaction().commit();
        session.close();

        return clientData;
    }


}
