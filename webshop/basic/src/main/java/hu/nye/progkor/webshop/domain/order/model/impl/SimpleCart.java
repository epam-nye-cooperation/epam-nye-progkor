package hu.nye.progkor.webshop.domain.order.model.impl;


import hu.nye.progkor.webshop.domain.order.Coupon;
import hu.nye.progkor.webshop.domain.order.model.Cart;
import hu.nye.progkor.webshop.domain.order.model.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
public class SimpleCart implements Cart {

    private final List<Product> products = new ArrayList<>();
    private final List<Coupon> coupons = new ArrayList<>();

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public List<Coupon> getCoupons() {
        return coupons;
    }

    @Override
    public void addProduct(final Product product) {
        products.add(product);
    }

    @Override
    public void removeProduct(final Product product) {
        products.remove(product);
    }

    @Override
    public void clearCart() {
        products.clear();
    }

    @Override
    public void addCoupon(final Coupon coupon) {
        coupons.add(coupon);
    }

    @Override
    public void removeCoupon(final Coupon coupon) {
        coupons.remove(coupon);
    }

    @Override
    public String toString() {
        return "SimpleCart{" +
                "\rproducts=" + products.stream().map(Object::toString).collect(Collectors.joining("\n")) +
                "\n\rcoupons=" + coupons.stream().map(Objects::toString).collect(Collectors.joining("\n")) +
                '}';
    }
}
