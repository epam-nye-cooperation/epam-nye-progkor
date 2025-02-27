package hu.nye.progkor.webshop.repository.impl;

import hu.nye.progkor.webshop.domain.order.model.Product;
import hu.nye.progkor.webshop.domain.order.model.impl.SimpleProduct;
import hu.nye.progkor.webshop.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class StubProductRepository implements ProductRepository {

    private final List<Product> products;

    public StubProductRepository() {
        products = new ArrayList<>();
        products.add(new SimpleProduct(1L, "Jonagold Apple", 2.99D));
        products.add(new SimpleProduct(2L, "Pink Lady Apple", 6.25D));
        products.add(new SimpleProduct(3L, "Honeycrisp Apple", 4.50D));
        products.add(new SimpleProduct(4L, "Fuji Apple", 5.10D));
        products.add(new SimpleProduct(5L, "Jazz Apple", 2.0D));
        products.add(new SimpleProduct(6L, "Gala Apple", 3.25D));
        products.add(new SimpleProduct(7L, "Golden Delicious Apple", 3.45D));
    }

    @Override
    public List<Product> findAllProducts() {
        return products;
    }

    @Override
    public Product saveProduct(Product product) {
        long productId = generateNextId();
        SimpleProduct productToSave = new SimpleProduct(productId, product.name(), product.netPrice());
        products.add(productToSave);
        return productToSave;
    }

    @Override
    public Product findProduct(Long id) {
        return products.stream()
                .filter(product -> product.id().equals(id))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
    }

    @Override
    public Product updateProduct(Long id, Product productDetails) {
        Product product = findProduct(id);
        SimpleProduct updatedSimpleProduct = new SimpleProduct(id, productDetails.name(), productDetails.netPrice());
        product.name();
        product.netPrice();
        deleteProduct(id);
        products.add(updatedSimpleProduct);
        return updatedSimpleProduct;
    }

    @Override
    public void deleteProduct(Long id) {
        products.removeIf(product -> product.id().equals(id));
    }

    private long generateNextId() {
        return products.stream().mapToLong(Product::id).max().orElse(0L);
    }

}
