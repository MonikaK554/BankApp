package application;

import database.daoImpl.AccountDataImpl;
import database.daoImpl.ClientDataImpl;
import database.entity.AccountData;
import database.entity.ClientData;

import java.math.BigDecimal;
import java.util.*;

public class CaseMethods {

    static Scanner scanner1 = new Scanner(System.in);
    static ClientDataImpl clientDataImpl = new ClientDataImpl();
    static AccountDataImpl accountDataImpl = new AccountDataImpl();

    public static void case1() {

        System.out.println("Podaj imię: ");
        String name = scanner1.nextLine();
        System.out.println("Podaj nazwisko: ");
        String surname = scanner1.nextLine();
        System.out.println("Podaj pesel: ");
        long pesel = scanner1.nextLong();
        if (DataValidator.peselValidator(pesel) && DataValidator.nameAndSurnameValidator(name, surname)) { // rowne true

            scanner1.nextLine(); //aby pozbyc się pustego znaku (spacji po wpisywaniu naprzemian String, int/long)

            ClientData clientData = new ClientData();
            clientData.setName(name);
            clientData.setSurname(surname);
            clientData.setPesel(pesel);
            clientData.setPin(Bank.createPin());

            clientDataImpl.save(clientData);
            System.out.println("Klient został utworzony"); // rekord w bazie danych
            System.out.println("Numer id klienta to: " + clientData.getId());
            System.out.println("Numer PIN to: " + clientData.getPin());

        }
    }

    public static void case2() throws InterruptedException {
        System.out.println("Podaj pin");
        int clientPin = scanner1.nextInt();
        scanner1.nextLine();

        if (clientDataImpl.findAll().stream().anyMatch(clientData -> clientData.getPin() == clientPin)) {  // jezeli pin jest poprawny

            ClientData clientData = clientDataImpl.findByPin(clientPin);  //to znajdz tego klienta i zwroc bo bedzie potrzebny do accountData.setClientData(clientData) przy tworzeniu wiersza w accounts_data

            System.out.println("Podaj typ konta, jakie chcesz utworzyć [STUDENT / STANDARD /PRO].");
            String accountType = scanner1.nextLine();

            if (accountType.equalsIgnoreCase(AccountType.STUDENT.name()) ||
                    accountType.equalsIgnoreCase(AccountType.PRO.name()) ||
                    accountType.equalsIgnoreCase(AccountType.STANDARD.name())) {

                System.out.println("Podaj kwotę salda początkowego:");
                BigDecimal balance1 = scanner1.nextBigDecimal();
                scanner1.nextLine();

                AccountData accountData = new AccountData(); // tworzymy nowy wiersz tabeli accounts_data i ustawiamy parametry

                accountData.setAccountType(AccountType.valueOf(accountType.toUpperCase()));
                accountData.setBalance(balance1);
                accountData.setAccountNumber(Account.createUniqueAccountNumber());
                accountData.setClientData(clientData); // stad wezmie id_clienta

                accountDataImpl.save(accountData);

            }
        } else {
            System.out.println("Podano niepoprawny PIN.");
            Thread.sleep(2000);
        }

    }

    public static void case3() throws InterruptedException {

        System.out.println("Podaj pin");
        int clientPin3 = scanner1.nextInt();
        scanner1.nextLine();

        if (clientDataImpl.findAll().stream().anyMatch(clientData -> clientData.getPin() == clientPin3)) {

            List<AccountData> listOfAllAccounts = clientDataImpl.findByPin(clientPin3).getAccountList();
            listOfAllAccounts.forEach(accountData -> System.out.println("Numer porządkowy " + accountData.getAccountId() + " " + accountData.getAccountType() + " " + accountData.getBalance()));

        } else {
            System.out.println("Podano niepoprawny PIN");
            Thread.sleep(2000);
        }

    }

    public static void case4() throws InterruptedException {

        System.out.println("Podaj pin");
        int clientPin4 = scanner1.nextInt();
        scanner1.nextLine();

        if (clientDataImpl.findAll().stream().anyMatch(clientData -> clientData.getPin() == clientPin4)) {

            Integer clientId = clientDataImpl.findByPin(clientPin4).getId(); // znajdujemy tego klienta, aby potem na podstawie client_id i No znalezc rekord w account_data zmienic jego balance

            List<AccountData> listOfAllAccounts = clientDataImpl.findByPin(clientPin4).getAccountList();
            listOfAllAccounts.forEach(accountData -> System.out.println("Numer porządkowy " + accountData.getAccountId() + " " + accountData.getAccountType() + " " + accountData.getBalance())); // dziala ale sie brzydko wyswietla. DO POPRAWY. wyswietlamy jego listę

            System.out.println("Podaj numer porządkowy swojego konta, na które chcesz wpłacić pieniądze.");
            Integer accountId = scanner1.nextInt();

            AccountData accountData = accountDataImpl.findByClientIdAnAccountId(clientId, accountId);

            System.out.println("Podaj kwotę do wpłaty:");
            BigDecimal impact = scanner1.nextBigDecimal();

            accountData.setBalance(impact.add(accountData.getBalance())); // dodajemy wplate do konta
            accountDataImpl.save(accountData); //updatujemy w bazie
            System.out.println("Nowe saldo wynosi " + accountData.getBalance());

        } else {
            System.out.println("Podano niepoprawny");
            Thread.sleep(2000);
        }
    }

    public static void case5() throws InterruptedException {

        System.out.println("Podaj pin");
        int clientPin5 = scanner1.nextInt();
        scanner1.nextLine();

        if (clientDataImpl.findAll().stream().anyMatch(clientData -> clientData.getPin() == clientPin5)) {

            Integer clientId = clientDataImpl.findByPin(clientPin5).getId(); // znajdujemy tego klienta, aby potem na podstawie client_id i No znalezc rekord w account_data zmienic jego balance

            List<AccountData> listOfAllAccounts = clientDataImpl.findByPin(clientPin5).getAccountList();
            listOfAllAccounts.forEach(accountData -> System.out.println("Numer porządkowy " + accountData.getAccountId() + " " + accountData.getAccountType() + " " + accountData.getBalance())); // dziala ale sie brzydko wyswietla. DO POPRAWY. wyswietlamy jego listę

            System.out.println("Podaj numer porządkowy swojego konta, z którego chcesz wypłacić pieniądze.");
            Integer accountId = scanner1.nextInt();

            AccountData accountData = accountDataImpl.findByClientIdAnAccountId(clientId, accountId);

            System.out.println("Podaj kwotę do wypłaty:");
            BigDecimal impact = scanner1.nextBigDecimal();

            if (accountData.getBalance().subtract(impact).compareTo(BigDecimal.ZERO) > 0) {// sprawdzenie czy na koncie jest wiecej srodkow niz chcemy wyplacic

                accountData.setBalance(accountData.getBalance().subtract(impact)); // robimy wyplate
                accountDataImpl.save(accountData); //updatujemy w bazie
                System.out.println("Nowe saldo wynosi " + accountData.getBalance());
            }else {
                System.out.println("Nie masz wystarczających środków na koncie.");
            }

        } else {
            System.out.println("Podano niepoprawny PIN");
            Thread.sleep(2000);
        }


    }
}

