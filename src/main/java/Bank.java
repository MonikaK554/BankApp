import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Bank {

    static final String name = "MBank";

    public static List <Account> allAccounts = new ArrayList<>(); // same rachunki wyswietlane
    public static List<Client> allClients = new ArrayList<>(); // wyswietlanie wszystkich klientow


    public static void showAllClients (){
        allClients.stream()
                .map(client -> client.getSurname() + " " + client.getName() + client.getListOfClientAccounts())
                .sorted()
                .forEach(System.out::println);
    }


    public static void showAllAccounts(){
        allAccounts.stream().forEach(System.out::println);
    }


    public static List<Client> deleteClientIfHasNoAccounts (){


       List<Client> onlyClientsWithAccounts =  allClients.stream()
               .filter(client -> client.getListOfClientAccounts() !=null) // zostaw tylko tych klientow, ktorzy maja jakies elementy(rachunki) na liscie
               .collect(Collectors.toList());

        System.out.println("Tylko klienci z otwartymi rachunkami");
        allClients = onlyClientsWithAccounts; // aktualizacja listy wszyskich klientow po usunieciu
       return onlyClientsWithAccounts;
    }


    public static List<Client> deleteAccountIfBalanceIsZero (){ // klient zostaje tylko rachunek mu usuwamy


        List <Client> clientsWithBalanceMoreThanZero = allClients.stream()
                .filter(client -> client.getListOfClientAccounts().removeIf(account -> account.getBalance() ==0))// zostaw tylko tych klientow gdzie balance wiekszy niz 0
                .collect(Collectors.toList());

        return clientsWithBalanceMoreThanZero;
    }


    @Override
    public String toString() {
        return "Bank{" +
                Bank.name;
    }
}
