package hu.nye.progkor.webshop.domain.order.orderconfirm;

import hu.nye.progkor.webshop.domain.order.Observer;
import hu.nye.progkor.webshop.domain.order.model.Cart;

public interface OrderConfirmationService extends Observer {

    void sendOrderConfirmation(Cart cart);
}
