package application.mvc.controller;

import application.mvc.model.daoImpl.AccountModel;
import application.mvc.model.daoImpl.ClientModel;
import application.mvc.view.View;
import application.mvc.view.ViewPL;
import application.mvc.model.entity.AccountData;
import application.mvc.model.entity.ClientData;

import java.math.BigDecimal;

public class Controller {

    private View view;
    private ClientModel clientModel;
    private AccountModel accountModel;


    public Controller() {
        view = new ViewPL();
        clientModel = new ClientModel();
        accountModel = new AccountModel();
    }


    public void start() throws InterruptedException {

        int selectedOption;

        do {
            selectedOption = view.showMenuAndGetSelectedOption();

            switch (selectedOption) {

                case 1:
                    handleOptionOne();
                    break;
                case 2:
                    handleOptionTwo();
                    break;
                case 3:
                    handleOptionThree();
                    break;
                case 4:
                    handleOptionFour();
                    break;
                case 5:
                    handleOptionFive();
                    break;
                case 6:
                    handleOptionSix();
                    break;
                case 7:
                    System.out.println("Dziękujemy. Do widzenia.");
                    break;
            }


        } while (selectedOption != 7);


    }

    private void handleOptionOne() { //caly uporzadkowany case 1

        ClientData clientData = view.askForClientPersonalDataAndCreateClientData(); // zwracamy stworzonego w widoku clienta z wrpowadoznych scanerem danych
        clientModel.save(clientData); // zapisujemy clienta do bazy danych
        view.showClientInformation(clientData); // wyswietlamy info o jego pinie itp.

    }

    private void handleOptionTwo() throws InterruptedException {
        int clientPin = view.askForClientPin();
        ClientData clientData = clientModel.findByPin(clientPin); // wyszukaj klienta po pinie
        if (clientData != null) { //jezeli pin poprawny to utworz mu konto
            AccountData accountData = view.askForAccountDataAndCreateAccount(clientData);
            if (accountData != null) { // jezeli poprawny typ konta wpisany to konto zostalo utworzone. Teraz zapisz je do bazy
                accountModel.save(accountData);
            }
        } else {
            view.showInfoAboutIncorrectPinNumber();
        }
    }

    private void handleOptionThree() throws InterruptedException {
        int clientPin = view.askForClientPin();
        ClientData clientData = clientModel.findByPin(clientPin);
        if (clientData != null) {
            clientModel.getListOfClientAccounts(clientData);
        } else {
            view.showInfoAboutIncorrectPinNumber();
        }
    }

    private void handleOptionFour() throws InterruptedException {
        int clientPin = view.askForClientPin();
        ClientData clientData = clientModel.findByPin(clientPin);
        if (clientData != null) {
            clientModel.getListOfClientAccounts(clientData); // wyswietlamy liste jego kont aby wybral na ktore chce wplacic pieniadze
            int accountId = view.askForAccountIdWhenCashAdd(); // wybiera na ktore konto wplata
            AccountData accountData = accountModel.findByClientIdAnAccountId(clientData.getId(), accountId);// sprawdzamy czy podal poprawne id konta
            if (accountData != null) {
                BigDecimal impact = view.askForDeposit();// jaka kwota do wplaty
                accountData.setBalance(impact.add(accountData.getBalance())); // dodajemy wplate do konta
                accountModel.save(accountData);
                view.showAccountBalance(accountData);
            } else {
                view.showInfoAboutIncorrectAccountId();
            }

        } else {
            view.showInfoAboutIncorrectPinNumber();
        }
    }

    private void handleOptionFive() throws InterruptedException {
        int clientPin = view.askForClientPin();
        ClientData clientData = clientModel.findByPin(clientPin);
        if (clientData != null) {
            clientModel.getListOfClientAccounts(clientData); // wyswietlamy liste jego kont aby wybral na ktore chce wplacic pieniadze
            int accountId = view.askForAccountIdWhenCashWithdrawal(); // wybiera z ktorego konta wyplata
            AccountData accountData = accountModel.findByClientIdAnAccountId(clientData.getId(), accountId);// sprawdzamy czy podal poprawne id konta
            if (accountData != null) {

                BigDecimal withdrawal = view.askForWithDrawal();
                if (accountData.getBalance().subtract(withdrawal).compareTo(BigDecimal.ZERO) > 0) { // sprawdzenie czy na koncie jest wiecej srodkow niz chcemy wyplacic
                    accountData.setBalance(accountData.getBalance().subtract(withdrawal)); // robimy wyplate
                    accountModel.save(accountData); //updatujemy w bazie
                    view.showAccountBalance(accountData);

                } else {
                   view.showNotEnoughMoneyToWithDrawal();
                }
            } else {
               view.showInfoAboutIncorrectAccountId();
            }
        } else {
           view.showInfoAboutIncorrectPinNumber();
        }
    }

    private void handleOptionSix() throws InterruptedException {
        int code = view.askForEmpleyeesCodeToGetAccess();
        boolean ifCorrectCode = view.verifyAccessCode(code);
        if (ifCorrectCode){
            int option = view.showEmployeesMenuAndGetSelectedOption();

            switch (option){
                case 1:
                    //Bank.showAllClients();
                    clientModel.findAll().forEach(client -> System.out.println(client.getName() + " " + client.getSurname() + " " + client.getPesel()));
                    break;
                case 2:
                    //Bank.showAllAccounts();
                    accountModel.findAll().forEach(account -> System.out.println(account.getAccountType() + " " + account.getBalance() + " " + account.getAccountNumber()));

                    break;
                case 3:
                    int clientId = view.askForClientId();
                    accountModel.deleteAllAccountsWhileDeletingClient(clientId);
                    clientModel.deleteById(clientId);
                    view.showInfoAboutDeletingClientWithAllAccounts();
                    break;
                case 4:
                    int clientId1 = view.askForClientId();
                    ClientData clientData = clientModel.findById(clientId1);
                    if (clientData !=null){
                        clientModel.getListOfClientAccounts(clientData);
                        int accountIdToDelete = view.askForAccountIdToDelete();
                        accountModel.deleteByAccountId(accountIdToDelete);
                        view.showInfoAboutDeletingSingleAccount();
                    }else{
                        view.showInfoAboutIncorrectClientId();
                    }
                    break;
                case 5:
                    int clientId2 = view.askForClientId();
                    ClientData clientData1 = clientModel.findById(clientId2);
                    if (clientData1 !=null){
                        String newSurname = view.askForNewSurname();
                        clientModel.updateClient(clientData1, newSurname);

                    }else{
                        view.showInfoAboutIncorrectClientId();
                    }
                    break;
                case 6:
                    break;
            }

        }


    }
}





