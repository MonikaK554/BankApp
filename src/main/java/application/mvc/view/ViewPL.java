package application.mvc.view;

import application.additional.Account;
import application.additional.AccountType;
import application.additional.Bank;
import application.additional.DataValidator;
import application.mvc.model.entity.AccountData;
import application.mvc.model.entity.ClientData;

import java.math.BigDecimal;
import java.util.Scanner;

public class ViewPL implements View {

    private Scanner scanner;
    ClientData clientData;


    public ViewPL() {
        scanner = new Scanner(System.in);
    }

    private void log(String text) { // zamiast pisania ciagle sout
        System.out.println(text);
    }


    @Override
    public int showMenuAndGetSelectedOption() {

        log("Witaj w " + Bank.name + " . Co chcesz zrobic? (Podaj cyfrę od 1 do 7)");
        log("1. Stworzenie nowego klienta");
        log("2. Dodanie rachunku dla klienta");
        log("3. Sprawdzenie salda posiadanych kont");
        log("4. Wpłata na rachunek");
        log("5. Wypłata");
        log("6. Funkcje dodatkowe dla pracowników banku");
        log("7. Wyjście");

        int selected = scanner.nextInt();
        scanner.nextLine();

        return selected;

    }

    @Override
    public ClientData askForClientPersonalDataAndCreateClientData() {

        System.out.println("Podaj imię: ");
        String name = scanner.nextLine();
        System.out.println("Podaj nazwisko: ");
        String surname = scanner.nextLine();
        System.out.println("Podaj pesel: ");
        long pesel = scanner.nextLong();
        if (DataValidator.peselValidator(pesel) && DataValidator.nameAndSurnameValidator(name, surname)) { // rowne true

            scanner.nextLine(); //aby pozbyc się pustego znaku (spacji po wpisywaniu naprzemian String, int/long)

            clientData = new ClientData();
            clientData.setName(name);
            clientData.setSurname(surname);
            clientData.setPesel(pesel);
            clientData.setPin(Bank.createPin());
        }

        return clientData;
    }

    @Override
    public void showClientInformation(ClientData clientData) {

        log("Klient został utworzony"); // rekord w bazie danych
        log("Numer id klienta to: " + clientData.getId());
        log("Numer PIN to: " + clientData.getPin());


    }

    @Override
    public int askForClientPin() {
        log("Podaj PIN");
        int pin = scanner.nextInt();
        scanner.nextLine();
        return pin;
    }

    @Override
    public AccountData askForAccountDataAndCreateAccount(ClientData clientData1) throws InterruptedException {
        log("Podaj typ konta, jakie chcesz utworzyć [STUDENT / STANDARD /PRO].");
        String accountType = scanner.nextLine();

        if (accountType.equalsIgnoreCase(AccountType.STUDENT.name()) ||
                accountType.equalsIgnoreCase(AccountType.PRO.name()) ||
                accountType.equalsIgnoreCase(AccountType.STANDARD.name())) {

            log("Podaj kwotę salda początkowego:");
            BigDecimal balance1 = scanner.nextBigDecimal();
            scanner.nextLine();

            AccountData accountData = new AccountData(); // tworzymy nowy wiersz tabeli accounts_data i ustawiamy parametry

            accountData.setAccountType(AccountType.valueOf(accountType.toUpperCase()));
            accountData.setBalance(balance1);
            accountData.setAccountNumber(Account.createUniqueAccountNumber());
            accountData.setClientData(clientData1); // stad wezmie id_clienta

            return accountData;
        } else {
            System.out.println("Podano niepoprawny typ konta.");
            Thread.sleep(2000);
            return null;
        }
    }

    @Override
    public int askForAccountIdWhenCashAdd() {
        log("Podaj numer porządkowy swojego konta, na które chcesz wpłacić pieniądze.");
        return scanner.nextInt();
    }

    @Override
    public int askForAccountIdWhenCashWithdrawal() {
        log("Podaj numer porządkowy swojego konta, z którego chcesz wypłacić pieniądze.");
        return scanner.nextInt();
    }

    @Override
    public BigDecimal askForDeposit() {
        System.out.println("Podaj kwotę do wpłaty:");
        return scanner.nextBigDecimal();
    }

    @Override
    public BigDecimal askForWithDrawal() {
        System.out.println("Podaj kwotę do wypłaty:");
        return scanner.nextBigDecimal();
    }

    @Override
    public void showInfoAboutIncorrectPinNumber() throws InterruptedException {
        System.out.println("Podano niepoprawny PIN");
        Thread.sleep(200);
    }

    @Override
    public void showInfoAboutIncorrectAccountId() throws InterruptedException {
        System.out.println("Podano niepoprawne id konta.");
        Thread.sleep(2000);
    }

    @Override
    public void showInfoAboutIncorrectClientId() throws InterruptedException {
        System.out.println("Podano niepoprawne id klienta.");
        Thread.sleep(2000);
    }

    @Override
    public void showAccountBalance(AccountData accountData) {
        System.out.println("Nowe saldo wynosi " + accountData.getBalance());
    }

    @Override
    public void showNotEnoughMoneyToWithDrawal() {
        System.out.println("Nie masz wystarczających środków na koncie.");
    }

    @Override
    public int askForEmpleyeesCodeToGetAccess() {
        System.out.println("Tylko dla pracowników banku. Podaj PIN: ");
        return scanner.nextInt();
    }

    @Override
    public boolean verifyAccessCode(int code) throws InterruptedException {
        if (code == 8345) {
            return true;
        }else {

            log("Wpisano niepoprawny kod dostępu");
            Thread.sleep(2000);
            return false;

        }
    }

    @Override
    public int showEmployeesMenuAndGetSelectedOption() {

        log("Co chcesz zrobić? (Podaj cyfrę od 1 do 5)");
        log("1. Wyświetl listę wszystkich klientów");
        log("2. Wyświetl listę wszystkich rachunków");
        log("3. Usuń klienta");   //wraz ze wszystkimi jego rachunkami oczywiście
        log("4. Usuń rachunek dla klienta");
        log("5. Zmiana danych osobowych");
        log("6. Powrót do poprzedniego menu");

        return scanner.nextInt();
    }

    @Override
    public int askForClientId() {
        log("Podaj id klienta, dla którego ma zostać wykonana operacja.");
        return scanner.nextInt();
    }

    @Override
    public void showInfoAboutDeletingClientWithAllAccounts() {
        System.out.println("Operacja usunięcia klienta się powiodła.");
    }

    @Override
    public int askForAccountIdToDelete() {
        log("Podaj id konta, które ma zostać usunięte.");
        return scanner.nextInt();

    }

    @Override
    public void showInfoAboutDeletingSingleAccount() {
        log("Konto zostało usunięte.");
    }

    @Override
    public String askForNewSurname() {
        System.out.println("Podaj nowe nazwisko klienta");
        return scanner.nextLine();
    }

}
