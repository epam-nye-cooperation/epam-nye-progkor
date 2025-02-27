package hu.nye.progkor.webshop.domain.cart.impl;

import hu.nye.progkor.webshop.domain.cart.ShoppingCartService;
import hu.nye.progkor.webshop.domain.exception.NoSuchProductException;
import hu.nye.progkor.webshop.domain.grossprice.impl.HungarianTaxGrossPriceCalculator;
import hu.nye.progkor.webshop.domain.order.Coupon;
import hu.nye.progkor.webshop.domain.order.Observer;
import hu.nye.progkor.webshop.domain.order.model.Cart;
import hu.nye.progkor.webshop.domain.order.model.Product;
import hu.nye.progkor.webshop.repository.OrderRepository;
import hu.nye.progkor.webshop.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Slf4j
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final Cart cart;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final HungarianTaxGrossPriceCalculator grossPriceCalculatorDecorator;
    private final List<Observer> observers;

    // #1. Konstruktor DI stratégia, ez a preferált
    public ShoppingCartServiceImpl(Cart cart, ProductRepository productRepository, OrderRepository orderRepository, HungarianTaxGrossPriceCalculator grossPriceCalculatorDecorator, List<Observer> observers) {
        this.cart = cart;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.grossPriceCalculatorDecorator = grossPriceCalculatorDecorator;
        this.observers = observers;
    }

    List<Observer> observersTemp2;

    // #2. Setter DI stratégia
    @Autowired
    public void setObserversTemp2(List<Observer> observersTemp2) {
        this.observersTemp2 = observersTemp2;
    }

    // #3. Reflexiós DI stratégia, ez a legkevésbé preferált
    @Autowired
    List<Observer> observersTemp;

    @PostConstruct
    public void populate() {
        log.info("Succesfully initalized the Shopping cart.");
    }

    @PreDestroy
    public void cleanUp() {
        log.info("CleanUp process of the Shopping cart.");
    }

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
        Product productToAdd = productRepository.findAllProducts()
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
