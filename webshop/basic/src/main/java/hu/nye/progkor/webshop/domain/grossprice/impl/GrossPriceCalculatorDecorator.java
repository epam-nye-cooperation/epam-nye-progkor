package hu.nye.progkor.webshop.domain.grossprice.impl;

import hu.nye.progkor.webshop.domain.cart.ShoppingCartService;
import hu.nye.progkor.webshop.domain.grossprice.GrossPriceCalculator;

public class GrossPriceCalculatorDecorator implements GrossPriceCalculator {

    public GrossPriceCalculatorDecorator(GrossPriceCalculator grossPriceCalculator) {
        this.grossPriceCalculator = grossPriceCalculator;
    }

    private final GrossPriceCalculator grossPriceCalculator;

    @Override
    public double getAggregatedGrossPrice(ShoppingCartService cartService) {
        return grossPriceCalculator.getAggregatedGrossPrice(cartService);
    }
}
