package database.daoImpl;

import database.dao.AccountDataDao;
import database.entity.AccountData;
import database.utils.HibernateUtils;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.List;

public class AccountDataImpl implements AccountDataDao {
    @Override
    public void save(AccountData accountData) {

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(accountData);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public AccountData findByIdAndNo(Integer clientId, Long accountNo) {

        AccountData accountData = null; // na wypadek gdyby nic nie znalazlo otakim ID i jego numerze porzadkowym konta

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            accountData = session.createQuery("FROM AccountData WHERE clientData.id =:id AND number=:no", AccountData.class)
                    .setParameter("id", clientId)
                    .setParameter("no", accountNo)
                    .getSingleResult();

        } catch (NoResultException e) {
            System.out.println("Podano nieprawidłowe dane."); // numer porzadkowy
        }

        session.getTransaction().commit();
        session.close();
        return accountData;
    }


    @Override
    public List<AccountData> findAll() {
        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<AccountData> allAccountsFromDatabase = session.createQuery("from AccountData", AccountData.class).list();
        session.getTransaction().commit();
        session.close();

        return allAccountsFromDatabase;
    }

    @Override
    public void deleteByNo(Long number) { // usun po numerze porzadkowym

        Session session = HibernateUtils.oneInstance().getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.createQuery("DELETE AccountData WHERE number=:number")
                .setParameter("number", number)
                .executeUpdate();

        session.getTransaction().commit();
        session.close();


    }
}
