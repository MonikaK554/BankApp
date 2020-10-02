import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Account {

    private String accountType;
    private double balance;
    private String accountNumber ="";
    private List<String> checkingIfAccountNumberIsTrulyUnique = new ArrayList<>();

    public Account(String accountType, double balance) {
        this.accountType = accountType;
        this.balance = balance;
        accountNumber = createUniqueAccountNumber();
    }

    public String createUniqueAccountNumber() {
        for (int i = 0; i < 27; i++) {
            Random random = new Random();
            int digit = random.nextInt(10);

          accountNumber = accountNumber+digit; // accountNumber = new StringBuilder(accountNumber).append(digit).toString() - taki zapis moglby byc, ale ze w petli jest to i tak za kazdym obrotem petli StringBuilder się zapisuje, wiec mozna stworzyc zwyklego stringa

        }

        if (!checkingIfAccountNumberIsTrulyUnique.contains(accountNumber)) { // jezeli lista numerow kont nie zawiera nowego numeru, to go dodaj i zwroc (jest unikalny)
            checkingIfAccountNumberIsTrulyUnique.add(accountNumber);
            return accountNumber;
        }  else {    // jak ponownie wykonac metodę ? continue nie dziala
            System.out.println("Błędny numer konta");
        }
        return null;
    }

    public static Account createAccount (String type, double balance){ // metoda do tworzenia rachunku zamiast slowka new
       Account account = new Account(type, balance);
       return account;
  }


    public String getAccountType() {
        return accountType;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountType='" + accountType + '\'' +
                ", balance=" + balance +
                ", accountNumber=" + accountNumber +
                '}';
    }
}
