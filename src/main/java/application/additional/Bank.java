package application.additional;

//import application.mvc.model.dao.AccountDataDao;
//import application.mvc.model.dao.ClientDataDao;
//import database.daoImpl.AccountDataImpl;
//import database.daoImpl.ClientDataImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Bank {

    public static final String name = "MBank";

    public static List<String> accountNumberUniqueList = new ArrayList<>(); // do klasy ACCOUNT wykorzystywane checkingIfAccountNumberIsTrulyUnique
    public static List<Integer> clientUniquePinList = new ArrayList<>(); // checkingIfPinIsTrulyUnique

    static Scanner scanner = new Scanner(System.in);

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



}