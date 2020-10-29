package database.daoImpl;

import application.Account;
import application.AccountType;
import application.Client;
import database.dao.ClientDataDao;
import database.entity.AccountData;
import database.entity.ClientData;
import database.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class ClientDataImpl implements ClientDataDao {
    @Override
    public void saveClient(Client client) { // mamy przekazany obiekt Client a musimy do tabeli zapisac wiersz czyli obiekt ClientData

        ClientData clientData = new ClientData();

        clientData.setName(client.getName());  // przerabiamy clienta na clientData
        clientData.setSurname(client.getSurname());
        clientData.setPesel(client.getPesel());
        clientData.setPin(client.getPin());

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(clientData);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public ClientData findById(Integer id) {
        return null;
    }

    @Override
    public List<ClientData> findAll() {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }
}
