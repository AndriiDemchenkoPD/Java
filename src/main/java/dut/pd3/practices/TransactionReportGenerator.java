package dut.pd3.practices;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class TransactionReportGenerator {

    private TransactionReportGenerator() {
    }

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

    public static void printPeriodExpensesExtremes(
            LocalDate start,
            LocalDate end,
            Optional<Transaction> maxExpense,
            Optional<Transaction> minExpense) {
        System.out.println("\nАналіз витрат за період з " + start + " по " + end + ":");
        printExpenseExtreme("Найбільша витрата", maxExpense);
        printExpenseExtreme("Найменша витрата", minExpense);
    }

    public static void printVisualExpenseReport(
            String title,
            Map<String, Double> expenseMap,
            double unitValue) {
        System.out.println("\n" + title + " (* = " + (int) unitValue + " грн):");
        expenseMap.forEach((key, sum) -> {
            String bar = "*".repeat(Math.max(0, (int) Math.round(sum / unitValue)));
            System.out.printf("%-18s | %-20s (%.2f грн)%n", key, bar, sum);
        });
    }

    private static void printExpenseExtreme(String label, Optional<Transaction> expense) {
        expense.ifPresentOrElse(
                transaction -> System.out.println(label + ": " + transaction.getDescription()
                        + " (" + transaction.getAmount() + " грн) від " + transaction.getDate()),
                () -> System.out.println(label + ": відсутня")
        );
    }
}
