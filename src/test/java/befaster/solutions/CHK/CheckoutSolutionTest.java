package befaster.solutions.CHK;

import org.junit.Test;

public class CheckoutSolutionTest {

    @Test
    public void checkoutTest() {
        CheckoutSolution checkoutSolution = new CheckoutSolution();
        assert checkoutSolution.checkout("A,A,A,B,B,C,D,A,B") == 290;
    }
}

