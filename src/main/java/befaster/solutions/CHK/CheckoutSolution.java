package befaster.solutions.CHK;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CheckoutSolution {

    private static Map<Character, Item> mapItems = new HashMap<>();

    static {
        mapItems.put('A', new Item(50, new SpecialOffer(3, 20)));
        mapItems.put('B', new Item(30, new SpecialOffer(2, 15)));
        mapItems.put('C', new Item(20, null));
        mapItems.put('D', new Item(15, null));
    }

    public Integer checkout(String skus) {
        try {
            Set<Item> basketItems = parseSkus(skus);
        } catch (Exception e) {
            return -1;
        }
    }

    private static Map<Item, Integer> parseSkus(String skus) throws Exception {
        Map<Item, Integer> basketItems = new HashMap<>();
        String[] arrSkus = skus.replace(" ", "").split(",");
        for (String sku : arrSkus)
            if (sku.length() > 1 || !mapItems.containsKey(sku.charAt(0)))
                throw new Exception();
            else {
                Item item = mapItems.get(sku.charAt(0));
                basketItems.putIfAbsent(item, 1);
                basketItems.computeIfPresent(item, (item1, integer) -> {
                    return
                });
            }

    }

    @AllArgsConstructor
    private static class Item {
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
    private static class SpecialOffer {
        int minAmount;
        int priceReduction;
    }
}

