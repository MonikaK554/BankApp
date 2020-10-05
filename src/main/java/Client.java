import java.util.*;
import java.util.stream.Collectors;

public class Client {

    private String name;
    private String surname;
    private long pesel;
    private long id;
    private List<Account> listOfClientAccounts;


    public Client(ClientBuilder builder) {
        this.name = builder.name;
        this.surname = builder.surname;
        this.pesel = builder.pesel;
        this.id = builder.id;
        this.listOfClientAccounts = builder.listOfClientAccounts;
    }




    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public long getPesel() {
        return pesel;
    }

    public long getId() {
        return id;
    }

    public List<Account> getListOfClientAccounts() {
        return listOfClientAccounts;
    }


//  public void setListOfClientAccounts(List<Account> listOfAccounts) {  //SET nieuzyty bo dodajac nowe konto po prostu robie listOFClientAccounts.add() zamiast set na nowo
//    this.listOfClientAccounts = listOfClientAccounts;
//}

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", pesel=" + pesel +
                ", id=" + id +
                ", listOfAccounts=" + "\n" + listOfClientAccounts +
                '}';
    }


    public static class ClientBuilder {

        private String name;
        private String surname;
        private long pesel;
        private long id;
        private List<Account> listOfClientAccounts;

        public ClientBuilder(String name, String surname, long pesel) {
            this.name = name;
            this.surname = surname;
            this.pesel = pesel;
            id = Bank.createId();
        }

        public ClientBuilder addNewListOfAccounts(AccountType[] typeOfAccount) { // metoda dodająca konta przy tworzeniu klienta pracujaca na ClientBuilderze (jedno kont na poczatek mozna utworzyc). Potem nastepme ,pzma dpdawac druga metoda
            listOfClientAccounts = new ArrayList<>();

                System.out.println("Podaj saldo początkowe dla konta " + Arrays.toString(typeOfAccount));
                Scanner scanner = new Scanner(System.in);
                double balance = scanner.nextDouble();
                Account account = Account.createAccount(typeOfAccount[0], balance); //stworzenie konta

                listOfClientAccounts.add(account); // dodanie konta do listy kont danego klienta
                Bank.allAccounts.add(account); // dodanie konta na listę wszystkich kont w calym banku

            this.listOfClientAccounts = listOfClientAccounts; // przypisanie do kontruktora . Czy to jest poprawny zapis przypisania nowego atrybutu do konstruktora?
            return this;
        }


        public Client build() { // metoda budująca. Teraz trzeba stworzyc konstruktor w klasie glownej oparty na ClientBuilderze
            return new Client(this);
        }

    }

    public Client addOtherAccountToList(String accountType, double balance) { // dodanie nowego konta juz po utworzeniu klienta (w trakcie). Pracujemy juz na kliencie nie na klientBuilderze
                    if (this.getListOfClientAccounts() == null) { // jezeli klient przy zakladaniu konta mial liste kont jako null to przed dodaniem pierwszego konta musimy sie zabezpieczyc przed null pointer (dodanie konta do null skutkuje nullpointerem)
                        listOfClientAccounts = new ArrayList<>();

                    }

        Account account1;
        if (accountType.equalsIgnoreCase(AccountType.STUDENT.name())) {
            account1 = Account.createAccount(AccountType.STUDENT, balance);

            //this.getListOfClientAccounts().add(account1);
            listOfClientAccounts.add(account1);

            Bank.allAccounts.add(account1); // dodanie na listę wszystkich kont w calym banku ( w kazdym miejscu gdzie tworzymy konto, dodajemy je do ogolnej listy kont)
            System.out.println("Operacja dodania nowego konta przebiegła pomyślnie.");
            return this;
            }
        else if (accountType.equalsIgnoreCase(AccountType.PRO.name())) {
            account1 = Account.createAccount(AccountType.PRO, balance);

            //this.getListOfClientAccounts().add(account1);
            listOfClientAccounts.add(account1);

            Bank.allAccounts.add(account1); // dodanie na listę wszystkich kont w calym banku ( w kazdym miejscu gdzie tworzymy konto, dodajemy je do ogolnej listy kont)
            System.out.println("Operacja dodania nowego konta przebiegła pomyślnie.");
            return this;
        }
        else if (accountType.equalsIgnoreCase(AccountType.STANDARD.name())) {
            account1 = Account.createAccount(AccountType.STANDARD, balance);
            //this.getListOfClientAccounts().add(account1);
            listOfClientAccounts.add(account1);

            Bank.allAccounts.add(account1); // dodanie na listę wszystkich kont w calym banku ( w kazdym miejscu gdzie tworzymy konto, dodajemy je do ogolnej listy kont)
            System.out.println("Operacja dodania nowego konta przebiegła pomyślnie.");
            return this;
        }else {

            return null;
        }
    }


    public static Client createClient(String name, String surname, long pesel, AccountType ... accountType) { // tworzenie nowego klienta przez metodę aby w main nie tworzyc Client client = new Client...

        if (accountType[0] == AccountType.NONE) {
            Client client = new Client.ClientBuilder(name, surname, pesel).build();

            Bank.allClients.add(client);
            return client;
        } else {
            Client client = new Client.ClientBuilder(name, surname, pesel).addNewListOfAccounts(accountType).build(); // twprzy liste jednoelementowa aby bylo juz w formacie list, ale na poczarku mozna tylko jedno konto utworzzyc

            Bank.allClients.add(client);
            return client;

        }
    }

    public Client cashAdd() {

        System.out.println("Podaj typ konta  na które chcesz wpłacić pieniądze: ");
        this.getListOfClientAccounts().stream() // wyswietla jakie klient ma konta
               .map(account -> account.getAccountType() + " " + account.getAccountNumber() + "Saldo: " + account.getBalance())
               .forEach(System.out::println);

        Scanner scanner = new Scanner(System.in);
        String choosenAccountType = scanner.nextLine();

        if (this.getListOfClientAccounts().stream()  // sprawdzamy czy to co wpisal uzytkownik jest zgodne z jego typami ktore posiada
                .filter(account -> account.getAccountType().name().equalsIgnoreCase(choosenAccountType))
                .toArray().length>0) {

            System.out.println("Podaj kwotę do wpłaty:");
            double impact = scanner.nextDouble();

            this.getListOfClientAccounts().stream() //zmiana salda
                    .filter(account -> account.getAccountType().name().equalsIgnoreCase(choosenAccountType)) // znajdz konto ktore wpisal uzytkownik
                    .forEach(account -> account.setBalance(account.getBalance() + impact)); // dla znalezionego konta dodaj pieniadze

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

        if (this.getListOfClientAccounts().stream()  // sprawdzamy czy to co wpisal uzytkownik jest zgodne z jego typami ktore posiada
                .filter(account -> account.getAccountType().name().equalsIgnoreCase(choosenAccountType))
                .toArray().length>0) {

            System.out.println("Podaj kwotę do wypłaty (pamiętaj, że musi mieścić się ona w zakresie dostępnych śrdoków na koncie)");
            double impact = scanner.nextDouble();

            List<Account> choosenAccount = this.getListOfClientAccounts().stream() //zmiana salda
                    .filter(account -> account.getAccountType().name().equalsIgnoreCase(choosenAccountType))// znajdz konto ktore wpisal uzytkownik
                   .collect(Collectors.toList()); // zamien to na typ LIST aby mozna bylo się do tego odnieść w if

            if (choosenAccount.get(0).getBalance() > impact) {
                choosenAccount.get(0).setBalance(choosenAccount.get(0).getBalance() - impact);

            } else{
                System.out.println("Brak wystarczających środków na koncie");
            }

            this.getListOfClientAccounts().stream() // wyswietlanie nowego salda
                    .filter(account -> account.getAccountType().name().equalsIgnoreCase(choosenAccountType))
                    .forEach(account -> System.out.println("Nowe saldo to: " + account.getBalance()));

        } else {
            System.out.println("Wybrano niepoprawne konto");
        }
        return this;
    }



}

