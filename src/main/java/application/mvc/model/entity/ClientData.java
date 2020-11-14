package application.mvc.model.entity;
import com.sun.istack.NotNull;
import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "Clients_Data") // tabela o nazwie dane klientow w workkbench
public class ClientData { // klasa o nazwie dane klienta pojedynczego



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private Integer pin; // pin nadany w kodzie w klasie Client

    @NotNull   // mozna uzyc @Column(nullable = false) ale  @NotNull bo "This way, we make sure the validation takes place before Hibernate sends any insert or update SQL queries to the database."
    private String name;
    @NotNull
    private String surname;
    @NotNull
    @Column(unique = true)
    private Long pesel;

    @OneToMany(fetch = FetchType.EAGER, mappedBy ="clientData") // jeden klient ma wiele kont wiec relacja z jego strony jeden do wiele one to many
   private List<AccountData> accountList;

public ClientData () {  // konstruktor bezparametrowy. Dobrą praktyką jest dodawanie go w encji

}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getPesel() {
        return pesel;
    }

    public void setPesel(Long pesel) {
        this.pesel = pesel;
    }

    public List<AccountData> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<AccountData> accountList) {
        this.accountList = accountList;
    }


    @Override
    public String toString() {
        return "ClientData{" +
                "id=" + id +
                ", pin=" + pin +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", pesel=" + pesel +
               // ", accountList=" + accountList +
                '}';
    }
}
