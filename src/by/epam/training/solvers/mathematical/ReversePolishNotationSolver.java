package by.epam.training.solvers.mathematical;

import by.epam.training.solvers.exception.SolverException;
import by.epam.training.solvers.validation.MathematicalExpressionValidator;
import by.epam.training.solvers.validation.TaskValidator;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that calculates mathematical expressions.
 * <p> Uses reverse polish notation (RPN)
 * to parse and calculate the expression.
 *
 * @author Baranovsky E. K.
 * @version 1.0
 */
public class ReversePolishNotationSolver implements MathematicalExpressionSolver {

    /**
     * A stack that is used for temporary storage of operators.
     * Each entry is one operator.
     */
    private final Stack<String> operatorStack = new Stack<>();
    /**
     * A stack that is used to store the
     * notation of an expression in RPN.
     * Each entry is one component of expression.
     */
    private final Stack<String> outputStack = new Stack<>();

    /**
     * Calculates the result of an expression.
     * Uses MathematicalExpressionValidator to validate expression.
     * if no validator was explicitly passed.
     *
     * @param expression Expression to solve.
     * @return Result of solution as a double value.
     */
    @Override
    public double solve(String expression) {
        return solve(expression, new MathematicalExpressionValidator());
    }

    @Override
    public double solve(String expression, TaskValidator validator) {
        if (!validator.validate(expression)) {
            throw new SolverException("Invalid mathematical expression!");
        }
        operatorStack.clear();
        outputStack.clear();
        parseExpressionToReversePolishNotation(expression);
        return solvePolishNotation();
    }

    /**
     * Converts an expression into
     * RPN form.
     *
     * @param expression The expression to convert.
     */
    private void parseExpressionToReversePolishNotation(String expression) {
        Queue<String> expressionElements = splitExpressionIntoComponents(expression);

        while (!expressionElements.isEmpty()) {
            String component = expressionElements.poll();

            if (ExpressionComponentTypes.NUMERICAL_COMPONENT.matches(component)) {
                outputStack.push(component);
            }
            if (ExpressionComponentTypes.OPERATOR.matches(component)) {
                operateWithOperatorComponent(component);
            }
            if (ExpressionComponentTypes.BRACKET.matches(component)) {
                operateWithBracketComponent(component);
            }
        }

        while (!operatorStack.empty()) {
            outputStack.push(operatorStack.pop());
        }
    }

    /**
     * Utility method.
     * Operates with a component that is operator.
     * <p>This method implements a part of RPN algorithm.
     * If operators' stack is empty, if its last element is
     * a bracket, or if {@code operator} has higher priority than
     * the last element in stack, {@code operator} is pushed into
     * operators' stack.
     * <p>If an operator has equal or lower priority than last operator
     * in operators' stack, this last operator in stack is pushed
     * into output stack. Then this check is repeated until
     * its conditions are not met. After that,
     * {@code operator} is pushed into output stack.
     *
     * @param operator The operator.
     */
    private void operateWithOperatorComponent(String operator) {
        while (comparePriorityWithLastInStack(operator) < 0) {
            outputStack.push(operatorStack.pop());
        }
        operatorStack.push(operator);
    }

    /**
     * Utility method.
     * Operates with a component that is either open or close bracket.
     * <p>This method implements a part of RPN algorithm.
     * If {@code bracket} is an open bracket, it is pushed into operators' stack.
     * If {@code bracket} is a close bracket,
     * operators  are popped  from operators' stack and pushed into output stack one by one
     * until an open bracket becomes the top element of operators' stack.
     * This open bracket is then deleted.
     *
     * @param bracket The bracket element.
     */
    private void operateWithBracketComponent(String bracket) {
        if (bracket.equals("(")) {
            operatorStack.push(bracket);
        } else {
            String operator;
            while (!((operator = operatorStack.pop()).equals("("))) {
                outputStack.add(operator);
            }
        }
    }

