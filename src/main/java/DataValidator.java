import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidator {

    public static boolean peselValidator(long peselGiven) { //sprawdza numer PESEL pod względem nieprawidłowych danych (niewłaściwe numery miesięcy, dni

        // TODO trzeba sprawdzic trzy rzeczy: czy dni, miesiace i rok sie zgadzaja, czy suma kontrolna sie zgadza i jak jest 29 luty to czy jest to rok przestepny
        boolean value = false;

        String peselGivenToString = peselGiven + ""; //zamieniamy long na string aby potem string na intigera przekonwertowac bez zmiany wartosci na kod ASCI(JAK CHAR)

        int year = Integer.parseInt(peselGivenToString.substring(0, 2));
        int month = Integer.parseInt(peselGivenToString.substring(2, 4));
        int day = Integer.parseInt(peselGivenToString.substring(4, 6));

        System.out.println( "Rok urodzenia " + year);
        System.out.println("Miesiąc " + month);
        System.out.println("Dzień "+day);

        // zakladamy ze nie ma ludzi urodzonych przed 1910 rokiem // ten if sprawdza poprawnosc rok/miesiac/dzien
        if (peselGivenToString.length() == 11 && year > 10 && month > 0 && month < 13) {

            if ((day > 0 && day < 32) && // dla miesiecy z liczba dni do 31
                    (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 ||
                            month == 12)) {
                System.out.println("Przypadek 1");
                value = true;

            } else if ((day > 0 && day < 31) && // dla miesiecy z liczba dni do 30
                    (month == 4 || month == 6 || month == 9 ||
                            month == 11)) {
                System.out.println("Przypadek 2");
                value = true;

            } else if (month == 2 && day > 0 && day < 30) {// dla lutego 29 dni. Sprawdzenie roku przestepnego w przypadku lutego
                System.out.println("Przypadek 3");

                if (day < 29) {

                    System.out.println("Przypadek 3a");
                    value = true;
                } else if (day == 29 && ((year + 1900) % 4 == 0 && (year + 1900) % 100 != 0 || (year + 1900) % 400 == 0)) {
                    System.out.println("Przypadek 3b");
                    value = true;
                } else {
                    value = false;
                }


            } else { // spelnia glowny warunek ale dni sie nie zgadzaja
                System.out.println("Przypadek 4");
                value = false;
            }


        } else {  // nie spelnia nawet glownego warunku
            System.out.println("Przypadek 5 ");
            value = false ;
        }


        // liczenie sumy kontrolnej

        int sum = 1 * Integer.parseInt(peselGivenToString.substring(0, 1)) + // nie moze byc peselGivenToString.indexOf(4) - wyciagane ze stringa tylko musi byc int mnpozony przez int aby byl poprawny wynik
                3 * Integer.parseInt(peselGivenToString.substring(1, 2))  +
                7 * Integer.parseInt(peselGivenToString.substring(2, 3))   + // tylko tak uzyskujemy z cyfry zapisanej w stringu realna jej wartosc a nie kod ASCI
                9 * Integer.parseInt(peselGivenToString.substring(3, 4))  +
                1 * Integer.parseInt(peselGivenToString.substring(4, 5))  +
                3 * Integer.parseInt(peselGivenToString.substring(5, 6)) +
                7 * Integer.parseInt(peselGivenToString.substring(6, 7))  +
                9 * Integer.parseInt(peselGivenToString.substring(7, 8)) +
                1 * Integer.parseInt(peselGivenToString.substring(8, 9))  +
                3 * Integer.parseInt(peselGivenToString.substring(9, 10)) ;
        sum %= 10;
        sum = 10 - sum;
        sum %= 10;

        System.out.println("Suma kontrolna :" +sum);


        if (sum == Integer.parseInt(peselGivenToString.substring(10))) {
            value = true;
            System.out.println("Walidacja nr PESEL przebiegła pomyślnie.");
        }
        else {
            value = false;
        }

        return value;
    }


    public static boolean nameAndSurnameValidator(String nameGiven, String surnameGiven) { // imie i nazwisko nie moze byc null oraz moze zawierac tylko litery

        boolean value1 = false;

        if (nameGiven != null && surnameGiven != null) {

            Pattern nameAndSurnamePattern = Pattern.compile("[A-Za-z ]*");

            Matcher nameMatcher = nameAndSurnamePattern.matcher(nameGiven);
            Matcher surnameMatcher = nameAndSurnamePattern.matcher(surnameGiven);


            if (nameMatcher.matches() && surnameMatcher.matches()) {
                System.out.println("Walidacja imienia i nazwiska przebiegła pomyślnie.");
                value1 = true;

            } else {  // jesli nie przeszlo regexu
                value1 = false;
            }

        } else { // jesli ktores jest null
             value1 = false;

        }
        return value1;
    }





}
