package befaster.solutions.CHK;

import org.junit.Test;

public class CheckoutSolutionTest {

    @Test
    public void checkoutTest() {
        CheckoutSolution checkoutSolution = new CheckoutSolution();
        String s = "AAAAAAAAAAAAAEEB";
        System.out.println(checkoutSolution.checkout(s));
        assert checkoutSolution.checkout(s) == 610;
    }
}
