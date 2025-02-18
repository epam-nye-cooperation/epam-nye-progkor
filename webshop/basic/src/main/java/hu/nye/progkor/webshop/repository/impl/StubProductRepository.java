package hu.nye.progkor.webshop.repository.impl;

import hu.nye.progkor.webshop.domain.order.model.Product;
import hu.nye.progkor.webshop.domain.order.model.impl.SimpleProduct;
import hu.nye.progkor.webshop.repository.ProductRepository;

import java.util.List;

public class StubProductRepository implements ProductRepository {

    private static final List<Product> PRODUCTS = List.of(
            new SimpleProduct("Jonagold Apple", 2.99D),
            new SimpleProduct("Pink Lady Apple", 6.25D),
            new SimpleProduct("Honeycrisp Apple", 4.50D),
            new SimpleProduct("Fuji Apple", 5.10D),
            new SimpleProduct("Jazz Apple", 2.0D),
            new SimpleProduct("Gala Apple", 3.25D),
            new SimpleProduct("Golden Delicious Apple", 3.45D)
    );


    @Override
    public List<Product> getAllProduct() {
        return PRODUCTS;
    }
}
