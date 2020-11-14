package application.additional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void createUniqueAccountNumber() {

        String uniqueNumber1 = Account.createUniqueAccountNumber();
        String uniqueNumber2 = Account.createUniqueAccountNumber();

        assertNotEquals(uniqueNumber1,uniqueNumber2);
    }
}