package hu.nye.progkor.webshop.repository;

import hu.nye.progkor.webshop.domain.order.model.Cart;

public interface OrderRepository {
    void saveOrder(Cart cart);
}