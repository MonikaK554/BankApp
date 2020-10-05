import java.awt.image.BandedSampleModel;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Methods {

   public static void mainMethod() throws InterruptedException {

      Scanner scanner = new Scanner(System.in);
      int choice; // inicjowany przed petla do aby byl widoczny w while

do {

   System.out.println("Witaj w " + Bank.name + " . Co chcesz zrobic? (Podaj cyfrę od 1 do 7)" + "\n"
           + "1. Stworzenie nowego klienta" + "\n"
           + "2. Dodanie rachunku dla istniejącego klienta" + "\n"
           + "3. Sprawdzenie salda posiadanych kont" + "\n"
           + "4. Wpłata na rachunek" + "\n"
           + "5. Wypłata" + "\n"
           + "6. Funkcje dodatkowe dla pracowników banku" + "\n"
           + "7. Wyjście");

   choice = scanner.nextInt();
   scanner.nextLine();


   switch (choice) {

      case 1:
         System.out.println("Podaj imię: ");
         String name = scanner.nextLine();
         System.out.println("Podaj nazwisko: ");
         String surname = scanner.nextLine();
         System.out.println("Podaj pesel: ");
         long pesel = scanner.nextLong();
         if (DataValidator.peselValidator(pesel)&& DataValidator.nameAndSurnameValidator(name, surname) ){ // rowne true

         scanner.nextLine(); //aby pozbyc się pustego znaku (spacji po wpisywaniu naprzemian String, int/long)

         System.out.println("Podaj typ pierwszego konta, jakie chcesz utworzyć (wpisz NULL jeśli nie chcesz w tym momencie zakładać konta).");
         String accountType = scanner.nextLine();

         if (accountType.equalsIgnoreCase(AccountType.STUDENT.name())) {
            System.out.println(Client.createClient(name, surname, pesel, AccountType.STUDENT));

         }else if (accountType.equalsIgnoreCase(AccountType.PRO.name())) {
            System.out.println(Client.createClient(name, surname, pesel, AccountType.PRO));

         }else if (accountType.equalsIgnoreCase(AccountType.STANDARD.name())) {
            System.out.println(Client.createClient(name, surname, pesel, AccountType.STANDARD));

         } else if (accountType.equalsIgnoreCase("null")){
            System.out.println(Client.createClient(name, surname, pesel, AccountType.NONE));
         }
         else {
            System.out.println("Wpisano niepoprawny typ konta.");
            continue;
         }
         }
         break;

      case 2:
         System.out.println("Podaj swoje id klienta");
         int clientId2 = scanner.nextInt();
         scanner.nextLine();
         Client client2; // definiowany tutaj aby byl widoczny nie tylko w if ponizej ale tez poza if w drugim if

         if (Bank.allClients.stream() // sprawdzamy czy poprawne id
                .filter(client1 -> client1.getId() == clientId2)
                .toArray().length >0) {  // zamieniamy strumien na tablice i jak tablica cos zawiera tzn ze poprawne id

            List<Client> list = Bank.allClients.stream().filter(client1 -> client1.getId() == clientId2).collect(Collectors.toList());

            client2 = list.get(0);

         } else {
            System.out.println("Podano niepoprawne id");
            Thread.sleep(2000);
            continue;
         }

         System.out.println("Podaj typ rachunku, jaki chcesz założyć: " + "\n" + "STUDENT, STANDARD, PRO" + "\n" + "Podaj jedną wartość.");
         String newAccountType = scanner.nextLine();

         if (newAccountType.equalsIgnoreCase(AccountType.PRO.name()) || newAccountType.equalsIgnoreCase(AccountType.STANDARD.name()) ||
                 newAccountType.equalsIgnoreCase(AccountType.STUDENT.name())) { // jak id juz sprawdzone to trzeba sprawdzic czy typ konta do zalozenia podany poprawnie

            System.out.println("Podaj kwotę salda początkowego:");
            double balance = scanner.nextDouble();
            scanner.nextLine();
            client2.addOtherAccountToList(newAccountType, balance);

         } else {
            System.out.println("Wybrano niepoprawny typ konta");

         }
          break;

      case 3:
         System.out.println("Podaj swoje id klienta");
          int clientId3 = scanner.nextInt();
         scanner.nextLine();
           Client client3; // definiowany tutaj aby byl widoczny nie tylko w if ponizej ale tez poza if w drugim if

         if (Bank.allClients.stream() // sprawdzamy czy poprawne id
                 .filter(client1 -> client1.getId() == clientId3)
                 .toArray().length >0) {  // zamieniamy strumien na tanblice, i jak tablica cos zawiera tzn ze poprawne id

            List<Client> list = Bank.allClients.stream().filter(client1 -> client1.getId() == clientId3).collect(Collectors.toList());
            client3 = list.get(0);

         } else {
            System.out.println("Podano niepoprawne id");
            Thread.sleep(2000);
            continue;
         }

         System.out.println(client3.getListOfClientAccounts());
         break;
      case 4:
         System.out.println("Podaj swoje id klienta");
         int clientId4 = scanner.nextInt();
         scanner.nextLine();
         Client client4; // definiowany tutaj aby byl widoczny nie tylko w if ponizej ale tez poza if w drugim if

         if (Bank.allClients.stream() // sprawdzamy czy poprawne id
                 .filter(client1 -> client1.getId() == clientId4)
                 .toArray().length >0) {  // zamieniamy strumien na tanblice, i jak tablica cos zawiera tzn ze poprawne id

            List<Client> list = Bank.allClients.stream().filter(client1 -> client1.getId() == clientId4).collect(Collectors.toList());
            client4 = list.get(0);

         } else {
            System.out.println("Podano niepoprawne id");
            Thread.sleep(2000);
            continue;
         }

         client4.cashAdd();
         break;
      case 5:
         System.out.println("Podaj swoje id klienta");
         int clientId5 = scanner.nextInt();
         scanner.nextLine();
         Client client5; // definiowany tutaj aby byl widoczny nie tylko w if ponizej ale tez poza if w drugim if

         if (Bank.allClients.stream() // sprawdzamy czy poprawne id
                 .filter(client1 -> client1.getId() == clientId5)
                 .toArray().length >0) {  // zamieniamy strumien na tanblice, i jak tablica cos zawiera tzn ze poprawne id

            List<Client> list = Bank.allClients.stream().filter(client1 -> client1.getId() == clientId5).collect(Collectors.toList());
            client5 = list.get(0);

         } else {
            System.out.println("Podano niepoprawne id");
            Thread.sleep(2000);
            continue;
         }

         client5.withdrawal();
         break;

      case 6:
         System.out.println("Tylko dla pracowników banku. Podaj PIN: ");
         int pin = scanner.nextInt();
         scanner.nextLine();

         if (pin == 8345){
            System.out.println("Co chcesz zrobić? (Podaj cyfrę od 1 do 5)" + "\n"
                  + "1. Wyświetl listę wszystkich klientów" + "\n"
                  + "2. Wyświetl listę wszystkich rachunków" +"\n"
                  + "3. Usuń wszystkich klientów, którzy nie mają rachunku" + "\n"
                  + "4. Usuń rachunek dla klienta, jeśli stan środków wynosi 0.00 zł" + "\n"
                  + "5. Powrót do poprzedniego menu" );

               int choice2 = scanner.nextInt();

               switch (choice2) {
                  case 1:
                     Bank.showAllClients();
                     break;
                  case 2:
                     Bank.showAllAccounts();
                     break;
                  case 3:
                     System.out.println(Bank.deleteClientIfHasNoAccounts());
                     break;
                  case 4:
                     Bank.deleteAccountIfBalanceIsZero();
                     break;
                  case 5:
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

} while(choice != 7);


   }
}