    /**
     * Utility method.
     * Compares priority level of a passed operator
     * with the last operator in operators' stack.
     *
     * @param operator The operator to check.
     * @return {@code 1} if the priority of the passed operator is higher,
     * or if operators' stack is empty,
     * or if the top element of operators' stack is a bracket.
     * {@code -1} otherwise.
     */
    private int comparePriorityWithLastInStack(String operator) {

        if (operatorStack.isEmpty()) {
            return 1;
        }

        String topInStack = operatorStack.peek();

        if (ExpressionComponentTypes.BRACKET.matches(topInStack)) {
            return 1;
        }

        if (ExpressionComponentTypes.HIGH_PRIORITY_OPERATOR.matches(operator)
                && ExpressionComponentTypes.LOW_PRIORITY_OPERATOR.matches(topInStack)) {
            return 1;
        } else return -1;
    }

    /**
     * Solves the expression that is already parsed into RPN.
     *
     * @return Result of solution as a double value.
     */
    private double solvePolishNotation() {
        Collections.reverse(outputStack);
        Stack<String> calculationStack = new Stack<>();

        while (!outputStack.isEmpty()) {
            String expressionComponent = outputStack.pop();

            if (ExpressionComponentTypes.NUMERICAL_COMPONENT.matches(expressionComponent)) {
                calculationStack.push(expressionComponent);
            }

            if (ExpressionComponentTypes.OPERATOR.matches(expressionComponent)) {
                String secondOperand = calculationStack.pop();
                String firstOperand = calculationStack.pop();

                MathOperationProvider.Operation operation = MathOperationProvider
                        .getOperation(expressionComponent);
                double operationResult = operation.calculate(Double.parseDouble(firstOperand),
                        Double.parseDouble(secondOperand));

                calculationStack.push(String.valueOf(operationResult));
            }

        }

        if (calculationStack.size() != 1) {
            throw new SolverException("Solution error: end results stack contains " +
                    calculationStack.size() + " values" +
                    " after solving.");
        }

        return Double.parseDouble(calculationStack.pop());
    }

    /**
     * Splits teh initial expression into a queue of its components.
     *
     * @param expression The initial expression.
     * @return A queue of components of an expression:
     * brackets, numerical elements and operators.
     * @see ExpressionComponentTypes
     */
    private Queue<String> splitExpressionIntoComponents(String expression) {
        Queue<String> components = new LinkedList<>();
        Matcher matcher = ExpressionComponentTypes.INITIAL_EXPRESSION_COMPONENT.pattern.matcher(expression);

        while (matcher.find()) {
            components.offer(expression.substring(matcher.start(), matcher.end()));
        }

        return components;
    }

    /**
     * Inner enumeration that contains regex patterns
     * that are used to recognize certain components of an expression.
     * They are used for:
     * <ul>
     *     <li>INITIAL_EXPRESSION_COMPONENT - to find components of initial expression:
     *     natural numbers, operators and brackets.
     *     <li>NUMERICAL_COMPONENT - to find numbers (negative and floating point included).
     *     <li>OPERATOR - to find any operators.
     *     <li>LOW_PRIORITY_OPERATOR - to find low priority operators (+ and - as per now).
     *     <li>HIGH_PRIORITY_OPERATOR - to find high priority operators (* and / as per now).
     *     <li>BRACKET - to find parentheses.
     * </ul>
     */
    private enum ExpressionComponentTypes {
        INITIAL_EXPRESSION_COMPONENT("([\\d]+)|([+\\-/*])|([)(])"),
        NUMERICAL_COMPONENT("\\-?[\\d]+\\.?[\\d]*"),
        OPERATOR("[+\\-/*]"),
        LOW_PRIORITY_OPERATOR("[+\\-]"),
        HIGH_PRIORITY_OPERATOR("[/*]"),
        BRACKET("[)(]");

        private final Pattern pattern;

        ExpressionComponentTypes(String s) {
            pattern = Pattern.compile(s);
        }

        /**
         * Checks if a string matches a pattern of this.
         *
         * @param string The string to check.
         * @return {@code true} if string matches pattern,
         * {@code false} otherwise.
         */
        boolean matches(String string) {
            return this.pattern.matcher(string).matches();
        }
    }

}
