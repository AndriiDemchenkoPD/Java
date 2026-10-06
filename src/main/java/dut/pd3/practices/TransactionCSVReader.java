package dut.pd3.practices;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class TransactionCSVReader {

    private TransactionCSVReader() {
    }

    public static List<Transaction> readTransactions(String fileName) {
        List<String> lines = new ArrayList<>();

        InputStream inputStream = TransactionCSVReader.class.getClassLoader().getResourceAsStream(fileName);
        try {
            if (inputStream == null) {
                inputStream = new FileInputStream(fileName);
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        lines.add(line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Помилка читання файлу: " + e.getMessage());
        }

        return parseLines(lines);
    }

    public static List<Transaction> parseLines(List<String> lines) {
        List<Transaction> transactions = new ArrayList<>();
        for (String line : lines) {
            String[] values = line.split(",");
            if (values.length >= 3) {
                try {
                    double amount = Double.parseDouble(values[1].trim());
                    transactions.add(new Transaction(
                            values[0].trim(), amount, values[2].trim()));
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return transactions;
    }
}
