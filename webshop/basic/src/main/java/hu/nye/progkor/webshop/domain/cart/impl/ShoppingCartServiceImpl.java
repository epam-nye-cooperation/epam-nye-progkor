package hu.nye.progkor.webshop.domain.cart.impl;

import hu.nye.progkor.webshop.domain.cart.ShoppingCartService;
import hu.nye.progkor.webshop.domain.exception.NoSuchProductException;
import hu.nye.progkor.webshop.domain.grossprice.impl.GrossPriceCalculatorDecorator;
import hu.nye.progkor.webshop.domain.grossprice.impl.GrossPriceCalculatorImpl;
import hu.nye.progkor.webshop.domain.grossprice.impl.HungarianTaxGrossPriceCalculator;
import hu.nye.progkor.webshop.domain.order.Coupon;
import hu.nye.progkor.webshop.domain.order.Observer;
import hu.nye.progkor.webshop.domain.order.model.Cart;
import hu.nye.progkor.webshop.domain.order.model.Product;
import hu.nye.progkor.webshop.domain.order.model.impl.SimpleCart;
import hu.nye.progkor.webshop.domain.order.orderconfirm.impl.EmailConfirmationAdapter;
import hu.nye.progkor.webshop.domain.order.orderconfirm.impl.StubOrderConfirmationService;
import hu.nye.progkor.webshop.domain.order.orderconfirm.lib.impl.EmailConfirmationService;
import hu.nye.progkor.webshop.domain.warehouse.impl.StubWarehouse;
import hu.nye.progkor.webshop.repository.OrderRepository;
import hu.nye.progkor.webshop.repository.ProductRepository;
import hu.nye.progkor.webshop.repository.impl.StubOrderRepository;
import hu.nye.progkor.webshop.repository.impl.StubProductRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final Cart cart = new SimpleCart();
    private final ProductRepository productRepository = new StubProductRepository();
    private final OrderRepository orderRepository = new StubOrderRepository();
    private final HungarianTaxGrossPriceCalculator grossPriceCalculatorDecorator = new HungarianTaxGrossPriceCalculator(new GrossPriceCalculatorDecorator(new GrossPriceCalculatorImpl()));
    private final List<Observer> observers = List.of(
            new EmailConfirmationAdapter(new EmailConfirmationService()),
            new StubOrderConfirmationService(),
            new StubWarehouse()
    );


    @Override
    public void order() {
        orderRepository.saveOrder(cart);
        log.info("Order Net price: {}", getTotalNetPrice());
        log.info("Order TotalGross price: {}", getTotalGrossPrice());
        observers.forEach(observer -> observer.notify(cart));
        cart.clearCart();
    }

    @Override
    public double getTotalNetPrice() {
        double basePrice = getBasePrice();
        double discount = getDiscountForCoupons();
        return basePrice - discount;
    }

    @Override
    public double getTotalGrossPrice() {
        return grossPriceCalculatorDecorator.getAggregatedGrossPrice(this);
    }

    @Override
    public void addProduct(String productName) throws NoSuchProductException {
        Product productToAdd = productRepository.getAllProduct()
                .stream()
                .filter(product -> product.name().equals(productName))
                .findFirst()
                .orElseThrow(NoSuchProductException::new);
        cart.addProduct(productToAdd);
    }

    @Override
    public List<Product> getProductsFromCart() {
        return cart.getProducts();
    }

    @Override
    public void removeProduct(final Product productToRemove) {
        cart.removeProduct(productToRemove);
    }

    @Override
    public void addCoupon(final Coupon coupon) {
        cart.addCoupon(coupon);
    }

    @Override
    public void removeCoupon(final Coupon coupon) {
        cart.removeCoupon(coupon);
    }

    @Override
    public List<Coupon> getCouponsFromCart() {
        return cart.getCoupons();
    }

    @Override
    public double getBasePrice() {
        double basePrice = 0;
        for (Product currentProduct : cart.getProducts()) {
            basePrice += currentProduct.netPrice();
        }
        return basePrice;
    }

    @Override
    public double getDiscountForCoupons() {
        double discount = 0;
        for (Coupon coupon : cart.getCoupons()) {
            discount += coupon.getDiscountForProducts(cart.getProducts());
        }
        return discount;
    }

    @Override
    public void subscribe(final Observer observer) {
        observers.add(observer);
    }

}
