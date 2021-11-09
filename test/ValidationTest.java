import by.epam.training.solvers.validation.MathematicalExpressionValidator;
import by.epam.training.solvers.validation.TaskValidator;
import org.junit.Assert;
import org.junit.Test;

public class ValidationTest {

    @Test
    public void mathematicalExpressionValidator_invalidString_false() {
        TaskValidator validator = new MathematicalExpressionValidator();
        String expression = "++45-2+(9/8)";

        boolean result = validator.validate(expression);

        Assert.assertFalse(result);

    }

    @Test
    public void mathematicalExpressionValidator_validString_true() {
        TaskValidator validator = new MathematicalExpressionValidator();
        String expression = "45-2+(9/8)";

        boolean result = validator.validate(expression);

        Assert.assertTrue(result);

    }

    @Test
    public void mathematicalExpressionValidator_emptyString_false() {
        TaskValidator validator = new MathematicalExpressionValidator();
        String expression = "";

        boolean result = validator.validate(expression);

        Assert.assertFalse(result);

    }

    @Test
    public void mathematicalExpressionValidator_nullString_false() {
        TaskValidator validator = new MathematicalExpressionValidator();
        String expression = null;

        boolean result = validator.validate(expression);

        Assert.assertFalse(result);

    }

    @Test
    public void mathematicalExpressionValidator_blankString_false() {
        TaskValidator validator = new MathematicalExpressionValidator();
        String expression = "                ";

        boolean result = validator.validate(expression);

        Assert.assertFalse(result);

    }

    @Test
    public void mathematicalExpressionValidator_invalidBracketSyntax_false() {
        TaskValidator validator = new MathematicalExpressionValidator();
        String expression = "(5+)6)(";

        boolean result = validator.validate(expression);

        Assert.assertFalse(result);

    }

    @Test
    public void mathematicalExpressionValidator_initialDivisionByZero_false() {
        TaskValidator validator = new MathematicalExpressionValidator();
        String expression = "(2/0)+5";

        boolean result = validator.validate(expression);

        Assert.assertFalse(result);
    }

    @Test
    public void mathematicalExpressionValidator_divisionByNumberStartingFromZero_true() {
        TaskValidator validator = new MathematicalExpressionValidator();
        String expression = "(2/015)+5";

        boolean result = validator.validate(expression);

        Assert.assertTrue(result);
    }

    @Test
    public void customValidator_validString_true() {
        TaskValidator validator = new TaskValidator() {
            @Override
            public boolean validate(String task) {
                return task.contains("valid");
            }
        };
        String toCheck = "invalid-kolyasochnik";

        boolean result = validator.validate(toCheck);

        Assert.assertTrue(result);

    }

    @Test
    public void customValidator_invalidString_false() {
        TaskValidator validator = new TaskValidator() {
            @Override
            public boolean validate(String task) {
                return task.contains("valid");
            }
        };
        String toCheck = "zdorovy cheloveche";

        boolean result = validator.validate(toCheck);

        Assert.assertFalse(result);

    }

}
