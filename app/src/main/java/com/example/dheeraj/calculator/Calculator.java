package com.example.dheeraj.calculator;

/**
 * Created by Dheeraj on 05-12-2017.
 */

public class Calculator {

    public static double add(double first, double second) {
        return first + second;
    }

    public static double subtract(double first, double second) {
        return first - second;
    }

    public static double multiply(double first, double second) {
        return first * second;
    }

    public static double divide(double first, double second) {
        return first / second;
    }

    public static double exponent(double number, double power) {

        return Math.pow(number, power);

    }

    public static double modulus(double first, double second) {
        return first % second;
    }
}
