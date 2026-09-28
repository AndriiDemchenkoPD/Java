package dut.pd3.practices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionAnalyzerTest {

    private List<Transaction> testTransactions;

    @BeforeEach
    void setUp() {
        testTransactions = List.of(
                new Transaction("01-02-2023", 100.0, "Дохід"),
                new Transaction("15-02-2023", -50.0, "Витрата 1"),
                new Transaction("20-02-2023", -2500.0, "Витрата 2"),
                new Transaction("05-03-2023", 150.0, "Дохід 2"),
                new Transaction("10-03-2023", -1200.0, "Витрата 3")
        );
    }

    @Test
    void calculatesTotalBalance() {
        assertEquals(-3500.0, TransactionAnalyzer.calculateTotalBalance(testTransactions));
    }

    @Test
    void countsTransactionsByMonth() {
        assertEquals(3, TransactionAnalyzer.countTransactionsByMonth(testTransactions, "02-2023"));
        assertEquals(2, TransactionAnalyzer.countTransactionsByMonth(testTransactions, "03-2023"));
    }

    @Test
    void returnsTenLargestExpensesInDescendingOrder() {
        List<Transaction> transactions = new ArrayList<>();
        for (int amount = 100; amount <= 1200; amount += 100) {
            transactions.add(new Transaction("01-01-2024", -amount, "Витрата " + amount));
        }
        transactions.add(new Transaction("02-01-2024", 5000, "Дохід"));

        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);

        assertEquals(10, topExpenses.size());
        assertEquals(-1200.0, topExpenses.get(0).getAmount());
        assertEquals(-300.0, topExpenses.get(9).getAmount());
        assertTrue(topExpenses.stream().allMatch(transaction -> transaction.getAmount() < 0));
    }

    @Test
    void readsTransactionsFromCsvFile() throws URISyntaxException {
        var csvResource = getClass().getResource("/transactions-test.csv");
        assertNotNull(csvResource, "CSV test file must be available on the test classpath");
        Path csvPath = Path.of(csvResource.toURI());

        List<Transaction> transactions = TransactionCSVReader.readTransactions(csvPath.toString());

        assertEquals(2, transactions.size());
        assertEquals("Покупки, продукти", transactions.get(0).getDescription());
        assertEquals(-150.0, transactions.get(1).getAmount());
    }

    @Test
    void findsLargestAndSmallestExpensesWithinInclusivePeriod() {
        LocalDate start = LocalDate.of(2023, 2, 1);
        LocalDate end = LocalDate.of(2023, 2, 28);

        Optional<Transaction> maxExpense = TransactionAnalyzer.findMaxExpenseInPeriod(testTransactions, start, end);
        Optional<Transaction> minExpense = TransactionAnalyzer.findMinExpenseInPeriod(testTransactions, start, end);

        assertTrue(maxExpense.isPresent());
        assertEquals(-2500.0, maxExpense.get().getAmount());
        assertTrue(minExpense.isPresent());
        assertEquals(-50.0, minExpense.get().getAmount());
    }
}
