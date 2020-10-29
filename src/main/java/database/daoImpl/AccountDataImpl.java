package database.daoImpl;

import application.Account;
import application.Client;
import database.dao.AccountDataDao;
import database.entity.AccountData;
import database.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class AccountDataImpl implements AccountDataDao {
    @Override
    public void saveClientAccount(Client client) {
        AccountData accountData = new AccountData(); //obiekt grający za wiersz (encja). Za wpisanie wiersza odpowiada IMPL

         // musimy ustawic teraz wszystkie parametry dla danego wiersza/rekodu w tabeli
        for (int i = 0; i < client.getListOfClientAccounts().size(); i++) {

            accountData.setAccountType(client.getListOfClientAccounts().get(i).getAccountType()); // ustawiamy parametry do account data(wiersza)
            accountData.setAccountNumber(client.getListOfClientAccounts().get(i).getAccountNumber());
            accountData.setBalance(client.getListOfClientAccounts().get(i).getBalance());

            Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.saveOrUpdate(accountData); // wpisujemy to wszystko gotowa metoda dla sesji saveOrUpdate
            session.getTransaction().commit();
            session.close();

        }
    }

    @Override
    public AccountData findById(Integer id) {
        return null;
    }

    @Override
    public List<AccountData> findAll() {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }
}
