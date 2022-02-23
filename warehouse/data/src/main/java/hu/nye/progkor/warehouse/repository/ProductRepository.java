package hu.nye.progkor.warehouse.repository;

import hu.nye.progkor.warehouse.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
