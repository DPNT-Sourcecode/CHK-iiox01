package befaster.solutions.CHK;

import org.junit.Test;

public class CheckoutSolutionTest {

    @Test
    public void checkoutTest() {
        CheckoutSolution checkoutSolution = new CheckoutSolution();
        System.out.println(checkoutSolution.checkout("AAAAAAAAAEE"));
        assert checkoutSolution.checkout("AAAAAAAAAEE") == 460;
    }
}

