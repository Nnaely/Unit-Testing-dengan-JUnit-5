package id.ac.polban.pbo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
    private BankAccount sender;
    private BankAccount receiver;

    @BeforeEach
    void setUp() {
        sender = new BankAccount("001", "Andi", 100_000);
        receiver = new BankAccount("002", "Budi", 0);
    }

    // ===== Praktikum I =====

    @Test
    void deposit_menambahSaldo() {
        // Arrange -> sender dari setUp()

        // Act
        sender.deposit(50_000);

        // Assert
        assertEquals(150_000, sender.getBalance(), 0.001);
    }

    @Test
    void constructor_menyimpanDataDenganBenar() {
        assertAll("data rekening",
            () -> assertEquals("001", sender.getAccountNumber()),
            () -> assertEquals("Andi", sender.getOwner()),
            () -> assertEquals(100_000, sender.getBalance(), 0.001)
        );
    }

    // ===== Praktikum II =====

    @Test
    void withdraw_melebihiSaldo_ditolak() {
        assertThrows(
            InsufficientBalanceException.class,
            () -> sender.withdraw(200_000)
        );

        assertEquals(100_000, sender.getBalance(), 0.001);
    }

    @Test
    void withdraw_valid_tidakMelempar() {
        assertDoesNotThrow(() -> sender.withdraw(30_000));
        assertEquals(70_000, sender.getBalance(), 0.001);
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
