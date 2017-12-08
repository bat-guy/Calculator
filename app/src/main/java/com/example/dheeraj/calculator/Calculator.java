package com.example.dheeraj.calculator;

/**
 * Created by Dheeraj on 05-12-2017.
 */

public class Calculator {

    /**
     * Method to multiple two numbers
     * @param first first number
     * @param second second number
     * @return result
     */
    public static double multiply(double first, double second) {
        return first * second;
    }

    /**
     * Method to divide two numbers
     * @param first first number
     * @param second second number
     * @return result
     */
    public static double divide(double first, double second) {
        return first / second;
    }

    /**
     * Method to calculate the power of a number
     * @param number the number
     * @param power the power of the number
     * @return result
     */
    public static double exponent(double number, double power) {
        return Math.pow(number, power);
    }

    /**
     * Method to get modulus of two numbers
     * @param first first number
     * @param second second number
     * @return result
     */
    public static double modulus(double first, double second) {
        return first % second;
    }
}
