package hu.nye.progkor.webshop.domain.order.model;


import hu.nye.progkor.webshop.domain.order.Coupon;

import java.util.List;

public interface Cart {

    List<Product> getProducts();

    List<Coupon> getCoupons();

    void addProduct(Product product);

    void removeProduct(Product product);

    void clearCart();

    void addCoupon(Coupon coupon);

    void removeCoupon(Coupon coupon);
}
