package befaster.solutions.CHK;

import org.junit.Test;

public class CheckoutSolutionTest {

    @Test
    public void checkoutTest() {
        CheckoutSolution checkoutSolution = new CheckoutSolution();
        System.out.println(checkoutSolution.checkout("AAAAAAAAAEEBBB"));
        assert checkoutSolution.checkout("AAAAAAAAAEEBBB") == 520;
    }
}



