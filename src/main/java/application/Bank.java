package application;

import database.dao.AccountDataDao;
import database.dao.ClientDataDao;
import database.daoImpl.AccountDataImpl;
import database.daoImpl.ClientDataImpl;
import database.entity.AccountData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Bank {

    static final String name = "MBank";

    public static List<String> accountNumberUniqueList = new ArrayList<>(); // do klasy ACCOUNT wykorzystywane checkingIfAccountNumberIsTrulyUnique
    public static List<Integer> clientUniquePinList = new ArrayList<>(); // checkingIfPinIsTrulyUnique

    static Scanner scanner = new Scanner(System.in);
    static ClientDataDao clientDataImpl = new ClientDataImpl();
    static AccountDataDao accountDataImpl = new AccountDataImpl();

    public static Integer createPin() {
        Random random = new Random();
        int[] pins = new int[4];
        String pin = "";
        for (int i = 0; i < pins.length; i++) {
            pins[i] = random.nextInt(10);
            pin = pin + pins[i];
        }

        int checkedPin = Integer.parseInt(pin); // to co się wylosowalo zamieniamy na int

        if (!clientUniquePinList.contains(checkedPin)) { // sprawdzenie czy wylosowanego pinu nie ma juz wczesniej dodanego do listy
            clientUniquePinList.add(checkedPin);        // jesli nie ma to go dodaj, aby lista byla kompletna przy nastepnym tworzeniu nowego id
            return checkedPin;
        } else { // jak wrocic ponownie do wykonania metody jeszcze raz zeby znalazla inny pin?
            System.out.println("Błędny numer pin wygenerowany.");
            return 0;
        }
    }

    public static void showAllClients() {

        ClientDataImpl clientData = new ClientDataImpl();
        clientData.findAll().forEach(client -> System.out.println(client.getName() + " " + client.getSurname() + " " + client.getPesel()));
    }


    public static void showAllAccounts() {

        AccountDataDao accountData = new AccountDataImpl();
        accountData.findAll().forEach(account -> System.out.println(account.getAccountType() + " " + account.getBalance() + " " + account.getAccountNumber()));
    }


    public static void deleteClient() {

        System.out.println("Podaj id klienta, którego chcesz usunąć.");
        Integer givenId = scanner.nextInt();

        accountDataImpl.deleteAllAccountsWhileDeletingClient(givenId); // najpierw trzeba usunąc wszystkie rachunki a potem klienta bo wiazanie(relacja)
        clientDataImpl.deleteById(givenId);

        System.out.println("Operacja usunięcia klienta się powiodła.");

    }

    public static void deleteAccount() {

        System.out.println("Podaj id klienta.");
        Integer givenId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Podaj numer porządkowy konta, które ma zostać usunięte.");
        System.out.println();

        List<AccountData> listOfClientAccounts = clientDataImpl.findById(givenId).getAccountList();
        listOfClientAccounts.forEach(accountData -> System.out.println("Numer porządkowy " + accountData.getAccountId() + " " + accountData.getAccountType() + " " + accountData.getBalance()));

        Integer accountIdToDelete = scanner.nextInt();
        accountDataImpl.deleteByAccountId(accountIdToDelete);
        System.out.println("Konto zostało usunięte.");

    }
}
