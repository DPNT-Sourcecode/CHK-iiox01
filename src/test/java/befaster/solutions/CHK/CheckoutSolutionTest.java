package befaster.solutions.CHK;

import org.junit.Test;

public class CheckoutSolutionTest {

    @Test
    public void checkoutTest() {
        CheckoutSolution checkoutSolution = new CheckoutSolution();
        assert checkoutSolution.checkout("A,B,C,D") == 115;
    }
}
