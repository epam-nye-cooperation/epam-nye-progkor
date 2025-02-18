package hu.nye.progkor.webshop.domain.order.orderconfirm.impl;


import hu.nye.progkor.webshop.domain.order.model.Cart;
import hu.nye.progkor.webshop.domain.order.orderconfirm.OrderConfirmationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StubOrderConfirmationService implements OrderConfirmationService {

    @Override
    public void sendOrderConfirmation(Cart cart) {
        log.info("An order confirmation for cart {} had been sent.", cart);
    }

    @Override
    public void notify(Cart cart) {
        sendOrderConfirmation(cart);
    }
}
