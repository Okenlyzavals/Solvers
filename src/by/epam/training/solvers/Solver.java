package by.epam.training.solvers;

import by.epam.training.solvers.validation.TaskValidator;

/**
 * A general interface for all kinds of Solver objects.
 *
 * @author Baranovsky E. K.
 * @version 1.0
 */
public interface Solver {

    /**
     * Solves given task.
     *
     * @param task String task to solve.
     * @return Result of solution as a double value.
     */
    double solve(String task);

    /**
     * Solves given task using custom validator.
     * <p>Uses passed validator
     * to check the task for violations
     * before solution. Use at your own risk.
     *
     * @param task      String task to solve.
     * @param validator The validator used to validate the task.
     * @return Result of solution as a double value.
     * @see TaskValidator
     */
    double solve(String task, TaskValidator validator);

}
