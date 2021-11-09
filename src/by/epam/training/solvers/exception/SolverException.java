package by.epam.training.solvers.exception;

/**
 * Basic exception class for Solvers library.
 *
 * @author Baranovsky E. K.
 * @version 1.0
 */
public class SolverException extends RuntimeException {

    public SolverException() {
        super();
    }

    public SolverException(String message) {
        super(message);
    }

    public SolverException(String message, Throwable cause) {
        super(message, cause);
    }

    public SolverException(Throwable cause) {
        super(cause);
    }
}
