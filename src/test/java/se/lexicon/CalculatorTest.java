package se.lexicon;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// Orders tests by @Order so the report reads add -> subtract -> multiply -> divide,
// instead of JUnit's default (unpredictable) method order. Doesn't affect correctness,
// only readability of results/output.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CalculatorTest {

    // Runs once for the whole class, before any test and before any @BeforeEach.
    // Good for expensive, shared, one-time setup (e.g. opening a DB connection,
    // starting a timer, loading a big file) that every test can reuse.
    // Must be static because no test instance exists yet when it runs.
    private static long suiteStartTime;

    @BeforeAll
    static void initSuite() {
        suiteStartTime = System.currentTimeMillis();
        System.out.println("=== Starting CalculatorTest suite ===");
    }

    // Runs once after all tests finish, pass or fail. Good for releasing whatever
    // was acquired in @BeforeAll (closing connections/files) or printing a summary.
    @AfterAll
    static void tearDownSuite() {
        long elapsed = System.currentTimeMillis() - suiteStartTime;
        System.out.println("=== Finished CalculatorTest suite in " + elapsed + " ms ===");
    }

    // Runs before EVERY test method. Use it to reset state so tests can't leak
    // into each other. Here it logs which test is about to run; if Calculator held
    // instance state (it doesn't - all methods are static), this is where you'd
    // create a fresh instance per test to guarantee isolation.
    @BeforeEach
    void logTestStart(TestInfo testInfo) {
        System.out.println("Running: " + testInfo.getDisplayName());
    }

    // Runs after EVERY test method, pass or fail. Use it for per-test cleanup
    // (e.g. resetting a shared counter, deleting a temp file). Here it just logs
    // completion, but this is the hook you'd use to undo whatever @BeforeEach set up.
    @AfterEach
    void logTestEnd(TestInfo testInfo) {
        System.out.println("Finished: " + testInfo.getDisplayName());
    }

    @Nested
    @DisplayName("add")
    class Add {

        @Test
        @Order(1)
        @DisplayName("adds two positive ints")
        void add_shouldReturnSum_whenGivenTwoPositiveNumbers() {
            assertEquals(5, Calculator.add(2, 3));
        }

        @Test
        @Order(2)
        @DisplayName("adds two negative ints")
        void add_shouldReturnNegativeSum_whenGivenTwoNegativeNumbers() {
            assertEquals(-5, Calculator.add(-2, -3));
        }

        @Test
        @Order(3)
        void add_shouldReturnOtherNumber_whenOneNumberIsZero() {
            assertEquals(7, Calculator.add(0, 7));
        }

        @Test
        void add_shouldReturnSum_whenGivenTwoPositiveNumbers_double() {
            assertEquals(5.5, Calculator.add(2.4, 3.1));
        }

        @Test
        void add_shouldReturnNegativeSum_whenGivenTwoNegativeNumbers_double() {
            assertEquals(-5.8, Calculator.add(-2.4, -3.4));
        }

        @Test
        void add_shouldReturnOtherNumber_whenOneNumberIsZero_double() {
            assertEquals(7.9, Calculator.add(0, 7.9));
        }
    }

    @Nested
    @DisplayName("subtract")
    class Subtract {

        @Test
        void subtract_shouldReturnDifference_whenGivenTwoPositiveNumbers() {
            assertEquals(2, Calculator.subtract(5, 3));
        }

        @Test
        void subtract_shouldReturnNegativeResult_whenSubtrahendIsLarger() {
            assertEquals(-2, Calculator.subtract(3, 5));
        }

        @Test
        void subtract_shouldReturnSameNumber_whenSubtractingZero() {
            assertEquals(7, Calculator.subtract(7, 0));
        }
    }

    @Nested
    @DisplayName("multiply")
    class Multiply {

        @Test
        void multiply_shouldReturnProduct_whenGivenTwoPositiveNumbers() {
            assertEquals(15, Calculator.multiply(3, 5));
        }

        @Test
        void multiply_shouldReturnNegativeProduct_whenOneNumberIsNegative() {
            assertEquals(-15, Calculator.multiply(-3, 5));
        }

        @Test
        void multiply_shouldReturnZero_whenOneNumberIsZero() {
            assertEquals(0, Calculator.multiply(0, 5));
        }

        // Disabled instead of deleted: documents a known gap (int overflow isn't
        // handled) without breaking the build. The reason is recorded so whoever
        // revisits this knows it's a deliberate skip, not an oversight.
        @Test
        @Disabled("Calculator.multiply doesn't guard against int overflow yet")
        void multiply_shouldNotOverflow_whenResultExceedsIntRange() {
            assertEquals(Integer.MAX_VALUE, Calculator.multiply(Integer.MAX_VALUE, 2));
        }
    }

    @Nested
    @DisplayName("divide")
    class Divide {

        @Test
        void divide_shouldReturnQuotient_whenGivenTwoPositiveNumbers() {
            assertEquals(2.5, Calculator.divide(5, 2));
        }

        @Test
        void divide_shouldReturnNegativeQuotient_whenOneNumberIsNegative() {
            assertEquals(-2.5, Calculator.divide(-5, 2));
        }

        @Test
        void divide_shouldThrowArithmeticException_whenDividingByZero() {
            assertThrows(ArithmeticException.class, () -> Calculator.divide(5, 0));
        }
    }
}
