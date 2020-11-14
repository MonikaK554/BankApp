package application.mvc.model.dao;

import application.mvc.model.entity.ClientData;

import java.util.List;

public interface ClientDataDao {

    ClientData save(ClientData clientData); // najpierw trzeba stworzyc obiekt wiersz, ustawic mu parametry aa metoda savve odpowiada za zapisanie gotowego obiektu tylko
    ClientData findByPin (Integer pin);
    ClientData findById (Integer id);
    List<ClientData> findAll(); //zwracanie listy rekordow z bazy danych a nie z metody Bank.showAllClients()
    void updateClient (ClientData clientData, String newSurname); // juz bez tworzenia nowego wiersza w tabeli
    void deleteById (Integer id); // usuwamy klienta wraz z jego wszytskimi kontami. Trzeba dodac ON DELETE CASCADE do constraint




}
