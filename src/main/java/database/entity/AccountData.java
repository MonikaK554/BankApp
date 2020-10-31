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

    @NotNull
    @Column(name = "account_type")
    @Enumerated(value = EnumType.STRING) // aby z enuma wyciagalo stringa
    private  AccountType accountType;

    @Column(name = "account_number") // moze byc null bo przy nie zalozeniu konta (null) brak numeru konta
    private String accountNumber;

    private BigDecimal balance; // moze byc null bo przy nie zalozeniu konta (null) brak numeru konta


    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id") // taka kolumna zostanie stworzona w tabeli accounts_data i bedą tu dane z klicza podstawowego encji ClientData - bo takie pole pod spodem
    private ClientData clientData;

    public AccountData () {

    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public ClientData getClientData() {
        return clientData;
    }

    public void setClientData(ClientData clientData) {
        this.clientData = clientData;
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

    @Override
    public String toString() {
        return "AccountData{" +
                "number=" + number +
                ", accountType=" + accountType +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", clientData=" + clientData +
                '}';
    }
}
