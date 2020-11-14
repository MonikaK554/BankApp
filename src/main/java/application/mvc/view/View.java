package application.mvc.view;

import application.mvc.model.entity.AccountData;
import application.mvc.model.entity.ClientData;

import java.math.BigDecimal;

public interface View {

    int showMenuAndGetSelectedOption();
    ClientData askForClientPersonalDataAndCreateClientData();
    void showClientInformation(ClientData clientData);
    int askForClientPin();
    AccountData askForAccountDataAndCreateAccount(ClientData clientData) throws InterruptedException; // przekazujemy jako parametr klienta aby napewno temu przypisalo konto
    int askForAccountIdWhenCashAdd();
    int askForAccountIdWhenCashWithdrawal();
    BigDecimal askForDeposit();
    BigDecimal askForWithDrawal();
    void showInfoAboutIncorrectPinNumber() throws InterruptedException;
    void showInfoAboutIncorrectAccountId() throws InterruptedException;
    void showInfoAboutIncorrectClientId() throws InterruptedException;
    void showAccountBalance(AccountData accountData);
    void showNotEnoughMoneyToWithDrawal();
    int askForEmpleyeesCodeToGetAccess();
    boolean verifyAccessCode (int code) throws InterruptedException;
    int showEmployeesMenuAndGetSelectedOption();
    int askForClientId();
    void showInfoAboutDeletingClientWithAllAccounts();
    int askForAccountIdToDelete ();
    void showInfoAboutDeletingSingleAccount();
    String askForNewSurname();




}
