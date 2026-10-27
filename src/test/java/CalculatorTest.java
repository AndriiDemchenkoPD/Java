package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("Базові операції (+, -, *)")
    void testBasicOperations() {
        assertEquals(15.0, calculator.add(10.0, 5.0), 1e-9);
        assertEquals(5.0, calculator.subtract(10.0, 5.0), 1e-9);
        assertEquals(50.0, calculator.multiply(10.0, 5.0), 1e-9);
    }

    @Test
    @DisplayName("Коректне ділення")
    void testValidDivision() {
        assertEquals(2.0, calculator.divide(10.0, 5.0), 1e-9);
    }

    @Test
    @DisplayName("Тест: Ділення на нуль повинно кидати ArithmeticException")
    void testDivisionByZeroThrowsException() {
        ArithmeticException exception = assertThrows(
                ArithmeticException.class,
                () -> calculator.divide(10.0, 0.0)
        );
        assertTrue(exception.getMessage().contains("Ділення на нуль"));
    }

    @Test
    @DisplayName("Тест: Від'ємне число під коренем повинно кидати InvalidInputException")
    void testSqrtNegativeNumberThrowsException() {
        InvalidInputException exception = assertThrows(
                InvalidInputException.class,
                () -> calculator.sqrt(-4.0)
        );
        assertTrue(exception.getMessage().contains("не може бути від'ємним"));
    }

    @Test
    @DisplayName("Тест: Обчислення кореня для допустимого числа")
    void testSqrtValidNumber() throws InvalidInputException {
        assertEquals(3.0, calculator.sqrt(9.0), 1e-9);
    }

    @Test
    @DisplayName("Текст замість числа спричиняє InvalidInputException")
    void testNonNumericInputThrowsException() {
        try (Scanner scanner = new Scanner("abc")) {
            InvalidInputException exception = assertThrows(
                    InvalidInputException.class,
                    () -> Main.readNumber(scanner, "")
            );
            assertTrue(exception.getMessage().contains("Некоректне число"));
        }
    }
}
