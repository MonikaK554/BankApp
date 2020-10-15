import java.util.Random;

public class Account {

    private AccountType accountType;
    private double balance;
    private String accountNumber = "";


    public Account(AccountType accountType, double balance) {
        this.accountType = accountType;
        this.balance = balance;
        accountNumber = createUniqueAccountNumber();
    }


    public String createUniqueAccountNumber() {
        for (int i = 0; i < 27; i++) {
            Random random = new Random();
            int digit = random.nextInt(10);

            accountNumber = accountNumber + digit; // accountNumber = new StringBuilder(accountNumber).append(digit).toString() - taki zapis moglby byc, ale ze w petli jest to i tak za kazdym obrotem petli StringBuilder się zapisuje, wiec mozna stworzyc zwyklego stringa

        }

        if (!Bank.accountNumberUniqueList.contains(accountNumber)) { // jezeli lista numerow kont nie zawiera nowego numeru, to go dodaj i zwroc (jest unikalny)
            Bank.accountNumberUniqueList.add(accountNumber);
            return accountNumber;
        } else {    // jezeli nie wejdzie do if (czyli juz taki numer byl) to zwroc ponowne wykonanie tej metody - ponowne losowanie numeru
            System.out.println("Błędny numer konta");
            return this.createUniqueAccountNumber();
        }

    }

    public static Account createAccount(AccountType type, double balance) { // metoda do tworzenia rachunku zamiast slowka new
        return new Account(type, balance);
    }


    public AccountType getAccountType() {
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
