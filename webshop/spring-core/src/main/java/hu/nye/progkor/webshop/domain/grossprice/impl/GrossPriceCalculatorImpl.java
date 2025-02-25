package hu.nye.progkor.webshop.domain.grossprice.impl;

import hu.nye.progkor.webshop.domain.cart.ShoppingCartService;
import hu.nye.progkor.webshop.domain.grossprice.GrossPriceCalculator;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component("grossPriceCalculatorImpl")
public class GrossPriceCalculatorImpl implements GrossPriceCalculator {

    @Override
    public double getAggregatedGrossPrice(ShoppingCartService cartService) {
        return cartService.getTotalNetPrice();
    }
}
