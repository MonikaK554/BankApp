package application.mvc.model.dao;

import application.mvc.model.entity.AccountData;
import java.util.List;

public interface AccountDataDao {

    void save(AccountData accountData); //przy dodawaniu nowego konta dla istniejacego klienta
    AccountData findByClientIdAnAccountId (Integer clientId, Integer accountId); // szukanie po id klienta i numerze porzadkowym konta potem
    List<AccountData> findAll();
    void deleteByAccountId (Integer accountId);
    void deleteAllAccountsWhileDeletingClient (Integer clientId);
}
