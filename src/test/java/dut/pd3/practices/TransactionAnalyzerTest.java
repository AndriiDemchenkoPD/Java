package dut.pd3.practices;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class TransactionAnalyzerTest {

    private List<Transaction> testTransactions;

    @BeforeEach
    void setUp() {
        testTransactions = Arrays.asList(
                new Transaction("01-02-2023", 100.0, "Дохід"),
                new Transaction("15-02-2023", -50.0, "Витрата 1"),
                new Transaction("20-02-2023", -2500.0, "Витрата 2"),
                new Transaction("05-03-2023", 150.0, "Дохід 2"),
                new Transaction("10-03-2023", -1200.0, "Витрата 3")
        );
    }

    @Test
    void testCalculateTotalBalance() {
        double result = TransactionAnalyzer.calculateTotalBalance(testTransactions);
        Assertions.assertEquals(-3500.0, result);
    }

    @Test
    void testCountTransactionsByMonth() {
        int countFeb = TransactionAnalyzer.countTransactionsByMonth(testTransactions, "02-2023");
        Assertions.assertEquals(3, countFeb);
    }

    @Test
    void testFindTopExpenses() {
        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(testTransactions);
        Assertions.assertEquals(3, topExpenses.size());
        Assertions.assertEquals(-2500.0, topExpenses.get(0).getAmount());
    }

    @Test
    void testParseLinesFromCsv() {
        List<String> rawCsv = Arrays.asList(
                "Дата,Сума,Категорія",
                "01-01-2024,500,Бонус",
                "02-01-2024,-150,Кава"
        );
        List<Transaction> parsed = TransactionCSVReader.parseLines(rawCsv);
        Assertions.assertEquals(2, parsed.size());
        Assertions.assertEquals("Бонус", parsed.get(0).getDescription());
        Assertions.assertEquals(-150.0, parsed.get(1).getAmount());
    }

    @Test
    void testFindMaxAndMinExpenseInPeriod() {
        LocalDate start = LocalDate.of(2023, 2, 1);
        LocalDate end = LocalDate.of(2023, 2, 28);

        Optional<Transaction> maxExpense = TransactionAnalyzer.findMaxExpenseInPeriod(
                testTransactions, start, end);
        Optional<Transaction> minExpense = TransactionAnalyzer.findMinExpenseInPeriod(
                testTransactions, start, end);

        Assertions.assertTrue(maxExpense.isPresent());
        Assertions.assertEquals(-2500.0, maxExpense.get().getAmount());

        Assertions.assertTrue(minExpense.isPresent());
        Assertions.assertEquals(-50.0, minExpense.get().getAmount());
    }
}
