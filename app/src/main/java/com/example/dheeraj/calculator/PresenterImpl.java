package com.example.dheeraj.calculator;

import android.util.Log;

import com.fathzer.soft.javaluator.DoubleEvaluator;

import java.util.ArrayList;

/**
 * Created by Dheeraj on 05-12-2017.
 */

public class PresenterImpl implements Presenter {

    private View view;

    private String ADD = "+";
    private String MINUS = "-";
    private String MULTIPLY = "*";
    private String DIVIDE = "/";
    private String MODULUS = "%";
    private String POWER = "^";
    private String DECIMAL = ".";

    private boolean isTotalDone = false;

    /**
     * This is the arrayList where all the expressions are stored.
     * Each element contains either a number or an operator (e.g., {2,+,3,/,4})
     * An operator and a number are never stored together in on index.
     * When evaluating, the index operator is searched and the two numbers are acquired from index-1 and index+1
     */
    private ArrayList<String> expressionList;
    private boolean isError = false;
    private String INFINITY = "Infinity";

    public PresenterImpl(View view) {
        this.view = view;
        expressionList = new ArrayList<>();
    }

    /**
     * Method called when add button is clicked
     */
    @Override
    public void addClicked() {
        resetTotal();
        checkOperatorsAndExpressionList(ADD);
    }

    /**
     * Method called when cancel button is clicked
     */
    @Override
    public void cancelClicked() {
        view.setText("0");
        expressionList.clear();
    }

    /**
     * Method called when CE button is clicked
     */
    @Override
    public void ceClicked() {

        //Checking if the length of the text is > 1
        //If true then the last character is omitted from the string
        //else cancelClicked() is called.
        if (view.getTextValue().length() > 1) {
            view.setText(view.getTextValue().substring(0, view.getTextValue().length() - 1));

            //Checking if the last index of the arrayList contains any operator or not
            //If true then the last index is removed from the arrayList.
            if (checkOperatorForInput(view.getTextValue().substring(view.getTextValue().length() - 1))) {
                expressionList.remove(expressionList.size() - 1);

                //Checking if the length of the string of last index of arrayList is > 1
                //If true then the last character is omitted from the index
                //else last index is removed
            } else if (!expressionList.get(expressionList.size() - 1).isEmpty() &&
                    expressionList.get(expressionList.size() - 1).length() > 1) {
                expressionList.set(expressionList.size() - 1, expressionList.get(expressionList.size()
                        - 1).substring(0, expressionList.get(expressionList.size() - 1).length() - 1));
                Log.e("TAG", expressionList.get(expressionList.size() - 1));
            } else {
                expressionList.remove(expressionList.size() - 1);
            }
        } else {
            //Cancel called to clear the arrayList and set the input as 0
            cancelClicked();
        }

    }

    /**
     * Method called when decimal button is clicked
     */
    @Override
    public void decimalClicked() {
        //Checking if the textView string == 0 or not
        //If yes, then the nothing will happen (As per the guidelines)
        //else the decimal will be placed.
        if (!view.getTextValue().equalsIgnoreCase("0")) {
            /*expressionList.add("0" + DECIMAL);
            view.setText(view.getTextValue() + expressionList.get(expressionList.size() - 1));*/
            //Checking if the last character of the input text is an operator or not.
            //If yes then the 0. is added to the input text and a new index with the same value is added to the arrayList
            if (checkOperatorForInput(view.getTextValue().substring(view.getTextValue().length() - 1))) {
                expressionList.add("0" + DECIMAL);
                view.setText(view.getTextValue() + expressionList.get(expressionList.size() - 1));

                //Checking if the last char of the input string contains a decimal or not
                //If not then the decimal is appended to the input string string as well as to the last index of the arrayList
            } else if (!isTotalDone && !expressionList.get(expressionList.size() - 1).contains(DECIMAL)) {
                expressionList.set(expressionList.size() - 1, expressionList.get(expressionList.size() - 1) + DECIMAL);
                view.setText(view.getTextValue() + DECIMAL);

                //Checking if the last char of the input string contains a decimal or not and if isTotalDone true or not
                //if isTotalDone == true, then arrayList is cleared and ".0" is set to the textView as
                //well as to the arrayList
            } else if (isTotalDone && !expressionList.get(expressionList.size() - 1).contains(DECIMAL)) {
                expressionList.clear();
                expressionList.add("0" + DECIMAL);
                view.setText(expressionList.get(0));
                resetTotal();
            }
        }
    }

    /**
     * Method called to reset the value of the isTotalDone
     * This boolean informs if recently a calculation has been done or not
     * Based on it's value, it is decided whether to add a new value to the input textView or append it(As per rule no 7 from the assignment)
     */
    private void resetTotal() {
        if (isTotalDone) {
            isTotalDone = false;
        }
    }

    /**
     * Method called when the divide button is clicked
     */
    @Override
    public void divideClicked() {
        resetTotal();
        checkOperatorsAndExpressionList(DIVIDE);
    }

