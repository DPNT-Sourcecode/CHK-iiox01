package befaster.solutions.CHK;

import org.junit.Test;

public class CheckoutSolutionTest {

    @Test
    public void checkoutTest() {
        CheckoutSolution checkoutSolution = new CheckoutSolution();
        String s = "AAAAAAAAAEEEEBBBC";
        System.out.println(checkoutSolution.checkout(s));
        assert checkoutSolution.checkout(s) == 380 + 160 + 30 + 20;
    }
}




