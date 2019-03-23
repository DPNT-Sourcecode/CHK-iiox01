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
        mapItems.put('F', new Item('F', 10, null));
        mapItems.put('G', new Item('G', 20, null));
        mapItems.put('H', new Item('H', 10, new SpecialOffer(new int[]{5, 10}, new int[]{5, 20})));
        mapItems.put('I', new Item('I', 30, null));
        mapItems.put('J', new Item('J', 60, null));
        mapItems.put('K', new Item('K', 80, new SpecialOffer(new int[]{2}, new int[]{10})));
        mapItems.put('L', new Item('L', 90, null));
        mapItems.put('M', new Item('M', 60, null));
        mapItems.put('N', new Item('N', 60, null));
        mapItems.put('O', new Item('O', 10, null));
        mapItems.put('P', new Item('P', 50, new SpecialOffer(new int[]{5}, new int[]{50})));
        mapItems.put('Q', new Item('Q', 30, new SpecialOffer(new int[]{3}, new int[]{10})));
        mapItems.put('R', new Item('R', 50, null));
        mapItems.put('S', new Item('S', 30, null));
        mapItems.put('T', new Item('T', 20, null));
        mapItems.put('U', new Item('U', 40, null));
        mapItems.put('V', new Item('V', 40, new SpecialOffer(new int[]{2, 3}, new int[]{10, 20})));
        mapItems.put('W', new Item('W', 20, null));
        mapItems.put('X', new Item('X', 90, null));
        mapItems.put('Y', new Item('Y', 10, null));
        mapItems.put('Z', new Item('Z', 50, null));
    }

    public Integer checkout(String skus) {

        if (skus.isEmpty())
            return 0;

        try {
            Map<Item, Integer> basketItems = parseSkus(skus);
            calculateFreeItems(basketItems);
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

    private void calculateFreeItems(Map<Item, Integer> basketItems) {

        int freeB = basketItems.get(mapItems.get('E')) == null ? 0 : basketItems.get(mapItems.get('E')) / 2;
        int freeF = basketItems.get(mapItems.get('F')) == null ? 0 : basketItems.get(mapItems.get('F')) / 3;
        int freeN = basketItems.get(mapItems.get('N')) == null ? 0 : basketItems.get(mapItems.get('N')) / 3;
        int freeQ = basketItems.get(mapItems.get('R')) == null ? 0 : basketItems.get(mapItems.get('R')) / 3;
        int freeU = basketItems.get(mapItems.get('U')) == null ? 0 : basketItems.get(mapItems.get('U')) / 3;

        basketItems.computeIfPresent(mapItems.get('B'), (item, integer) -> integer - freeB);
        basketItems.computeIfPresent(mapItems.get('F'), (item, integer) -> integer - freeF);
        basketItems.computeIfPresent(mapItems.get('N'), (item, integer) -> integer - freeN);
        basketItems.computeIfPresent(mapItems.get('Q'), (item, integer) -> integer - freeQ);
        basketItems.computeIfPresent(mapItems.get('U'), (item, integer) -> integer - freeU);
    }

    @AllArgsConstructor
    static class Item {
        char code;
        int price;
        SpecialOffer specialOffer;

        static int getCheckoutPrice(Item item, int amount) {

            if (amount == 0)
                return 0;

            if (item == null || amount < 1)
                return -1;

            if (item.specialOffer != null) {
                int price = 0;
                int bestOfferIndex = item.specialOffer.minAmount.length - 1;

                for (int i = bestOfferIndex; i >= 0; --i) {
                    int numberOfDeals = amount / item.specialOffer.minAmount[i];
                    if (numberOfDeals > 0)
                        do {
                            numberOfDeals--;
                            amount -= item.specialOffer.minAmount[i];
                            price += item.price * item.specialOffer.minAmount[i] - item.specialOffer.priceReduction[i];
                        } while (numberOfDeals > 0);
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
