package se.lexicon;

public class Calculator {
    
    public static int add(int a, int b){
         return a + b;
    }
    
    public static double add(double a, double b){
        return a + b;
    }
    
    public static int subtract(int a, int b){
         return a-b;
    }

    public static int multiply(int a, int b){
        return a * b;
    }

    public static double divide(double a, double b){
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a / b;
    }

}
