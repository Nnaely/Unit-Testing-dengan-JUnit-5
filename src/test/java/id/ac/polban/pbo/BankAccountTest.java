package id.ac.polban.pbo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

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

    // ===== Praktikum III =====

    // -0.01 = boundary: nilai tepat di bawah batas valid (0)
    @ParameterizedTest(name = "setoran {0} ditolak")
    @ValueSource(doubles = {0, -0.01, -1, -50_000})
    void deposit_tidakValid(double amount) {
        assertThrows(
            IllegalArgumentException.class,
            () -> sender.deposit(amount)
        );
    }

    @ParameterizedTest(name = "saldo {0} - tarik {1} = {2}")
    @CsvSource({
        "100000, 10000,   90000",
        "100000, 100000,  0",
        "50000,  25000.5, 24999.5"
    })
    void withdraw_berbagaiNilai(
            double awal, double tarik, double sisa)
            throws InsufficientBalanceException {
        BankAccount acc =
            new BankAccount("009", "Dewi", awal);

        acc.withdraw(tarik);

        assertEquals(sisa, acc.getBalance(), 0.001);
    }
}
