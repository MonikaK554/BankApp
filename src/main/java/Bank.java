import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class Bank {

    static final String name = "MBank";

    public static List<String> accountNumberUniqueList = new ArrayList<>(); // do klasy ACCOUNT wykorzystywane checkingIfAccountNumberIsTrulyUnique
    public static List<Integer> clientUniqueIdList = new ArrayList<>(); // checkingIfIdIsTrulyUnique

    public static List<Account> allAccounts = new ArrayList<>(); // same rachunki wyswietlane
    public static List<Client> allClients = new ArrayList<>(); // wyswietlanie wszystkich klientow


    public static long createId() {
        Random random = new Random();
        int[] ids = new int[4];
        String id = "";
        for (int i = 0; i < ids.length; i++) {
            ids[i] = random.nextInt(10);
            id = id + ids[i];

        }

        int checkedId = Integer.parseInt(id);

        if (!clientUniqueIdList.contains(checkedId)) { // sprawdzenie czy wylosowanego id nie ma juz wczesniej dodanego do listy
            clientUniqueIdList.add(checkedId);        // jesli nie ma to go dodaj, aby lista byla kompletna przy nastepnym tworzeniu nowego id
            return checkedId;
        } else { // jak wrocic ponownie do wykonania metody jeszcze raz zeby znalazla inny nr id?
            System.out.println("Błędny numer id.");
            return 0;
        }

    }

    public static void showAllClients() {
        allClients.stream()
                .map(client -> client.getSurname() + " " + client.getName() + client.getListOfClientAccounts())
                .sorted()
                .forEach(System.out::println);
    }


    public static void showAllAccounts() {
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
                .filter(client -> client.getListOfClientAccounts().removeIf(account -> account.getBalance() == 0))// zostaw tylko tych klientow gdzie balance wiekszy niz 0
                .collect(Collectors.toList());

        return clientsWithBalanceMoreThanZero;
    }


    @Override
    public String toString() {
        return "Bank{" +
                Bank.name;
    }
}
