package hu.nye.progkor.webshop.domain.grossprice.impl;

import hu.nye.progkor.webshop.domain.cart.ShoppingCartService;
import hu.nye.progkor.webshop.domain.grossprice.GrossPriceCalculator;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GrossPriceCalculatorImpl implements GrossPriceCalculator {

    @Override
    public double getAggregatedGrossPrice(ShoppingCartService cartService) {
        return cartService.getTotalNetPrice();
    }
}
