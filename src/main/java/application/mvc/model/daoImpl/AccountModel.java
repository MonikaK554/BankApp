package application.mvc.model.daoImpl;

import application.mvc.model.dao.AccountDataDao;
import application.mvc.model.entity.AccountData;
import application.mvc.model.utils.HibernateUtils;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.List;

public class AccountModel implements AccountDataDao {

    public void save(AccountData accountData) {

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(accountData);
        session.getTransaction().commit();
        session.close();
    }

    public AccountData findByClientIdAnAccountId(Integer clientId, Integer accountId) {

        AccountData accountData = null; // na wypadek gdyby nic nie znalazlo otakim ID i jego numerze porzadkowym konta

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            accountData = session.createQuery("FROM AccountData WHERE clientData.id =:id AND accountId=:accountId", AccountData.class)
                    .setParameter("id", clientId)
                    .setParameter("accountId", accountId)
                    .getSingleResult();

        } catch (NoResultException e) {

        }

        session.getTransaction().commit();
        session.close();
        return accountData;
    }

    public void deleteAllAccountsWhileDeletingClient(Integer clientId) {

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.createQuery("DELETE AccountData WHERE clientData.id =:id")
                .setParameter("id", clientId)
                .executeUpdate();

        session.getTransaction().commit();
        session.close();

    }


    public void deleteByAccountId(Integer accountId) { // usun po numerze porzadkowym wybrane konto klienta

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.createQuery("DELETE AccountData WHERE accountId=:accountId")
                .setParameter("accountId", accountId)
                .executeUpdate();

        session.getTransaction().commit();
        session.close();

    }

    public List<AccountData> findAll() {
        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<AccountData> allAccountsFromDatabase = session.createQuery("from AccountData", AccountData.class).list();
        session.getTransaction().commit();
        session.close();

        return allAccountsFromDatabase;
    }

}
