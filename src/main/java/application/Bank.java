package application;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class Bank {

    static final String name = "MBank";

    public static List<String> accountNumberUniqueList = new ArrayList<>(); // do klasy ACCOUNT wykorzystywane checkingIfAccountNumberIsTrulyUnique
    public static List<Integer> clientUniquePinList = new ArrayList<>(); // checkingIfPinIsTrulyUnique

    public static List<Account> allAccounts = new ArrayList<>(); // same rachunki wyswietlane
    public static List<Client> allClients = new ArrayList<>(); // wyswietlanie wszystkich klientow

    public static long createPin() {
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

    public static void showAllClients() { // mozna bedzie to potem usunac i pobierac po prostu z bazy danych findAll()
        allClients.stream()
                .map(client -> client.getSurname() + " " + client.getName() + client.getListOfClientAccounts())
                .sorted()
                .forEach(System.out::println);
    }

    public static void showAllAccounts() {  // tak samo mozna potem usunac i pobierac z bazy danych
        allAccounts.stream().forEach(System.out::println);
    }

    public static List<Client> deleteClientIfHasNoAccounts() {

        List<Client> onlyClientsWithAccounts = allClients.stream()
                .filter(client -> client.getListOfClientAccounts() != null) // zostaw tylko tych klientow, ktorzy maja jakies elementy(rachunki) na liscie
                .collect(Collectors.toList());

        System.out.println("Tylko klienci z otwartymi rachunkami");
        allClients = onlyClientsWithAccounts; // aktualizacja listy wszyskich klientow po usunieciu
        return onlyClientsWithAccounts;
    }

    public static List<Client> deleteAccountIfBalanceIsZero() { // klient zostaje tylko rachunek mu usuwamy

        List<Client> clientsWithBalanceMoreThanZero = allClients.stream()
                        .filter(client -> client.getListOfClientAccounts()
                        .removeIf(account -> account.getBalance().equals(new BigDecimal(0.00))))// zostaw tylko tych klientow gdzie balance wiekszy niz 0
                        .collect(Collectors.toList());

        return clientsWithBalanceMoreThanZero;
    }

    @Override
    public String toString() {
        return "application.Bank{" +
                Bank.name;
    }
}
