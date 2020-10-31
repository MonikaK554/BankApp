package database.daoImpl;

import application.Bank;
import application.Client;
import database.dao.ClientDataDao;
import database.entity.ClientData;
import database.utils.HibernateUtils;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientDataImplTest {

    private ClientDataDao clientDataDao = new ClientDataImpl();

    @BeforeEach
    void clearTable() {
        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();

        session.beginTransaction();
        session.createSQLQuery("DELETE FROM clients_data").executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    @Test
    void save() {
        ClientData clientData = new ClientData();
        clientData.setName("Anna");
        clientData.setSurname("Nowak");
        clientData.setPesel(90011250017L);
        clientData.setPin(Bank.createPin());

        clientDataDao.save(clientData);
        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        ClientData saved = session.createQuery("FROM ClientData WHERE id=:id AND name=:name AND surname=:surname AND pesel=:pesel AND pin=:pin", ClientData.class)
                .setParameter("id", clientData.getId())
                .setParameter("name", clientData.getName())
                .setParameter("surname", clientData.getSurname())
                .setParameter("pesel", clientData.getPesel())
                .setParameter("pin", clientData.getPin())
                .getSingleResult();

        session.getTransaction().commit();
        session.close();

        assertNotNull(saved);
        assertEquals(clientData.getId(), saved.getId());
        assertEquals(clientData.getName(), saved.getName());
        assertEquals(clientData.getSurname(), saved.getSurname());
        assertEquals(clientData.getPesel(), saved.getPesel());

        assertEquals(clientData.getPin(), saved.getPin());
    }

    @Test
    void findByPin() {

        ClientData clientData = new ClientData();
        clientData.setName("Anna");
        clientData.setSurname("Nowak");
        clientData.setPesel(90011250017L);
        clientData.setPin(Bank.createPin());

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.saveOrUpdate(clientData);

        session.getTransaction().commit(); // korzystamy z gotowej metody a nie ze stworzonej wlasnej save() aby test pozostal jednostkowy
        session.close();

        ClientData foundByPin = clientDataDao.findByPin(clientData.getPin());

        assertNotNull(foundByPin);
        assertEquals(clientData.getId(), foundByPin.getId());
        assertEquals(clientData.getName(), foundByPin.getName());
        assertEquals(clientData.getSurname(), foundByPin.getSurname());
        assertEquals(clientData.getPesel(), foundByPin.getPesel());
        assertEquals(clientData.getPin(), foundByPin.getPin());

    }

    @Test
    void findAll() {

        ClientData clientData = new ClientData();
        clientData.setName("Anna");
        clientData.setSurname("Nowak");
        clientData.setPesel(90011250017L);
        clientData.setPin(Bank.createPin());

        ClientData clientData1 = new ClientData();
        clientData1.setName("Macin");
        clientData1.setSurname("Kowalski");
        clientData1.setPesel(90041840217L);
        clientData1.setPin(Bank.createPin());

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.saveOrUpdate(clientData);
        session.saveOrUpdate(clientData1);

        session.getTransaction().commit();
        session.close();

        List<ClientData> listOfAllClients = clientDataDao.findAll();

        assertNotNull(listOfAllClients);
        assertEquals(2, listOfAllClients.size());

        ClientData loaded = null;
        if (listOfAllClients.get(0).getId() == clientData.getId()) {
            loaded = listOfAllClients.get(0);

            assertNotNull(loaded);
            assertEquals(clientData.getId(), loaded.getId());
            assertEquals(clientData.getName(), loaded.getName());
            assertEquals(clientData.getSurname(), loaded.getSurname());
            assertEquals(clientData.getPesel(), loaded.getPesel());
            assertEquals(clientData.getPin(), loaded.getPin());

        } else {
            loaded = listOfAllClients.get(1);

            assertNotNull(loaded);
            assertEquals(clientData1.getId(), loaded.getId());
            assertEquals(clientData1.getName(), loaded.getName());
            assertEquals(clientData1.getSurname(), loaded.getSurname());
            assertEquals(clientData1.getPesel(), loaded.getPesel());
            assertEquals(clientData1.getPin(), loaded.getPin());
        }
    }

    @Test
    void updateClient() {

        ClientData clientData = new ClientData();
        clientData.setName("Anna");
        clientData.setSurname("Nowak");
        clientData.setPesel(90011250017L);
        clientData.setPin(Bank.createPin());

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.saveOrUpdate(clientData);
        clientData.setSurname("Kowalska");
        session.saveOrUpdate(clientData);

        session.getTransaction().commit();
        session.close();

        ClientData updated = clientDataDao.findById(clientData.getId());

        assertNotNull(updated);

        assertEquals(clientData.getId(), updated.getId());
        assertEquals(clientData.getName(), updated.getName());
        assertEquals(clientData.getSurname(), updated.getSurname());
        assertEquals(clientData.getPesel(), updated.getPesel());
        assertEquals(clientData.getPin(), updated.getPin());

    }

    @Test
    void deleteById() {

        ClientData clientData = new ClientData();
        clientData.setName("Anna");
        clientData.setSurname("Nowak");
        clientData.setPesel(90011250017L);
        clientData.setPin(Bank.createPin());

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.saveOrUpdate(clientData);

        session.getTransaction().commit();
        session.close();

        clientDataDao.deleteById(clientData.getId());
        ClientData deleted = clientDataDao.findById(clientData.getId());

        assertNull(deleted);

    }

    @Test
    void findById() {

        ClientData clientData = new ClientData();
        clientData.setName("Anna");
        clientData.setSurname("Nowak");
        clientData.setPesel(90011250017L);
        clientData.setPin(Bank.createPin());

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.saveOrUpdate(clientData);

        session.getTransaction().commit();
        session.close();

        ClientData foundById = clientDataDao.findById(clientData.getId());

        assertNotNull(foundById);
        assertEquals(clientData.getId(), foundById.getId());
        assertEquals(clientData.getName(), foundById.getName());
        assertEquals(clientData.getSurname(), foundById.getSurname());
        assertEquals(clientData.getPesel(), foundById.getPesel());
        assertEquals(clientData.getPin(), foundById.getPin());
    }
}