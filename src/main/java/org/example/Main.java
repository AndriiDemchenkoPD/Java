package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            run(scanner);
        } finally {
            System.out.println("Обробку запиту завершено.");
        }
    }

    static void run(Scanner scanner) {
        System.out.println("=== Консольний калькулятор ===");
        System.out.println("Доступні операції: +, -, *, /, sqrt");

        try {
            System.out.print("Оберіть операцію: ");
            if (!scanner.hasNext()) {
                throw new InvalidInputException("Операцію не введено.");
            }
            String operation = scanner.next();
            Calculator calculator = new Calculator();

            if (operation.equalsIgnoreCase("sqrt")) {
                double number = readNumber(scanner, "Введіть число: ");
                double result = calculator.sqrt(number);
                System.out.printf("Результат: sqrt(%.2f) = %.4f%n", number, result);
                return;
            }

            if (!operation.equals("+") && !operation.equals("-")
                    && !operation.equals("*") && !operation.equals("/")) {
                throw new InvalidInputException("Невідома операція: " + operation);
            }

            double a = readNumber(scanner, "Введіть перше число: ");
            double b = readNumber(scanner, "Введіть друге число: ");
            double result = switch (operation) {
                case "+" -> calculator.add(a, b);
                case "-" -> calculator.subtract(a, b);
                case "*" -> calculator.multiply(a, b);
                case "/" -> calculator.divide(a, b);
                default -> throw new IllegalStateException("Непідтримувана операція");
            };
            System.out.printf("Результат: %.2f %s %.2f = %.4f%n", a, operation, b, result);
        } catch (InvalidInputException | ArithmeticException e) {
            System.err.println("Помилка: " + e.getMessage());
        }
    }

    static double readNumber(Scanner scanner, String prompt) throws InvalidInputException {
        System.out.print(prompt);
        if (!scanner.hasNext()) {
            throw new InvalidInputException("Число не введено.");
        }

        String input = scanner.next();
        try {
            double value = Double.parseDouble(input);
            if (!Double.isFinite(value)) {
                throw new NumberFormatException();
            }
            return value;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Некоректне число: " + input);
        }
    }
}
