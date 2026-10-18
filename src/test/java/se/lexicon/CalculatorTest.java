package se.lexicon;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {

    @Test
    void add_shouldReturnSum_whenGivenTwoPositiveNumbers() {
        int result = Calculator.add(2, 3);

        assertEquals(5, result);
    }

    @Test
    void add_shouldReturnNegativeSum_whenGivenTwoNegativeNumbers() {
        int result = Calculator.add(-2, -3);

        assertEquals(-5, result);
    }

    @Test
    void add_shouldReturnOtherNumber_whenOneNumberIsZero() {
        int result = Calculator.add(0, 7);

        assertEquals(7, result);
    }
}
