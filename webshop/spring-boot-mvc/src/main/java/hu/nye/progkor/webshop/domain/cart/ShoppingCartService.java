package hu.nye.progkor.webshop.domain.cart;


import hu.nye.progkor.webshop.domain.exception.NoSuchProductException;
import hu.nye.progkor.webshop.domain.order.Coupon;
import hu.nye.progkor.webshop.domain.order.Observable;
import hu.nye.progkor.webshop.domain.order.Observer;
import hu.nye.progkor.webshop.domain.order.model.Product;

import java.util.List;

public interface ShoppingCartService extends Observable {

    void order();

    double getTotalNetPrice();

    double getTotalGrossPrice();

    void addProduct(String productName) throws NoSuchProductException;

    List<Product> getProductsFromCart();

    void removeProduct(Product productToRemove);

    void addCoupon(Coupon coupon);

    void removeCoupon(Coupon coupon);

    List<Coupon> getCouponsFromCart();

    double getBasePrice();

    double getDiscountForCoupons();

    void subscribe(Observer observer);
}
