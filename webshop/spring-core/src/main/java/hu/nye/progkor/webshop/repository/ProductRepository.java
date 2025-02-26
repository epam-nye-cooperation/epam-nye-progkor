package hu.nye.progkor.webshop.repository;

import hu.nye.progkor.webshop.domain.order.model.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> findAllProducts();

    Product saveProduct(Product product);

    Product findProduct(Long id);

    Product updateProduct(Long id, Product productDetails);

    void deleteProduct(Long id);
}
