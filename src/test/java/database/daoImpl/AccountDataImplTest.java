package database.daoImpl;

import application.Account;
import application.AccountType;
import application.Bank;
import database.dao.AccountDataDao;
import database.dao.ClientDataDao;
import database.entity.AccountData;
import database.entity.ClientData;
import database.utils.HibernateUtils;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountDataImplTest {

    AccountDataDao accountDataDao = new AccountDataImpl();
    ClientDataDao clientDataDao = new ClientDataImpl();

    @BeforeEach
    void clearTable() {
        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();

        session.beginTransaction();
        session.createSQLQuery("DELETE FROM accounts_data").executeUpdate();
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

        AccountData accountData = new AccountData();
        accountData.setClientData(clientData);
        accountData.setAccountType(AccountType.PRO);
        accountData.setBalance(new BigDecimal(1000));
        accountData.setAccountNumber(Account.createUniqueAccountNumber());

        accountDataDao.save(accountData);
        AccountData saved = accountDataDao.findByClientIdAnAccountId(clientData.getId(), accountData.getAccountId());

        assertNotNull(saved);
        assertEquals(accountData.getAccountId(), saved.getAccountId());
        assertEquals(accountData.getClientData(), saved.getClientData());
        assertEquals(accountData.getAccountType(), saved.getAccountType());
        assertEquals(accountData.getBalance(), saved.getBalance());
        assertEquals(accountData.getAccountNumber(), saved.getAccountNumber());
    }

    @Test
    void findByClientIdAnAccountId() {

        ClientData clientData = new ClientData();
        clientData.setName("Anna");
        clientData.setSurname("Nowak");
        clientData.setPesel(90011250017L);
        clientData.setPin(Bank.createPin());

        clientDataDao.save(clientData);

        AccountData accountData = new AccountData();
        accountData.setClientData(clientData);
        accountData.setAccountType(AccountType.PRO);
        accountData.setBalance(new BigDecimal(1000));
        accountData.setAccountNumber(Account.createUniqueAccountNumber());

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.saveOrUpdate(accountData);

        session.getTransaction().commit();
        session.close();

        AccountData found = accountDataDao.findByClientIdAnAccountId(clientData.getId(), accountData.getAccountId());

        assertNotNull(found);

        assertEquals(accountData.getAccountId(), found.getAccountId());
        assertEquals(accountData.getClientData(), found.getClientData());
        assertEquals(accountData.getAccountType(), found.getAccountType());
        assertEquals(accountData.getBalance(), found.getBalance());
        assertEquals(accountData.getAccountNumber(), found.getAccountNumber());

    }

    @Test
    void findAll() {

        ClientData clientData = new ClientData();
        clientData.setName("Anna");
        clientData.setSurname("Nowak");
        clientData.setPesel(90011250017L);
        clientData.setPin(Bank.createPin());

        clientDataDao.save(clientData);

        AccountData accountData = new AccountData();
        accountData.setClientData(clientData);
        accountData.setAccountType(AccountType.PRO);
        accountData.setBalance(new BigDecimal(1000));
        accountData.setAccountNumber(Account.createUniqueAccountNumber());

        AccountData accountData1 = new AccountData();
        accountData1.setClientData(clientData);
        accountData1.setAccountType(AccountType.STUDENT);
        accountData1.setBalance(new BigDecimal(500));
        accountData1.setAccountNumber(Account.createUniqueAccountNumber());

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.saveOrUpdate(accountData);
        session.saveOrUpdate(accountData1);

        session.getTransaction().commit();
        session.close();

        List<AccountData> listOfAllAccounts = accountDataDao.findAll();

        assertNotNull(listOfAllAccounts);
        assertEquals(2, listOfAllAccounts.size());


        AccountData loaded = null;
        if (listOfAllAccounts.get(0).getAccountId().equals(accountData.getAccountId())) {
            loaded = listOfAllAccounts.get(0);

            assertNotNull(loaded);
            assertEquals(accountData.getAccountId(), loaded.getAccountId());
            assertEquals(accountData.getClientData(), loaded.getClientData());
            assertEquals(accountData.getAccountType(), loaded.getAccountType());
            assertEquals(accountData.getBalance(), loaded.getBalance());
            assertEquals(accountData.getAccountNumber(), loaded.getAccountNumber());

        } else {
            loaded = listOfAllAccounts.get(1);

            assertNotNull(loaded);
            assertEquals(accountData1.getAccountId(), loaded.getAccountId());
            assertEquals(accountData1.getClientData(), loaded.getClientData());
            assertEquals(accountData1.getAccountType(), loaded.getAccountType());
            assertEquals(accountData1.getBalance(), loaded.getBalance());
            assertEquals(accountData1.getAccountNumber(), loaded.getAccountNumber());
        }

    }

    @Test
    void deleteByAccountId() { // twporzym wiersz AccountData, ale zeby go stworzyc to trzeba najpierw swtorzyc klienta

        ClientData clientData = new ClientData();
        clientData.setName("Anna");
        clientData.setSurname("Nowak");
        clientData.setPesel(90011250017L);
        clientData.setPin(Bank.createPin());

        clientDataDao.save(clientData);

        AccountData accountData = new AccountData();
        accountData.setClientData(clientData);
        accountData.setAccountType(AccountType.PRO);
        accountData.setBalance(new BigDecimal(1000));
        accountData.setAccountNumber(Account.createUniqueAccountNumber());

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(accountData);
        session.getTransaction().commit();
        session.close();

        accountDataDao.deleteByAccountId(accountData.getAccountId());
        AccountData deleted = accountDataDao.findByClientIdAnAccountId(clientData.getId(), accountData.getAccountId());

        assertNull(deleted);
    }

    @Test
    void deleteAllAccountsWhileDeletingClient() {
        ClientData clientData = new ClientData();
        clientData.setName("Anna");
        clientData.setSurname("Nowak");
        clientData.setPesel(90011250017L);
        clientData.setPin(Bank.createPin());

        clientDataDao.save(clientData);

        AccountData accountData = new AccountData();
        accountData.setClientData(clientData);
        accountData.setAccountType(AccountType.PRO);
        accountData.setBalance(new BigDecimal(1000));
        accountData.setAccountNumber(Account.createUniqueAccountNumber());

        AccountData accountData1 = new AccountData();
        accountData1.setClientData(clientData);
        accountData1.setAccountType(AccountType.STUDENT);
        accountData1.setBalance(new BigDecimal(500));
        accountData1.setAccountNumber(Account.createUniqueAccountNumber());

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(accountData);
        session.saveOrUpdate(accountData1);
        session.getTransaction().commit();
        session.close();

       accountDataDao.deleteAllAccountsWhileDeletingClient(clientData.getId());

       assertNull(clientData.getAccountList());
    }
}