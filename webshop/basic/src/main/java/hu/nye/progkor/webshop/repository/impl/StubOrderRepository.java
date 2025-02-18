package hu.nye.progkor.webshop.repository.impl;

import hu.nye.progkor.webshop.domain.order.model.Cart;
import hu.nye.progkor.webshop.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StubOrderRepository implements OrderRepository {

    @Override
    public void saveOrder(Cart cart) {
        log.info("Saving order {}", cart);
    }
}
