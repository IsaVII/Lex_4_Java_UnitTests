package se.lexicon;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {

    //ADD 
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

    @Test
    void add_shouldReturnSum_whenGivenTwoPositiveNumbers_double() {
        double result = Calculator.add(2.4, 3.1);

        assertEquals(5.5, result);
    }

    @Test
    void add_shouldReturnNegativeSum_whenGivenTwoNegativeNumbers_double() {
        double result = Calculator.add(-2.4, -3.4);

        assertEquals(-5.8, result);
    }

    @Test
    void add_shouldReturnOtherNumber_whenOneNumberIsZero_double() {
        double result = Calculator.add(0, 7.9);

        assertEquals(7.9, result);
    }

    
    //Subtract
    @Test
    void subtract_shouldReturnDifference_whenGivenTwoPositiveNumbers() {
        int result = Calculator.subtract(5, 3);

        assertEquals(2, result);
    }

    @Test
    void subtract_shouldReturnNegativeResult_whenSubtrahendIsLarger() {
        int result = Calculator.subtract(3, 5);

        assertEquals(-2, result);
    }

    @Test
    void subtract_shouldReturnSameNumber_whenSubtractingZero() {
        int result = Calculator.subtract(7, 0);

        assertEquals(7, result);
    }
}
