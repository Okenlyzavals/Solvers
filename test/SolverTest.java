import by.epam.training.solvers.Solver;
import by.epam.training.solvers.exception.SolverException;
import by.epam.training.solvers.mathematical.ReversePolishNotationSolver;
import by.epam.training.solvers.validation.TaskValidator;
import org.junit.Assert;
import org.junit.Test;

public class SolverTest {

    @Test
    public void rpnSolver_solveTest() {
        Solver solver = new ReversePolishNotationSolver();
        String expression = "(51/3)+5/2-56+(2-4)";

        double result = solver.solve(expression);

        Assert.assertEquals(-38.5, result, 0.001);

    }

    @Test(expected = SolverException.class)
    public void rpnSolver_divisionByZeroTest() {
        Solver solver = new ReversePolishNotationSolver();
        String expression = "(51/(5-5))";

        solver.solve(expression);

    }

    @Test(expected = SolverException.class)
    public void rpnSolver_invalidExpressionTest() {
        Solver solver = new ReversePolishNotationSolver();
        String expression = "(1/lorem ipsum)";

        solver.solve(expression);

    }

    @Test
    public void solver_customSolverTestNoValidator() {
        Solver solver = new Solver() {
            @Override
            public double solve(String task) {
                return Double.parseDouble(task);
            }

            @Override
            public double solve(String task, TaskValidator validator) {
                return solve(task);
            }
        };
        String task = "56.2";

        double result = solver.solve(task);

        Assert.assertEquals(56.2, result, 0.01);

    }

    @Test(expected = SolverException.class)
    public void solver_customSolverTestValidator_invalidExpression() {
        Solver solver = new Solver() {
            @Override
            public double solve(String task) {
                return Double.parseDouble(task);
            }

            @Override
            public double solve(String task, TaskValidator validator) {
                if (!validator.validate(task)) {
                    throw new SolverException("Invalid task!");
                }
                return Double.parseDouble(task);
            }
        };
        TaskValidator validator = task -> !task.contains(".666");
        String task = "12.666";

        solver.solve(task, validator);

    }

}
