package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

public class CheckoutSolution {
    public Integer checkout(String skus) {
        throw new SolutionNotImplementedException();
    }

    @Data
    static class Item {
        char item;
        int price;
        SpecialOffer specialOffer;
    }

    private static class SpecialOffer {

    }
}
