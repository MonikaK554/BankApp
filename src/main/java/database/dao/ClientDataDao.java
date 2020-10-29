package database.dao;

import application.Client;
import database.entity.ClientData;

import java.util.List;

public interface ClientDataDao {

    void saveClient(Client client); // przekazujemy klienta, zamieniamy go na wiersz w tabeli i zapisujemy do tabeli
    ClientData findById (Integer id); //zwracamy tu wiersz z tabeli, nie trzeba zwracac obiektu JAVA choc mozna
    List<ClientData> findAll(); //zwracanie listy rekordow z bazy danych a nie z metody Bank.showAllClients()
    void deleteById (Integer id); // trzeba usunac z dwoch miejsc, z tabeli bazy danych oraz z listy wszystkich klientow z kodu

}
