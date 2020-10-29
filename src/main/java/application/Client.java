package application;

import database.dao.AccountDataDao;
import database.dao.ClientDataDao;
import database.daoImpl.AccountDataImpl;
import database.daoImpl.ClientDataImpl;

import java.math.BigDecimal;
import java.util.*;

public class Client {

    private String name;
    private String surname;
    private long pesel;
    private long pin;
    private List<Account> listOfClientAccounts;

    public Client(String name, String surname, long pesel, List<Account> listOfClientAccounts) {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        pin = Bank.createPin();
        this.listOfClientAccounts = listOfClientAccounts;
    }

    public static Client createClient(String name, String surname, long pesel, String accountType) { // tworzenie nowego klienta przez metodę aby w main nie tworzyc application.Client client = new application.Client...

        ClientDataDao clientImp = new ClientDataImpl();
        AccountDataDao accountImpl = new AccountDataImpl();

        if (accountType.equalsIgnoreCase(AccountType.NONE.name())) {
            Client client = new Client(name, surname, pesel, new ArrayList<>()); // klient ma pusta, tylko zainicjalizowana liste

            Bank.allClients.add(client); //wpisujemy klienta na liste klientow w kodzie
            clientImp.saveClient(client); // wpisujemy klienta do bazy danych
            accountImpl.saveClientAccount(client);
            return client;

        } else if (accountType.equalsIgnoreCase(AccountType.PRO.name()) ||
                accountType.equalsIgnoreCase(AccountType.STANDARD.name()) ||
                accountType.equalsIgnoreCase(AccountType.STUDENT.name())) {

            System.out.println("Podaj saldo początkowe dla konta " + AccountType.valueOf(accountType.toUpperCase()));
            Scanner scanner = new Scanner(System.in);
            BigDecimal initialBalance = scanner.nextBigDecimal();

            Client client = new Client(name, surname, pesel, new ArrayList<>()); // w takie formie inicjalizacja listy, bo jak bylo Arrays.asList to nie mozna zrobic potem list.add()
            client.getListOfClientAccounts().add(Account.createAccount(AccountType.valueOf(accountType.toUpperCase()), initialBalance));

            Bank.allClients.add(client); //wpisujemy klienta na liste klientow w kodzie
            clientImp.saveClient(client); // wpisujemy klienta do bazy danych
            return client;

        }

        return null;
    }

    ////to samo co case 2
//    public Client addOtherAccountToList(String accountType, BigDecimal balance) { // dodanie nowego konta juz po utworzeniu klienta (w trakcie)
//       this.getListOfClientAccounts().add(Account.createAccount(AccountType.valueOf(accountType), balance));
//       return this;
//        }

    public Client cashAdd() {

        System.out.println("Podaj typ konta  na które chcesz wpłacić pieniądze: ");
        this.getListOfClientAccounts().stream() // wyswietla jakie klient ma konta bo THIS.
                .map(account -> account.getAccountType() + " " + account.getAccountNumber() + "Saldo: " + account.getBalance())
                .forEach(System.out::println);

        Scanner scanner = new Scanner(System.in);
        String choosenAccountType = scanner.nextLine();

        //wszystko robione na this, czyli na obiekcie na ktorym wykonywana jest metoda, nie trzeba go od poczatku wyszukiwac
        if (this.getListOfClientAccounts().stream()// sprawdzamy czy to co wpisal uzytkownik jest zgodne z jego typami ktore posiada
                .anyMatch(account -> account.getAccountType().name().equalsIgnoreCase(choosenAccountType))) {

            System.out.println("Podaj kwotę do wpłaty:");
            BigDecimal impact = scanner.nextBigDecimal();

            this.getListOfClientAccounts().stream() //zmiana salda
                    .filter(account -> account.getAccountType().name().equalsIgnoreCase(choosenAccountType))// znajdz konto ktore wpisal uzytkownik
                    .forEach(account -> account.setBalance(account.getBalance().add(impact))); // dla znalezionego konta dodaj pieniadze

            this.getListOfClientAccounts().stream() // wyswietlanie nowego salda
                    .filter(account -> account.getAccountType().name().equalsIgnoreCase(choosenAccountType))
                    .forEach(account -> System.out.println("Nowe saldo to: " + account.getBalance()));

        } else {
            System.out.println("Wybrano niepoprawne konto");
        }
        return this;
    }

    public Client withdrawal() {

        System.out.println("Podaj typ konta, z ktorego chcesz wypłacić pieniądze:  ");

        this.getListOfClientAccounts().stream()
                .map(account -> account.getAccountType() + " " + account.getAccountNumber() + "Saldo: " + account.getBalance())
                .forEach(System.out::println);

        Scanner scanner = new Scanner(System.in);
        String choosenAccountType = scanner.nextLine();

        if (this.getListOfClientAccounts().stream()// sprawdzamy czy to co wpisal uzytkownik jest zgodne z jego typami ktore posiada
                .anyMatch(account -> account.getAccountType().name().equalsIgnoreCase(choosenAccountType))) {

            System.out.println("Podaj kwotę do wypłaty (pamiętaj, że musi mieścić się ona w zakresie dostępnych śrdoków na koncie)");
            BigDecimal impact = scanner.nextBigDecimal();

            Optional<Account> choosenAccount = this.getListOfClientAccounts().stream() //zmiana salda
                    .filter(account -> account.getAccountType().name().equalsIgnoreCase(choosenAccountType))// znajdz konto ktore wpisal uzytkownik
                    .findFirst();

            if (choosenAccount.get().getBalance().subtract(impact).compareTo(BigDecimal.ZERO) > 0) { // sprawdzenie czy na koncie jest wiecej srodkow niz chcemy wyplacic, aby mozna bylo zrboic wyplate
                choosenAccount.get().setBalance(choosenAccount.get().getBalance().subtract(impact));

                this.getListOfClientAccounts().stream() // wyswietlanie nowego salda
                        .filter(account -> account.getAccountType().name().equalsIgnoreCase(choosenAccountType))
                        .forEach(account -> System.out.println("Nowe saldo to: " + account.getBalance()));

            } else {
                System.out.println("Brak wystarczających środków na koncie");
            }

        } else {
            System.out.println("Wybrano niepoprawne konto");
        }
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getPesel() {
        return pesel;
    }

    public void setPesel(long pesel) {
        this.pesel = pesel;
    }

    public long getPin() {
        return pin;
    }

    public void setPin(long pin) {
        this.pin = pin;
    }

    public List<Account> getListOfClientAccounts() {
        return listOfClientAccounts;
    }

    public void setListOfClientAccounts(List<Account> listOfClientAccounts) {
        this.listOfClientAccounts = listOfClientAccounts;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", pesel=" + pesel +
                ", pin=" + pin +
                ", listOfClientAccounts=" + listOfClientAccounts +
                '}';
    }
}






