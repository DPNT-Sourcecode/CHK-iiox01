package befaster.solutions.CHK;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

public class CheckoutSolution {

    private Map<Character, Item> mapItems = new HashMap<>();

    {
        mapItems.put('A', new Item(50, new SpecialOffer(3, 20)));
        mapItems.put('B', new Item(30, new SpecialOffer(2, 15)));
        mapItems.put('C', new Item(20, null));
        mapItems.put('D', new Item(15, null));
    }

    public Integer checkout(String skus) {
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
        String[] arrSkus = skus.replace(" ", "").split(",");
        for (String sku : arrSkus)
            if (sku.length() > 1 || !mapItems.containsKey(sku.charAt(0)))
                throw new Exception();
            else {
                Item item = mapItems.get(sku.charAt(0));
                basketItems.computeIfPresent(item, (keyItem, amount) -> ++amount);
                basketItems.putIfAbsent(item, 1);
            }
        return basketItems;
    }

    @AllArgsConstructor
    static class Item {
        int price;
        SpecialOffer specialOffer;

        static int getCheckoutPrice(Item item, int amount) {

            if (item == null || amount < 1)
                return -1;

            if (item.specialOffer != null) {
                int numberOfDeals = item.specialOffer.minAmount / amount;
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

