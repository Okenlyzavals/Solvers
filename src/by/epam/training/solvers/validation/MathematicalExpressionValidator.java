package by.epam.training.solvers.validation;

import java.util.regex.Pattern;

/**
 * Validator for basic mathematical expressions.
 * <p>This validator is suited
 * for use with mathematical expressions
 * that might contain brackets,
 * but lack any negative of floating point numbers,
 * as well as any operators beyond basic
 * addition, subtraction, multiplication and division.
 *
 * @author Baranovsky E. K.
 * @version 1.0
 */
public class MathematicalExpressionValidator implements TaskValidator {

    /**
     * A regexp pattern that is used to find
     * any illegal symbols or combinations of symbols,
     * such as letters or invalid operators.
     *
     * <p>Currently, any of the following is forbidden within expression:
     * <ul>
     *     <li>Usage of any symbols beside digits, basic operators and brackets (spaces are allowed).
     *     <li>Usage of operators or open parenthesis right before a closed parenthesis.
     *     <li>Usage of symbols that are not operators or open parentheses right before an open parenthesis.
     *     <li>Usage of operators right after an open parenthesis.
     *     <li>Usage of symbols that are not operators or close parentheses right after a close parenthesis.
     *     <li>Ending the expression with an operator.
     *     <li>Starting the expression with an operator.
     *     <li>Usage of consecutive operators.
     *     <li>Division by zero (in an initial expression).
     * </ul>
     */
    private static final Pattern illegalSymbolsPattern;

    static {
        illegalSymbolsPattern = Pattern
                .compile("([\\D&&[^+\\-/*)(]])" +
                        "|([+\\-/*(]\\))" +
                        "|([^+\\-/*(]\\()" +
                        "|(\\([+\\-/*])" +
                        "|(\\)[^+\\-/*)])" +
                        "|([+\\-/*(]$)" +
                        "|(^[+\\-/*)])" +
                        "|([+\\-/*]{2,})" +
                        "|((/0[+\\-/*)]+)|(/0$))");
    }

    @Override
    public boolean validate(String expression) {
        return checkStringValidity(expression) && checkBracketSyntaxInExpression(expression);
    }

    /**
     * Checks if passed string is resembling
     * mathematical expression.
     * Ignores space symbols.
     *
     * @param expression The expression to check.
     * @return {@code false} if expression is null or is blank,
     * or if it contains illegal symbol combinations.
     * {@code true} otherwise.
     */
    private static boolean checkStringValidity(String expression) {
        if (expression == null || expression.isBlank()) {
            return false;
        }

        String collapsedExpression = expression.replace("\s", "");

        return !illegalSymbolsPattern.matcher(collapsedExpression).find();
    }

    /**
     * Checks if an expression contains equal number
     * of open and closed brackets, and if
     * it does not contain any unclosed bracket pairs.
     *
     * @param expression The expression to check.
     * @return {@code true} if bracket syntax of expression is valid,
     * {@code false} otherwise.
     */
    private static boolean checkBracketSyntaxInExpression(String expression) {

        int bracketsCounter = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                bracketsCounter++;
            }
            if (expression.charAt(i) == ')') {
                bracketsCounter--;
            }

            if (bracketsCounter < 0) {
                return false;
            }
        }

        return bracketsCounter == 0;

    }

}