    /**
     * Method called when the power button is clicked
     */
    @Override
    public void powerClicked() {
        resetTotal();
        checkOperatorsAndExpressionList(POWER);
    }

    /**
     * Method called when the minus button is clicked
     */
    @Override
    public void minusClicked() {
        resetTotal();
        checkOperatorsAndExpressionList(MINUS);
    }

    /**
     * Method called when a digit button is clicked
     *
     * @param value the button's value (1, 2, 3 etc.)
     */
    @Override
    public void digitClicked(int value) {
        //Checking if the value of the input text < 16
        if (view.getTextValue().length() < 16) {
            //
            //Checking if the textView's value is 0 or not
            //If yes, then the value of the input button is added to the textView as well as arrayList
            if (view.getTextValue().equalsIgnoreCase("0")) {
                expressionList.clear();//Clearing the arrayList in case there is any character in there
                view.setText(String.valueOf(value));
                expressionList.add(String.valueOf(value));

                //Checking if the last character of the textView contains a char or not and if(isTotalDone)
                //if there is no operator isTotalDone = false, then the value of input button is appended to the textView
                // and the last index of the arrayList
            } else if (!isTotalDone && !checkOperatorForInput(view.getTextValue().substring(view.getTextValue().length() - 1))) {
                view.setText(view.getTextValue() + String.valueOf(value));
                expressionList.set(expressionList.size() - 1, expressionList.get(expressionList.size() - 1) + value);

                //Checking if the last character of the textView contains a char or not and if(isTotalDone)
                //if there is no operator and isTotalDone = true, then the value of input button is set to the textView
                // and a new index is added to the arrayList
            } else if (isTotalDone && !checkOperatorForInput(view.getTextValue().substring(view.getTextValue().length() - 1))) {
                view.setText(String.valueOf(value));
                expressionList.clear();
                expressionList.add(String.valueOf(value));
                resetTotal();
            } else {
                view.setText(view.getTextValue() + String.valueOf(value));
                expressionList.add(String.valueOf(value));
            }
        }

    }

    /**
     * Method called when the modulus button is clicked
     */
    @Override
    public void modulusClicked() {
        resetTotal();
        checkOperatorsAndExpressionList(MODULUS);
    }

    /**
     * Method called when the multiple button is clicked
     */
    @Override
    public void multiplyClicked() {
        resetTotal();
        checkOperatorsAndExpressionList(MULTIPLY);
    }

    /**
     * Method called when the total button is clicked
     */
    @Override
    public void totalClicked() {
        //Checking if the arrayList's size is > 3 or not because of the arrayList's structure
        if (expressionList.size() >= 3) {
            //Evaluating the operators in the order of their precedence(As per assignment)
            calculateExpression(POWER);
            calculateExpression(MULTIPLY);
            calculateExpression(DIVIDE);
            calculateExpression(MODULUS);

            //Checking if any error has occurred during the calculation
            //If yes, then the result is discarded and an error toast is shown to the user
            //else the expression is further evaluated
            if (!isError) {
                //Evaluating add and subtract
                String result = evaluateAddSubtract();
                expressionList.clear();

                //Checking if the result is negative or not and is not "-0"
                //If yes, then the minus is added to the first index of arrayList and the result to the second
                if (result.contains(MINUS) && Math.abs(Double.parseDouble(result)) != 0) {
                    expressionList.add(MINUS);
                    expressionList.add(result.replaceAll(MINUS, ""));
                    expressionList.set(1, checkLastDigits(String.valueOf(Math.abs(Double.parseDouble(result)))));
                    view.setText(expressionList.get(0) + expressionList.get(1));
                } else {
                    //If the result == "-0", then it is simply set to 0
                    expressionList.add(checkLastDigits(String.valueOf(Math.abs(Double.parseDouble(result)))));
                    view.setText(expressionList.get(0));
                }

                //Setting it to true to imply that a calculation has been performed
                isTotalDone = true;
            } else {
                view.showToast();
                cancelClicked();
                isError = false;
            }
        }
    }

    /**
     * Checking if the last two chars of the result == .0 or not
     * If yes, then they are omitted from the string
     * else the string is returned as is.
     *
     * @param input result string
     * @return final result
     */
    private String checkLastDigits(String input) {
        if (input.length() >= 3 /*&& input.substring(input.length() - 2, input.length()).equalsIgnoreCase(".0")*/) {
            String substring = input.substring(input.length() - 2, input.length());
            if (substring.equalsIgnoreCase(".0")) {
                return input.replace(".0", "");
            } else {
                return input;
            }
        } else {
            return input;
        }
    }

    /**
     * Method to evaluate the ADD and SUBTRACT operations
     * As their precedence is same, so Javaluator lib is used to evaluate the result to avoid any wrong results
     *
     * @return The evaluated result
     */
    private String evaluateAddSubtract() {
        String expression = new String();

        //Concatenating the operators and the numbers to form an expression
        for (int i = 0; i < expressionList.size(); i++) {
            if (i == 0) {
                expression = expressionList.get(i);
            } else if (i < expressionList.size() - 1) {
                expression = expression.concat(expressionList.get(i));
            } else if (i == expressionList.size() - 1 && !checkOperatorForInput(expressionList.get(i))) {
                expression = expression.concat(expressionList.get(i));
            }

        }

        return String.valueOf(new DoubleEvaluator().evaluate(expression));
    }

