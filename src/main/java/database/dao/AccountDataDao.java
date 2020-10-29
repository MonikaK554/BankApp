package database.dao;

import application.Client;
import database.entity.AccountData;

import java.util.List;

public interface AccountDataDao {

    void saveClientAccount(Client client);
    AccountData findById (Integer id);
    List<AccountData> findAll();
    void deleteById (Integer id);
}
