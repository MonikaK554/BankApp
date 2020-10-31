package application;

import application.Bank;
import application.CaseMethods;
import database.dao.AccountDataDao;
import database.dao.ClientDataDao;
import database.daoImpl.AccountDataImpl;
import database.daoImpl.ClientDataImpl;

import java.util.Scanner;

public class MainMethod {

    public static void mainMethod() throws InterruptedException {

        Scanner scanner = new Scanner(System.in);
        AccountDataDao accountDataImpl = new AccountDataImpl();
        ClientDataDao clientDataImpl = new ClientDataImpl();

        int choice; // inicjowany przed petla do aby byl widoczny w while

        do {

            System.out.println("Witaj w " + Bank.name + " . Co chcesz zrobic? (Podaj cyfrę od 1 do 7)" + "\n"
                    + "1. Stworzenie nowego klienta" + "\n"
                    + "2. Dodanie rachunku dla klienta" + "\n"
                    + "3. Sprawdzenie salda posiadanych kont" + "\n"
                    + "4. Wpłata na rachunek" + "\n"
                    + "5. Wypłata" + "\n"
                    + "6. Funkcje dodatkowe dla pracowników banku" + "\n"
                    + "7. Wyjście");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    CaseMethods.case1();
                    break;
                case 2:
                    CaseMethods.case2();
                    break;
                case 3:
                    CaseMethods.case3();
                    break;
                case 4:
                    CaseMethods.case4();
                    break;
                case 5:
                    CaseMethods.case5();
                    break;

                case 6:
                    System.out.println("Tylko dla pracowników banku. Podaj PIN: ");
                    int pin = scanner.nextInt();
                    scanner.nextLine();

                    if (pin == 8345) {
                        System.out.println("Co chcesz zrobić? (Podaj cyfrę od 1 do 5)" + "\n"
                                + "1. Wyświetl listę wszystkich klientów" + "\n"
                                + "2. Wyświetl listę wszystkich rachunków" + "\n"
                                + "3. Usuń klienta" + "\n"   //wraz ze wszystkimi jego rachunkami oczywiście
                                + "4. Usuń rachunek dla klienta" + "\n"
                                + "5. Zmiana danych osobowych" + "\n"
                                + "6. Powrót do poprzedniego menu");

                        int choice2 = scanner.nextInt();

                        switch (choice2) {
                            case 1:
                                Bank.showAllClients();
                                break;
                            case 2:
                                Bank.showAllAccounts();
                                break;
                            case 3:
                                Bank.deleteClient();
                                break;
                            case 4:
                                Bank.deleteAccount();
                                break;
                            case 5:
                                clientDataImpl.updateClient();
                            case 6:
                                continue;
                        }
                    } else {
                        System.out.println("Wpisano niepoprawny PIN");
                        Thread.sleep(2000);
                        continue;
                    }
                    break;

                case 7:
                    System.out.println("Dziękujemy. Do widzenia.");
                    break;

                default:
                    System.out.println("Wybrano niepoprawny numer. Spróbuj ponownie.");
                    Thread.sleep(2000);
                    continue;
            }

            Thread.sleep(5000);

        } while (choice != 7);
    }
}









