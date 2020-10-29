package application;

import application.AccountType;
import application.Bank;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
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

            System.out.println("Podaj typ pierwszego konta, jakie chcesz utworzyć (wpisz NONE jeśli nie chcesz w tym momencie zakładać konta).");
            String accountType = scanner1.nextLine();

            if (accountType.equalsIgnoreCase(AccountType.NONE.name()) ||
                    accountType.equalsIgnoreCase(AccountType.STUDENT.name()) ||
                    accountType.equalsIgnoreCase(AccountType.PRO.name()) ||
                    accountType.equalsIgnoreCase(AccountType.STANDARD.name())) {

                System.out.println(Client.createClient(name, surname, pesel, accountType));
            }
        } else {
            System.out.println("Wpisano niepoprawny typ konta.");
        }
    }

    public static void case2() throws InterruptedException {
        System.out.println("Podaj pin");
        int clientPin = scanner1.nextInt();
        scanner1.nextLine();
        Client client2; // definiowany tutaj aby byl widoczny nie tylko w if ponizej ale tez poza if w drugim if

        if (Bank.allClients.stream().anyMatch(client -> client.getPin() == clientPin)) { //sprawdzamy czy pin poprawny (czy jest klient z takim pinem

            client2 = Bank.allClients.stream().filter(client -> client.getPin() == clientPin).findAny().get(); // jezeli jest to go wez wyjmij

            System.out.println("Podaj typ rachunku, jaki chcesz założyć: " + "\n" + "STUDENT, STANDARD, PRO" + "\n" + "Podaj jedną wartość.");
            String newAccountType1 = scanner1.nextLine();

            if (newAccountType1.equalsIgnoreCase(AccountType.PRO.name()) || newAccountType1.equalsIgnoreCase(AccountType.STANDARD.name()) ||
                    newAccountType1.equalsIgnoreCase(AccountType.STUDENT.name())) { // jak pin juz sprawdzone to trzeba sprawdzic czy typ konta do zalozenia podany poprawnie

                System.out.println("Podaj kwotę salda początkowego:");
                BigDecimal balance1 = scanner1.nextBigDecimal();
                scanner1.nextLine();
                client2.getListOfClientAccounts().add(Account.createAccount(AccountType.valueOf(newAccountType1.toUpperCase()), balance1));
                System.out.println("Konto zosało utworzone.");

            } else {
                System.out.println("Wybrano niepoprawny typ konta");
            }

        } else {
            System.out.println("Podano niepoprawny pin");
            Thread.sleep(2000);
            //  continue;
        }
    }

    public static void case3() throws InterruptedException {

        System.out.println("Podaj pin");
        int clientPin3 = scanner1.nextInt();
        scanner1.nextLine();
        Client client3; // definiowany tutaj aby byl widoczny nie tylko w if ponizej ale tez poza if w drugim if

        if (Bank.allClients.stream() // sprawdzamy czy poprawny pin
                .anyMatch(client -> client.getPin() == clientPin3)) {

            System.out.println(Bank.allClients.stream().filter(client -> client.getPin() == clientPin3)
                    .findAny().get().getListOfClientAccounts()); // i wyswietl jego liste rachunkow

        } else {
            System.out.println("Podano niepoprawny pin");
            Thread.sleep(2000);
        }
    }

    public static void case4() throws InterruptedException {
        System.out.println("Podaj pin");
        int clientPin4 = scanner1.nextInt();
        scanner1.nextLine();
        Client client4; // definiowany tutaj aby byl widoczny nie tylko w if ponizej ale tez poza if w drugim if

        if (Bank.allClients.stream() // sprawdzamy czy poprawny pin
                .anyMatch(client -> client.getPin() == clientPin4)) {

            client4 = Bank.allClients.stream().filter(client -> client.getPin() == clientPin4)
                    .findAny().get();

            client4.cashAdd();

        } else {
            System.out.println("Podano niepoprawny");
            Thread.sleep(2000);
        }
    }

    public static void case5() throws InterruptedException {
        System.out.println("Podaj pin");
        int clientPin5 = scanner1.nextInt();
        scanner1.nextLine();
        Client client5; // definiowany tutaj aby byl widoczny nie tylko w if ponizej ale tez poza if w drugim if

        if (Bank.allClients.stream() // sprawdzamy czy poprawny
                .anyMatch(client -> client.getPin() == clientPin5)) {

            client5 = Bank.allClients.stream().filter(client -> client.getPin() == clientPin5).findFirst().get();
            client5.withdrawal();

        } else {
            System.out.println("Podano niepoprawne id");
            Thread.sleep(2000);
        }
    }
}
