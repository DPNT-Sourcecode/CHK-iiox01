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
        mapItems.put('I', new Item('I', 35, null));
        mapItems.put('J', new Item('J', 60, null));
        mapItems.put('K', new Item('K', 80, new SpecialOffer(new int[]{2}, new int[]{10})));
        mapItems.put('L', new Item('L', 90, null));
        mapItems.put('M', new Item('M', 15, null));
        mapItems.put('N', new Item('N', 40, null));
        mapItems.put('O', new Item('O', 10, null));
        mapItems.put('P', new Item('P', 50, new SpecialOffer(new int[]{5}, new int[]{50})));
        mapItems.put('Q', new Item('Q', 30, new SpecialOffer(new int[]{3}, new int[]{10})));
        mapItems.put('R', new Item('R', 50, null));
        mapItems.put('S', new Item('S', 20, null));
        mapItems.put('T', new Item('T', 20, null));
        mapItems.put('U', new Item('U', 40, null));
        mapItems.put('V', new Item('V', 50, new SpecialOffer(new int[]{2, 3}, new int[]{10, 20})));
        mapItems.put('W', new Item('W', 20, null));
        mapItems.put('X', new Item('X', 17, null));
        mapItems.put('Y', new Item('Y', 20, null));
        mapItems.put('Z', new Item('Z', 21, null));
    }

    public Integer checkout(String skus) {

        if (skus.isEmpty())
            return 0;

        try {
            Map<Item, Integer> basketItems = parseSkus(skus);
            calculateFreeItems(basketItems);
            int priceToAdd = calculateGroupDiscount(basketItems);
            int[] total = new int[1];
            basketItems.forEach((item, amount) -> total[0] += Item.getCheckoutPrice(item, amount));
            return total[0] + priceToAdd;
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
        int freeM = basketItems.get(mapItems.get('N')) == null ? 0 : basketItems.get(mapItems.get('N')) / 3;
        int freeQ = basketItems.get(mapItems.get('R')) == null ? 0 : basketItems.get(mapItems.get('R')) / 3;
        int freeU = basketItems.get(mapItems.get('U')) == null ? 0 : basketItems.get(mapItems.get('U')) / 4;

        basketItems.computeIfPresent(mapItems.get('B'), (item, integer) -> integer - freeB);
        basketItems.computeIfPresent(mapItems.get('F'), (item, integer) -> integer - freeF);
        basketItems.computeIfPresent(mapItems.get('M'), (item, integer) -> integer - freeM);
        basketItems.computeIfPresent(mapItems.get('Q'), (item, integer) -> integer - freeQ);
        basketItems.computeIfPresent(mapItems.get('U'), (item, integer) -> integer - freeU);
    }

    private int calculateGroupDiscount(Map<Item, Integer> basketItems) {

        int numberZitems = basketItems.get(mapItems.get('Z')) == null ? 0 : basketItems.get(mapItems.get('Z'));
        int numberYitems = basketItems.get(mapItems.get('Y')) == null ? 0 : basketItems.get(mapItems.get('Y'));
        int numberTitems = basketItems.get(mapItems.get('T')) == null ? 0 : basketItems.get(mapItems.get('T'));
        int numberSitems = basketItems.get(mapItems.get('S')) == null ? 0 : basketItems.get(mapItems.get('S'));
        int numberXitems = basketItems.get(mapItems.get('X')) == null ? 0 : basketItems.get(mapItems.get('X'));

        int priceToAdd = 0;

        while (numberZitems >= 3) {
            priceToAdd += 45;
            numberZitems -= 3;
            basketItems.compute(mapItems.get('Z'), (item, amount) -> amount -3);
        }

        while (numberZitems + numberYitems >= 3) {
            priceToAdd += 45;
            int reduceValue = numberZitems - 3;
            numberYitems +=  reduceValue;
            basketItems.compute(mapItems.get('Y'), (item, amount) -> amount + reduceValue);
            numberZitems = 0;
            basketItems.compute(mapItems.get('Z'), (item, amount) -> 0);
        }

        while (numberZitems + numberYitems + numberTitems >= 3) {
            priceToAdd += 45;
            int reduceValue = numberYitems + numberZitems - 3;
            numberTitems += reduceValue;
            basketItems.compute(mapItems.get('T'), (item, amount) -> amount + reduceValue);
            numberYitems = 0;
            basketItems.compute(mapItems.get('Y'), (item, amount) -> 0);
            numberZitems = 0;
            basketItems.compute(mapItems.get('Z'), (item, amount) -> 0);
        }

        while (numberZitems + numberYitems + numberTitems + numberSitems >= 3) {
            priceToAdd += 45;
            int reduceValue = numberZitems + numberYitems + numberTitems - 3;
            numberSitems += reduceValue;
            basketItems.compute(mapItems.get('S'), (item, amount) -> amount + reduceValue);
            numberTitems = 0;
            basketItems.compute(mapItems.get('T'), (item, amount) -> 0);
            numberZitems = 0;
            basketItems.compute(mapItems.get('Z'), (item, amount) -> 0);
            numberYitems = 0;
            basketItems.compute(mapItems.get('Y'), (item, amount) -> 0);
        }

        while (numberZitems + numberYitems + numberTitems + numberSitems + numberXitems >= 3) {
            priceToAdd += 45;
            int reduceValue = numberZitems + numberYitems + numberTitems + numberSitems - 3;
            numberXitems += reduceValue;
            basketItems.compute(mapItems.get('X'), (item, amount) -> amount + reduceValue);
            numberSitems = 0;
            basketItems.compute(mapItems.get('S'), (item, amount) -> 0);
            numberTitems = 0;
            basketItems.compute(mapItems.get('T'), (item, amount) -> 0);
            numberZitems = 0;
            basketItems.compute(mapItems.get('Z'), (item, amount) -> 0);
            numberYitems = 0;
            basketItems.compute(mapItems.get('Y'), (item, amount) -> 0);
        }

        return priceToAdd;
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
