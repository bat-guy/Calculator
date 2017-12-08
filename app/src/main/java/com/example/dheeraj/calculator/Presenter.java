package com.example.dheeraj.calculator;

/**
 * Created by Dheeraj on 05-12-2017.
 */

public interface Presenter {
    void addClicked();

    void cancelClicked();

    void ceClicked();

    void decimalClicked();

    void divideClicked();

    void powerClicked();

    void minusClicked();

    void digitClicked(int value);

    void modulusClicked();

    void multiplyClicked();

    void totalClicked();
}
