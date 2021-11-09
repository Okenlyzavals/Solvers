package by.epam.training.solvers.mathematical;

import by.epam.training.solvers.exception.SolverException;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class.
 * Used to provide binary operations
 * based on an operator.
 *
 * @author Baranovsky E. K.
 * @version 1.0
 */
class MathOperationProvider {

    /**
     * A map that stores all accessible operations right now by their operators.
     */
    private static final Map<String, Operation> operationMap = new HashMap<>();

    static {
        operationMap.put("+", (operand1, operand2) -> operand1 + operand2);
        operationMap.put("-", (operand1, operand2) -> operand1 - operand2);
        operationMap.put("*", (operand1, operand2) -> operand1 * operand2);
        operationMap.put("/", (operand1, operand2) -> {
            if (operand2 == 0) {
                throw new SolverException(new ArithmeticException("Division by zero."));
            }
            return operand1 / operand2;
        });
    }

    /**
     * Retrieves specific operation by operator.
     *
     * @param operator The operator.
     * @return Instance of {@code Operation} matching the operator.
     * {@code null} if no such Operations exist in operation map.
     */
    static Operation getOperation(String operator) {
        return operationMap.get(operator);
    }

    /**
     * An inner interface for binary operation.
     * Represents a binary operation.
     * Implementations choose which operation will be performed
     * when calling {@code calculate()}.
     */
    interface Operation {
        /**
         * Calculates the result of an an operation.
         *
         * @param operand1 The first operand.
         * @param operand2 The second operand.
         * @return Result of a calculation in double value.
         */
        double calculate(Double operand1, Double operand2);
    }
}
