package hu.nye.progkor.webshop.domain.order;

import hu.nye.progkor.webshop.domain.order.model.Cart;

public interface Observer {

    void notify(Cart cart);
}
