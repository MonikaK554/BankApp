package application.mvc.model.daoImpl;

import application.mvc.model.dao.ClientDataDao;
import application.mvc.model.entity.AccountData;
import application.mvc.model.entity.ClientData;
import application.mvc.model.utils.HibernateUtils;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.List;

public class ClientModel implements ClientDataDao { // to samo co clientDataImpl (dane z bazy o clientdata)

    public ClientData save(ClientData clientData) { // to samo co clientDataImpl.save() tylko przerabiamy na mvc

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(clientData);
        session.getTransaction().commit();
        session.close();

        return clientData;

    }


    public ClientData findByPin(Integer pin) {
        ClientData clientData = null; // jak nic nie znajdzie o tym pinie(bo klient podal bledny pin) to zwroci null

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {

            clientData = session.createQuery("FROM ClientData WHERE pin =:pin", ClientData.class)
                    .setParameter("pin", pin)
                    .getSingleResult();

            session.getTransaction().commit();
            session.close();
        } catch (NoResultException e) {

        }

        return clientData;
    }


    public ClientData findById(Integer id) { // nie musimy w ogole odwolywac się do obiektu client, po prostu wyswietlamy wiersz

        ClientData clientData = null; // dlaczego tu jest wyszarzone?

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            clientData = session.createQuery("FROM ClientData WHERE id =:id", ClientData.class)
                    .setParameter("id", id)
                    .getSingleResult();

        } catch (NoResultException e) {

        }

        session.getTransaction().commit();
        session.close();

        return clientData;
    }


    public void getListOfClientAccounts (ClientData clientData) {
        List<AccountData> listOfAllAccounts = clientData.getAccountList();
        listOfAllAccounts.forEach(accountData -> System.out.println("Id konta " + accountData.getAccountId() + " " + accountData.getAccountType() + " " + accountData.getBalance()));
    }


    public void deleteById(Integer id) {

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.createQuery("DELETE ClientData WHERE id=:id")
                .setParameter("id", id)
                .executeUpdate();

        session.getTransaction().commit();
        session.close();

    }

    public void updateClient(ClientData clientData, String newSurname) { // na podstawie klienta będzie musiala odszukac w bazie danych odpowiadajacy mu wiersz(np p//pin bo to jest cecha wspolna i zarazem unikalna dla Client i ClientData- wiersz), zaktualizowac sam wiersz, jak i klienta,  a//na koniec wrzucic zaktualizowany wiesz(ClientData) do bazy danych

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        clientData = session.createQuery("from ClientData where id =:id", ClientData.class)
                .setParameter("id", clientData.getId())
                .getSingleResult();

        clientData.setSurname(newSurname);
        session.saveOrUpdate(clientData);  // zmiana  w bazie danych
        session.getTransaction().commit();
        session.close();

    }

    public List<ClientData> findAll() { //zwracamy tylko wiersze, nie potrzebujemy tu obiektu typu Client

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<ClientData> allClientsFromDatabase = session.createQuery("from ClientData", ClientData.class).list();
        session.getTransaction().commit();
        session.close();

        return allClientsFromDatabase;
    }
}