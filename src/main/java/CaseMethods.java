import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CaseMethods {

    static Scanner scanner1 = new Scanner(System.in);

    public static void case1() {

        System.out.println("Podaj imię: ");
        String name = scanner1.nextLine();
        System.out.println("Podaj nazwisko: ");
        String surname = scanner1.nextLine();
        System.out.println("Podaj pesel: ");
        long pesel = scanner1.nextLong();
        if (DataValidator.peselValidator(pesel) && DataValidator.nameAndSurnameValidator(name, surname)) { // rowne true

            scanner1.nextLine(); //aby pozbyc się pustego znaku (spacji po wpisywaniu naprzemian String, int/long)

            System.out.println("Podaj typ pierwszego konta, jakie chcesz utworzyć (wpisz NULL jeśli nie chcesz w tym momencie zakładać konta).");
            String accountType = scanner1.nextLine();

            if (accountType.equalsIgnoreCase(AccountType.STUDENT.name())) {
                System.out.println(Client.createClient(name, surname, pesel, AccountType.STUDENT));

            } else if (accountType.equalsIgnoreCase(AccountType.PRO.name())) {
                System.out.println(Client.createClient(name, surname, pesel, AccountType.PRO));

            } else if (accountType.equalsIgnoreCase(AccountType.STANDARD.name())) {
                System.out.println(Client.createClient(name, surname, pesel, AccountType.STANDARD));

            } else if (accountType.equalsIgnoreCase("null")) {
                System.out.println(Client.createClient(name, surname, pesel, AccountType.NONE));
            } else {
                System.out.println("Wpisano niepoprawny typ konta.");
            }
        }
    }

    public static void case2() throws InterruptedException {
        System.out.println("Podaj swoje id klienta");
        int clientId2 = scanner1.nextInt();
        scanner1.nextLine();
        Client client2; // definiowany tutaj aby byl widoczny nie tylko w if ponizej ale tez poza if w drugim if

        if (Bank.allClients.stream() // sprawdzamy czy poprawne id
                .filter(client1 -> client1.getId() == clientId2)
                .toArray().length > 0) {  // zamieniamy strumien na tablice i jak tablica cos zawiera tzn ze poprawne id

            List<Client> list = Bank.allClients.stream().filter(client1 -> client1.getId() == clientId2).collect(Collectors.toList());

            client2 = list.get(0);

            System.out.println("Podaj typ rachunku, jaki chcesz założyć: " + "\n" + "STUDENT, STANDARD, PRO" + "\n" + "Podaj jedną wartość.");
            String newAccountType = scanner1.nextLine();

            if (newAccountType.equalsIgnoreCase(AccountType.PRO.name()) || newAccountType.equalsIgnoreCase(AccountType.STANDARD.name()) ||
                    newAccountType.equalsIgnoreCase(AccountType.STUDENT.name())) { // jak id juz sprawdzone to trzeba sprawdzic czy typ konta do zalozenia podany poprawnie

                System.out.println("Podaj kwotę salda początkowego:");
                double balance = scanner1.nextDouble();
                scanner1.nextLine();
                client2.addOtherAccountToList(newAccountType, balance);

            } else {
                System.out.println("Wybrano niepoprawny typ konta");

            }

        } else {
            System.out.println("Podano niepoprawne id");
            Thread.sleep(2000);
            //  continue;
        }
    }


    public static void case3() throws InterruptedException {

        System.out.println("Podaj swoje id klienta");
        int clientId3 = scanner1.nextInt();
        scanner1.nextLine();
        Client client3; // definiowany tutaj aby byl widoczny nie tylko w if ponizej ale tez poza if w drugim if

        if (Bank.allClients.stream() // sprawdzamy czy poprawne id
                .filter(client1 -> client1.getId() == clientId3)
                .toArray().length > 0) {  // zamieniamy strumien na tanblice, i jak tablica cos zawiera tzn ze poprawne id

            List<Client> list = Bank.allClients.stream().filter(client1 -> client1.getId() == clientId3).collect(Collectors.toList());
            client3 = list.get(0);
            System.out.println(client3.getListOfClientAccounts());

        } else {
            System.out.println("Podano niepoprawne id");
            Thread.sleep(2000);

        }
    }

    public static void case4() throws InterruptedException {
        System.out.println("Podaj swoje id klienta");
        int clientId4 = scanner1.nextInt();
        scanner1.nextLine();
        Client client4; // definiowany tutaj aby byl widoczny nie tylko w if ponizej ale tez poza if w drugim if

        if (Bank.allClients.stream() // sprawdzamy czy poprawne id
                .filter(client1 -> client1.getId() == clientId4)
                .toArray().length > 0) {  // zamieniamy strumien na tanblice, i jak tablica cos zawiera tzn ze poprawne id

            List<Client> list = Bank.allClients.stream().filter(client1 -> client1.getId() == clientId4).collect(Collectors.toList());
            client4 = list.get(0);
            client4.cashAdd();
        } else {
            System.out.println("Podano niepoprawne id");
            Thread.sleep(2000);
            //    continue;
        }
    }

    public static void case5() throws InterruptedException {
        System.out.println("Podaj swoje id klienta");
        int clientId5 = scanner1.nextInt();
        scanner1.nextLine();
        Client client5; // definiowany tutaj aby byl widoczny nie tylko w if ponizej ale tez poza if w drugim if

        if (Bank.allClients.stream() // sprawdzamy czy poprawne id
                .filter(client1 -> client1.getId() == clientId5)
                .toArray().length > 0) {  // zamieniamy strumien na tanblice, i jak tablica cos zawiera tzn ze poprawne id

            List<Client> list = Bank.allClients.stream().filter(client1 -> client1.getId() == clientId5).collect(Collectors.toList());
            client5 = list.get(0);
            client5.withdrawal();

        } else {
            System.out.println("Podano niepoprawne id");
            Thread.sleep(2000);
            //continue;
        }

    }


}
