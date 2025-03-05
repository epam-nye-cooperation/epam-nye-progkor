package hu.nye.progkor.webshop.controller;

import hu.nye.progkor.webshop.domain.order.model.Product;
import hu.nye.progkor.webshop.domain.order.model.impl.SimpleProduct;
import hu.nye.progkor.webshop.repository.ProductRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

  private final ProductRepository productRepository;

  public ProductRestController(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @GetMapping("/list")
  public ResponseEntity<List<Product>> getProducts() {
    List<Product> products = productRepository.findAllProducts();
    return ResponseEntity.ok(products);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProduct(@PathVariable Long id) {
    Product product = productRepository.findProduct(id);
    return ResponseEntity.ok(product);
  }

  @PostMapping("/create")
  public ResponseEntity<Product> createProduct(@RequestBody SimpleProduct simpleProduct) {
    Product product = productRepository.saveProduct(simpleProduct);
    return ResponseEntity.ok(product);
  }

  @PutMapping("/update")
  public ResponseEntity<Product> updateProduct(@RequestBody SimpleProduct product) {
    Product updatedProduct = productRepository.updateProduct(product.id(), product);
    return ResponseEntity.ok(updatedProduct);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productRepository.deleteProduct(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/testError")
  public String testError() {
    throw new RuntimeException("Test exception");
  }
}
