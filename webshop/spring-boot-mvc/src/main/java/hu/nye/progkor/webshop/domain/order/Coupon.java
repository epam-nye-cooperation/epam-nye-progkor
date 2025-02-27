package hu.nye.progkor.webshop.domain.order;

import hu.nye.progkor.webshop.domain.order.model.Product;

import java.util.List;

public interface Coupon {

    String getId();

    double getDiscountForProducts(List<Product> products);
}
