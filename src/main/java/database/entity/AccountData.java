package database.entity;

import application.AccountType;
import application.Client;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Accounts_data")
public class AccountData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long number;

//    @NotNull - nie tworzymy tej kolumny od razu, ona zostanie stworzona jako wynik relacji i jako klucz obcy
//    private Long client_id;

    @NotNull
    @Column(name = "account_type")
    @Enumerated(value = EnumType.STRING) // aby z enuma wyciagalo stringa
    private  AccountType accountType;

    @Column(name = "account_number") // moze byc null bo przy nie zalozeniu konta (null) brak numeru konta
    private String accountNumber;

    private BigDecimal balance; // moze byc null bo przy nie zalozeniu konta (null) brak numeru konta

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private ClientData client1;

    public AccountData () {

    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

//    public Long getClient_id() {
//        return client_id;
//    }
//
//    public void setClient_id(Long client_id) {
//        this.client_id = client_id;
//    }


    public ClientData getClient1() {
        return client1;
    }

    public void setClient1(ClientData client1) {
        this.client1 = client1;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
