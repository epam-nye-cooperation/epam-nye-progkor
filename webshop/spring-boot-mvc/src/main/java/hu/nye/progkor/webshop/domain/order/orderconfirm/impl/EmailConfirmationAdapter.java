package hu.nye.progkor.webshop.domain.order.orderconfirm.impl;

import hu.nye.progkor.webshop.domain.order.model.Cart;
import hu.nye.progkor.webshop.domain.order.orderconfirm.OrderConfirmationService;
import hu.nye.progkor.webshop.domain.order.orderconfirm.lib.ConfirmationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class EmailConfirmationAdapter implements OrderConfirmationService {

    private ConfirmationService emailConfirmationService;

    @Override
    public void notify(final Cart cart) {
       log.info("Order confirmation, email adapter, cart: {}", cart);
        sendOrderConfirmation(cart);
    }

    @Override
    public void sendOrderConfirmation(final Cart cart) {
        emailConfirmationService.sendConfirmationMessageAbout(cart.getProducts());
    }
}
