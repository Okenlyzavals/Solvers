package by.epam.training.solvers.validation;

/**
 * General interface for validators.
 * <p>Validator is used to validate a task
 * that is passed to solver
 * before executing solution.
 *
 * @author Baranovsky E. K.
 * @version 1.0
 */
public interface TaskValidator {
    /**
     * Validates the task string.
     *
     * @param task String expression to validate.
     * @return {@code true} if expression is valid,
     * {@code false} otherwise.
     */
    boolean validate(String task);
}
