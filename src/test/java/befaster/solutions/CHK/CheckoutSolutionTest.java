package befaster.solutions.CHK;

import org.junit.Test;

public class CheckoutSolutionTest {

    @Test
    public void checkoutTest() {
        CheckoutSolution checkoutSolution = new CheckoutSolution();
        String s = "KKKK";
        System.out.println(checkoutSolution.checkout(s));
        assert checkoutSolution.checkout(s) == 240;
    }
}


