package hu.nye.progkor.warehouse.controller;

import hu.nye.progkor.warehouse.model.dto.ProductDTO;
import hu.nye.progkor.warehouse.model.request.ProductRequest;
import hu.nye.progkor.warehouse.model.response.ProductResponse;
import hu.nye.progkor.warehouse.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
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
@RequiredArgsConstructor
@RequestMapping("/products")
@Log4j2
public class ProductRestController {

    private final ProductService productService;
    private final Converter<ProductDTO, ProductResponse> productDtoToResponseConverter;
    private final Converter<ProductRequest, ProductDTO> productRequestToDtoConverter;

    @GetMapping
    public List<ProductResponse> getProducts() {
        log.info("Get all Products");
        return productService.getAllProducts().stream()
                .map(productDtoToResponseConverter::convert)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(final @PathVariable Long id) {
        log.info("Get a Product, ID:{}", id);
        return productDtoToResponseConverter.convert(productService.getProduct(id));
    }

    @PostMapping
    public ProductResponse createProduct(final @RequestBody ProductRequest productRequest) {
        log.info("Create a new Product, details: {}", productRequest);
        return productDtoToResponseConverter.convert(
                productService.createProduct(productRequestToDtoConverter.convert(productRequest))
        );
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(final @PathVariable Long id, final @RequestBody ProductRequest productRequest) {
        log.info("Update a Product with Id:{} to Product:{}", id, productRequest);
        return productDtoToResponseConverter.convert(
                productService.updateProduct(id, productRequestToDtoConverter.convert(productRequest))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(final @PathVariable Long id) {
        log.info("Delete a Product with ID:{}", id);
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