    /**
     * Method to evaluate the expression based on the operator
     *
     * @param operator the operator
     */
    private void calculateExpression(String operator) {
        //Checking if(isError) and if the arrayList contains that operator or not and if the list's size
        //is > 2 or not.
        if (!isError && expressionList.contains(operator) && expressionList.size() > 2) {
            //Checking if the last element of the arrayList == operator or not
            //If yes, then it is removed from the list as it is of no use.
            //else the expression is evaluated
            if (expressionList.get(expressionList.size() - 1).equalsIgnoreCase(operator)) {
                expressionList.remove(expressionList.size() - 1);
            } else {
                int index = expressionList.indexOf(operator);//Getting the index of the operator
                String result = setCalculation(operator, index);//Getting the result after evaluation

                //Checking if the result == Infinity or not
                //If yes, then the result is ignored
                if (!result.equals(INFINITY)) {
                    //Checking if the result is negative or not
                    //If yes, then the MINUS is set to index-1 and result is set on index of arrayList
                    //And index+1 is removed
                    if (result.contains(MINUS)) {
                        expressionList.set(index - 1, "-");
                        expressionList.set(index, String.valueOf(Math.abs(Double.parseDouble(result))));
                        expressionList.remove(index + 1);
                    } else {
                        //The result is set on index-1 and index and index+1 are removed fro the arrayList
                        expressionList.set(index - 1, result);
                        expressionList.remove(index + 1);
                        expressionList.remove(index);
                    }

                } else {
                    isError = true;
                }
            }

            //Checking if the arrayList contains the operator or not
            //If yes, then a recursive call to this method is made
            if (!isError && expressionList.size() > 2 && expressionList.contains(operator)) {
                calculateExpression(operator);
            }
        }

        //Checking if the last element of the arrayList == operator or not
        //If yes, then it is removed from the list as it is of no use.
        if (!isError && expressionList.get(expressionList.size() - 1).equalsIgnoreCase(operator)) {
            expressionList.remove(expressionList.size() - 1);
        }

    }

    /**
     * Method to evaluate the expressions.
     * operator argument checked to match which operation is to be done.
     * the two numbers are acquired from index-1 and index+1
     *
     * @param operator the operator
     * @param index    index of the operator
     * @return
     */
    private String setCalculation(String operator, int index) {
        if (operator.equalsIgnoreCase(MODULUS)) {
            return String.valueOf(Calculator.modulus(
                    Double.parseDouble(expressionList.get(index - 1)),
                    Double.parseDouble(expressionList.get(index + 1))));
        } else if (operator.equalsIgnoreCase(MULTIPLY)) {
            return String.valueOf(Calculator.multiply(
                    Double.parseDouble(expressionList.get(index - 1)),
                    Double.parseDouble(expressionList.get(index + 1))));
        } else if (operator.equalsIgnoreCase(DIVIDE)) {
            return String.valueOf(Calculator.divide(
                    Double.parseDouble(expressionList.get(index - 1)),
                    Double.parseDouble(expressionList.get(index + 1))));
        } else {
            return String.valueOf(Calculator.exponent(
                    Double.parseDouble(expressionList.get(index - 1)),
                    Double.parseDouble(expressionList.get(index + 1))));
        }
    }

    /**
     * Checking if there is any other operator immediately before the input
     *
     * @param substring the input string
     * @return true if substring has any operator else false
     */

    private boolean checkOperatorForInput(String substring) {
        if (substring.equalsIgnoreCase(ADD) || substring.equalsIgnoreCase(MINUS) ||
                substring.equalsIgnoreCase(MULTIPLY) || substring.equalsIgnoreCase(DIVIDE) ||
                substring.equalsIgnoreCase(MODULUS) || substring.equalsIgnoreCase(POWER)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checking if there is any other operator immediately before the input
     * Also, it is check that if the input string = 0 or not.
     * If true, then the result is discarded.
     *
     * @param operator operator name
     */
    private void checkOperatorsAndExpressionList(String operator) {
        //Checking if the input text is < 16 and if there is any operator on the last index of the arrayList or not
        if (view.getTextValue().length() < 16 && !checkOperatorForInput(view.getTextValue().substring(
                view.getTextValue().length() - 1))/*&& !view.getTextValue().equalsIgnoreCase("0")*/) {

            //Checking if the input text == 0 or not
            //If yes, then the operator is added to the arrayList and set to the textView
            if (view.getTextValue().equalsIgnoreCase("0")) {
                if (operator.equalsIgnoreCase(MINUS)) {
                    view.setText(operator);
                    expressionList.add(operator);
                }
            } else {
                //The operator is appended to the textView and added to the arrayList
                view.setText(view.getTextValue() + operator);
                expressionList.add(operator);
            }

        }
    }
}
