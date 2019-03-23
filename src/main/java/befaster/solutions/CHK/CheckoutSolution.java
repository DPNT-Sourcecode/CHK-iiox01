package befaster.solutions.CHK;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

public class CheckoutSolution {

    private Map<Character, Item> mapItems = new HashMap<>();

    {
        mapItems.put('A', new Item('A', 50, new SpecialOffer(3, 20)));
        mapItems.put('B', new Item('B', 30, new SpecialOffer(2, 15)));
        mapItems.put('C', new Item('C', 20, null));
        mapItems.put('D', new Item('D', 15, null));
    }

    public Integer checkout(String skus) {

        if (skus.isEmpty())
            return 0;

        try {
            Map<Item, Integer> basketItems = parseSkus(skus);
            int[] total = new int[1];
            basketItems.forEach((item, amount) -> total[0] += Item.getCheckoutPrice(item, amount));
            return total[0];
        } catch (Exception e) {
            return -1;
        }
    }

    private Map<Item, Integer> parseSkus(String skus) throws Exception {
        Map<Item, Integer> basketItems = new HashMap<>();
        char[] arrSkus = new char[skus.length()];
        for (int i = 0; i < arrSkus.length; ++i)
            arrSkus[i] = skus.charAt(i);

        for (char sku : arrSkus)
            if (!mapItems.containsKey(sku))
                throw new Exception();
            else {
                Item item = mapItems.get(sku);
                basketItems.computeIfPresent(item, (keyItem, amount) -> ++amount);
                basketItems.putIfAbsent(item, 1);
            }
        return basketItems;
    }

    @AllArgsConstructor
    static class Item {
        char code;
        int price;
        SpecialOffer specialOffer;

        static int getCheckoutPrice(Item item, int amount) {

            if (item == null || amount < 1)
                return -1;

            if (item.specialOffer != null) {
                int numberOfDeals = amount / item.specialOffer.minAmount;
                int priceReduction = numberOfDeals * item.specialOffer.priceReduction;
                return (item.price * amount) - priceReduction;
            }

            return item.price * amount;
        }
    }

    @AllArgsConstructor
    static class SpecialOffer {
        int minAmount;
        int priceReduction;
    }
}



