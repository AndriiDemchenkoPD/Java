package dut.pd3.practices;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class TransactionReportGenerator {

    private TransactionReportGenerator() {}

    public static void printBalanceReport(double totalBalance) {
        System.out.println("Загальний баланс: " + totalBalance);
    }

    public static void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("Кількість транзакцій за " + monthYear + ": " + count);
    }

    public static void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("\n10 найбільших витрат:");
        for (Transaction expense : topExpenses) {
            System.out.println(expense.getDescription() + ": " + expense.getAmount());
        }
    }

    public static void printPeriodExpensesExtremes(LocalDate start, LocalDate end,
                                                   Optional<Transaction> maxExpense,
                                                   Optional<Transaction> minExpense) {
        System.out.println("\nАналіз витрат за період з " + start + " по " + end + ":");
        maxExpense.ifPresentOrElse(
                t -> System.out.println("Найбільша витрата: " + t.getDescription() + " (" + t.getAmount() + " грн) від " + t.getDate()),
                () -> System.out.println("Найбільша витрата: відсутня")
        );
        minExpense.ifPresentOrElse(
                t -> System.out.println("Найменша витрата: " + t.getDescription() + " (" + t.getAmount() + " грн) від " + t.getDate()),
                () -> System.out.println("Найменша витрата: відсутня")
        );
    }

    // Псевдографічний звіт: 1 символ '*' = 1000 грн
    public static void printVisualExpenseReport(String title, Map<String, Double> expenseMap, double unitValue) {
        if (unitValue <= 0 || !Double.isFinite(unitValue)) {
            throw new IllegalArgumentException("Значення unitValue має бути додатним числом");
        }
        System.out.println("\n" + title + " (* = " + (int) unitValue + " грн):");
        expenseMap.forEach((key, sum) -> {
            int starsCount = (int) (sum / unitValue);
            String bar = "*".repeat(Math.max(0, starsCount));
            System.out.printf("%-18s | %-20s (%.2f грн)%n", key, bar, sum);
        });
    }
}
