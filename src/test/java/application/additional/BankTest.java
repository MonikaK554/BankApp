package application.additional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    @Test
    void createPin() {

        Integer pin1 = Bank.createPin();
        Integer pin2 = Bank.createPin();

        assertNotEquals(pin1,pin2);



    }
}