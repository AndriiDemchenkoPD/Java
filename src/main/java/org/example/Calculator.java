
package org.example;

public class Calculator {

    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) throws ArithmeticException {
        if (Math.abs(b) < 1e-9) {
            throw new ArithmeticException("Помилка: Ділення на нуль неможливе.");
        }
        return a / b;
    }

    public double sqrt(double a) throws InvalidInputException {
        if (a < 0) {
            throw new InvalidInputException("Помилка вхідних даних: Число під коренем не може бути від'ємним (" + a + ").");
        }
        return Math.sqrt(a);
    }
}
