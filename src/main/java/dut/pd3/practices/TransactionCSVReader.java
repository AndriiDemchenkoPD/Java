package dut.pd3.practices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class TransactionCSVReader {

    private TransactionCSVReader() {
    }

    public static List<Transaction> readTransactions(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("Шлях до CSV не може бути порожнім");
        }

        Path path = null;
        try {
            path = Path.of(filePath);
        } catch (InvalidPathException ignored) {
            // Значення може бути URL, а не шляхом до локального файлу.
        }

        try (BufferedReader reader = path != null && Files.isRegularFile(path)
                ? Files.newBufferedReader(path, StandardCharsets.UTF_8)
                : new BufferedReader(new InputStreamReader(
                        URI.create(filePath).toURL().openStream(), StandardCharsets.UTF_8))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    lines.add(line);
                }
            }
            return parseLines(lines);
        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalStateException("Не вдалося зчитати CSV: " + filePath, e);
        }
    }

    public static List<Transaction> parseLines(List<String> lines) {
        List<Transaction> transactions = new ArrayList<>();
        for (String line : lines) {
            List<String> values = parseCsvRow(line);
            if (values.size() < 3) {
                continue;
            }

            try {
                double amount = Double.parseDouble(values.get(1).trim());
                transactions.add(new Transaction(
                        values.get(0).trim(), amount, values.get(2).trim()));
            } catch (NumberFormatException ignored) {
                // Некоректне число, зокрема заголовок CSV, пропускаємо.
            }
        }
        return transactions;
    }

    private static List<String> parseCsvRow(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder value = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char current = line.charAt(i);
            if (current == '"') {
                if (insideQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    value.append('"');
                    i++;
                } else {
                    insideQuotes = !insideQuotes;
                }
            } else if (current == ',' && !insideQuotes) {
                values.add(value.toString());
                value.setLength(0);
            } else {
                value.append(current);
            }
        }

        if (insideQuotes) {
            return List.of();
        }
        values.add(value.toString());
        return values;
    }
}
