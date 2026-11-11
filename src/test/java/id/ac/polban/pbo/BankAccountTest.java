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

    @Test
    void withdraw_melebihiSaldo_ditolak() {
        BankAccount acc =
            new BankAccount("001", "Andi", 100_000);

        assertThrows(
            InsufficientBalanceException.class,
            () -> acc.withdraw(200_000)
        );

        assertEquals(100_000, acc.getBalance(), 0.001);
    }

    @Test
    void withdraw_valid_tidakMelempar() {
        BankAccount acc =
            new BankAccount("001", "Andi", 100_000);

        assertDoesNotThrow(() -> acc.withdraw(30_000));
        assertEquals(70_000, acc.getBalance(), 0.001);
    }

    @Test
    void constructor_saldoNegatif_ditolak() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new BankAccount("001", "Andi", -1));

        assertEquals("Saldo awal tidak boleh negatif",
                     ex.getMessage());
    }
}
