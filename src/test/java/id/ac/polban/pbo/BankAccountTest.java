package id.ac.polban.pbo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void deposit_menambahSaldo() {
        // Arrange
        BankAccount acc =
            new BankAccount("001", "Andi", 100_000);

        // Act
        acc.deposit(50_000);

        // Assert
        assertEquals(150_000, acc.getBalance(), 0.001);
    }

    @Test
    void constructor_menyimpanDataDenganBenar() {
        BankAccount acc =
            new BankAccount("001", "Andi", 100_000);

        assertAll("data rekening",
            () -> assertEquals("001", acc.getAccountNumber()),
            () -> assertEquals("Andi", acc.getOwner()),
            () -> assertEquals(100_000, acc.getBalance(), 0.001)
        );
    }
}
