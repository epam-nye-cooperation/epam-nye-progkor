package hu.nye.progkor.warehouse.service;

import hu.nye.progkor.warehouse.model.dto.ProductDTO;
import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    ProductDTO getProduct(Long id);

    ProductDTO createProduct(ProductDTO product);

    ProductDTO updateProduct(Long id, ProductDTO productChanges);

    void deleteProduct(Long id);
}
