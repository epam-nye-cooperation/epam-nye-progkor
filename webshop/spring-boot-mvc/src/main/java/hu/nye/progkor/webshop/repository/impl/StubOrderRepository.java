package hu.nye.progkor.webshop.repository.impl;

import hu.nye.progkor.webshop.domain.order.model.Cart;
import hu.nye.progkor.webshop.repository.OrderRepository;
import hu.nye.progkor.webshop.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StubOrderRepository implements OrderRepository {

    // Circular dependency
    private final ProductRepository productRepository;

    // The Lazy annotation, the függőség akkor lesz betöltve amikor szükség van rá és nem az alkalmazás indulásánál.
    public StubOrderRepository(@Lazy ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void saveOrder(Cart cart) {
        log.info("Saving order {}", cart);
    }
}
