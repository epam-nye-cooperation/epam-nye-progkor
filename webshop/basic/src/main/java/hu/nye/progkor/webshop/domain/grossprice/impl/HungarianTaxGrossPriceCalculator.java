package hu.nye.progkor.webshop.domain.grossprice.impl;


import hu.nye.progkor.webshop.domain.cart.ShoppingCartService;
import hu.nye.progkor.webshop.domain.grossprice.GrossPriceCalculator;

public class HungarianTaxGrossPriceCalculator extends GrossPriceCalculatorDecorator {

    public HungarianTaxGrossPriceCalculator(GrossPriceCalculator grossPriceCalculator) {
        super(grossPriceCalculator);
    }

    private static final double TAX_RATE = 1.27;

    @Override
    public double getAggregatedGrossPrice(ShoppingCartService cartService) {
        return super.getAggregatedGrossPrice(cartService) * TAX_RATE;
    }
}
