package hu.nye.progkor.webshop.repository;

import hu.nye.progkor.webshop.domain.order.model.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> getAllProduct();
}
