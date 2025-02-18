package hu.nye.progkor.webshop.domain.warehouse.impl;

import hu.nye.progkor.webshop.domain.order.model.Cart;
import hu.nye.progkor.webshop.domain.order.model.Product;
import hu.nye.progkor.webshop.domain.warehouse.Warehouse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class StubWarehouse implements Warehouse {


        @Override
        public void registerOrderedProducts(List<Product> products) {
            log.info("I have registered the order of products {}", products);
        }

        @Override
        public void notify(Cart cart) {
            registerOrderedProducts(cart.getProducts());
        }
    }
