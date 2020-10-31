package application;

import java.util.Random;

public class Account {

    public static String createUniqueAccountNumber() {
        String accountNumber = "";
        for (int i = 0; i < 27; i++) {
            Random random = new Random();
            int digit = random.nextInt(10);

            accountNumber = accountNumber + digit; // accountNumber = new StringBuilder(accountNumber).append(digit).toString() - taki zapis moglby byc, ale ze w petli jest to i tak za kazdym obrotem petli StringBuilder się zapisuje, wiec mozna stworzyc zwyklego stringa
        }
        if (!Bank.accountNumberUniqueList.contains(accountNumber)) { // jezeli lista numerow kont nie zawiera nowego numeru, to go dodaj i zwroc (jest unikalny)

            Bank.accountNumberUniqueList.add(accountNumber);
            return accountNumber;

        } else { // jezeli nie wejdzie do if (czyli juz taki numer byl) to zwroc ponowne wykonanie tej metody - ponowne losowanie numeru

            System.out.println("Błędny numer konta");
            return createUniqueAccountNumber();
        }
    }
}


