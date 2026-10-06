package dut.pd3.practices;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public abstract class TransactionAnalyzer {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter MONTH_YEAR_FORMATTER =
            DateTimeFormatter.ofPattern("MM-yyyy");

    private TransactionAnalyzer() {
    }

    public static double calculateTotalBalance(List<Transaction> transactions) {
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public static int countTransactionsByMonth(List<Transaction> transactions, String monthYear) {
        return (int) transactions.stream()
                .filter(t -> {
                    LocalDate date = LocalDate.parse(t.getDate(), DATE_FORMATTER);
                    return date.format(MONTH_YEAR_FORMATTER).equals(monthYear);
                })
                .count();
    }

    public static List<Transaction> findTopExpenses(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparing(Transaction::getAmount))
                .limit(10)
                .collect(Collectors.toList());
    }

    public static Optional<Transaction> findMaxExpenseInPeriod(
            List<Transaction> transactions,
            LocalDate startDate,
            LocalDate endDate) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> {
                    LocalDate date = LocalDate.parse(t.getDate(), DATE_FORMATTER);
                    return !date.isBefore(startDate) && !date.isAfter(endDate);
                })
                .min(Comparator.comparing(Transaction::getAmount));
    }

    public static Optional<Transaction> findMinExpenseInPeriod(
            List<Transaction> transactions,
            LocalDate startDate,
            LocalDate endDate) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> {
                    LocalDate date = LocalDate.parse(t.getDate(), DATE_FORMATTER);
                    return !date.isBefore(startDate) && !date.isAfter(endDate);
                })
                .max(Comparator.comparing(Transaction::getAmount));
    }

    public static Map<String, Double> calculateExpensesByCategory(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        Transaction::getDescription,
                        Collectors.summingDouble(t -> Math.abs(t.getAmount()))
                ));
    }

    public static Map<String, Double> calculateExpensesByMonth(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        t -> LocalDate.parse(t.getDate(), DATE_FORMATTER).format(MONTH_YEAR_FORMATTER),
                        TreeMap::new,
                        Collectors.summingDouble(t -> Math.abs(t.getAmount()))
                ));
    }
}
