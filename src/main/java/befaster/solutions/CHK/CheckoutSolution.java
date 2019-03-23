package befaster.solutions.CHK;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

public class CheckoutSolution {

    private Map<Character, Item> mapItems = new HashMap<>();

    {
        mapItems.put('A', new Item('A', 50, new SpecialOffer(new int[]{3, 5}, new int[]{20, 50})));
        mapItems.put('B', new Item('B', 30, new SpecialOffer(new int[]{2}, new int[]{15})));
        mapItems.put('C', new Item('C', 20, null));
        mapItems.put('D', new Item('D', 15, null));
        mapItems.put('E', new Item('E', 40, null));
    }

    public Integer checkout(String skus) {

        if (skus.isEmpty())
            return 0;

        skus = calculateFreeItems(skus);

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

    private String calculateFreeItems(String items) {
        int counter = 0;

        for (int i = 0; i < items.length(); ++i)
            if (items.charAt(i) == 'E')
                counter++;

        counter = counter / 2;
        if (counter > 0) {
            StringBuilder sb = new StringBuilder(items);
            for (int i = 0; i < sb.length(); ++i)
                if (sb.charAt(i) == 'B') {
                    sb.replace(i, i + 1, "");
                    --counter;
                    if (counter == 0)
                        return sb.toString();
                }
        }
        return items;
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
                int price = 0;
                int bestOfferIndex = item.specialOffer.minAmount.length - 1;

                for (int i = bestOfferIndex; i >= 0; --i) {
                    int numberOfDeals = amount / item.specialOffer.minAmount[i];
                    if (numberOfDeals > 0) {
                        do {
                            numberOfDeals--;
                            amount -= item.specialOffer.minAmount[i];
                            int priceReduction = numberOfDeals * item.specialOffer.priceReduction[i];
                            price += (item.price * item.specialOffer.minAmount[i]) - priceReduction;
                        } while (numberOfDeals > 0);
                    }
                }
                return price + (item.price * amount);
            }
            return item.price * amount;
        }
    }

    @AllArgsConstructor
    static class SpecialOffer {
        int[] minAmount;
        int[] priceReduction;
    }
}
