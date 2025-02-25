package hu.nye.progkor.webshop.domain.grossprice;

import hu.nye.progkor.webshop.domain.cart.ShoppingCartService;

public interface GrossPriceCalculator {

    double getAggregatedGrossPrice(ShoppingCartService cartService);
}
