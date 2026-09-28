package dut.pd3.practices;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        String filePath = "https://informer.com.ua/dut/java/pr2.csv";

        // 1. Зчитування даних
        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);

        // 2. Розрахунок балансу
        double totalBalance = TransactionAnalyzer.calculateTotalBalance(transactions);
        TransactionReportGenerator.printBalanceReport(totalBalance);

        // 3. Підрахунок транзакцій за місяць
        String monthYear = "01-2024";
        int count = TransactionAnalyzer.countTransactionsByMonth(transactions, monthYear);
        TransactionReportGenerator.printTransactionsCountByMonth(monthYear, count);

        // 4. Топ-10 витрат
        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);
        TransactionReportGenerator.printTopExpensesReport(topExpenses);

        // 5. Функціональні розширення самостійної роботи
        LocalDate startDate = LocalDate.of(2023, 12, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        Optional<Transaction> maxExp = TransactionAnalyzer.findMaxExpenseInPeriod(transactions, startDate, endDate);
        Optional<Transaction> minExp = TransactionAnalyzer.findMinExpenseInPeriod(transactions, startDate, endDate);
        TransactionReportGenerator.printPeriodExpensesExtremes(startDate, endDate, maxExp, minExp);

        // Візуалізація витрат по категоріях та місяцях
        Map<String, Double> expensesByCategory = TransactionAnalyzer.calculateExpensesByCategory(transactions);
        TransactionReportGenerator.printVisualExpenseReport("Витрати за категоріями", expensesByCategory, 1000.0);

        Map<String, Double> expensesByMonth = TransactionAnalyzer.calculateExpensesByMonth(transactions);
        TransactionReportGenerator.printVisualExpenseReport("Витрати за місяцями", expensesByMonth, 1000.0);
    }
}
