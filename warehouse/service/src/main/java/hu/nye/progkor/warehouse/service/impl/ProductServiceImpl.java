package hu.nye.progkor.warehouse.service.impl;

import hu.nye.progkor.warehouse.model.Product;
import hu.nye.progkor.warehouse.model.dto.ProductDTO;
import hu.nye.progkor.warehouse.model.exception.NotFoundException;
import hu.nye.progkor.warehouse.model.exception.ProductUpdateException;
import hu.nye.progkor.warehouse.repository.ProductRepository;
import hu.nye.progkor.warehouse.service.ProductService;
import hu.nye.progkor.warehouse.service.WareHouseService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final WareHouseService wareHouseService;
    private final Converter<ProductDTO, Product> productDtoToEntityConverter;
    private final Converter<Product, ProductDTO> productEntityToDtoConverter;

    @Override
    public List<ProductDTO> getAllProducts() {
        log.info("Get all Products.");
        return productRepository.findAll().stream()
                .map(productEntityToDtoConverter::convert)
                .toList();
    }

    @Override
    public ProductDTO getProduct(final Long id) {
        log.info("Get a Product with ID:{}.", id);
        return productRepository.findById(id)
                .map(productEntityToDtoConverter::convert)
                .orElseThrow(() -> new NotFoundException("There is no Product with ID:" + id));
    }

    @Override
    public ProductDTO createProduct(final ProductDTO product) {
        log.info("Create a Product:{}.", product);
        return Optional.ofNullable(product)
                .map(productDtoToEntityConverter::convert)
                .map(productRepository::save)
                .map(productEntityToDtoConverter::convert)
                .orElseThrow(() -> new IllegalArgumentException("Provided parameter is invalid: " + product));
    }

    @Override
    public ProductDTO updateProduct(final Long id, final ProductDTO productChanges) {
        log.info("Update Product with ID:{} to {}.", id, productChanges);
        final Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no Product with ID:" + id));
        if (productChanges.size() != product.getSize() && wareHouseService.isProductStored(id)) {
            throw new ProductUpdateException("Size can not be changed because it is already stored!");
        }
        product.setName(productChanges.name());
        product.setDescription(productChanges.description());
        product.setNetValue(productChanges.netValue());
        product.setSize(productChanges.size());
        product.setSuggestedFoodStorageType(productChanges.foodStorageType());
        final Product updatedProduct = productRepository.save(product);
        return productEntityToDtoConverter.convert(updatedProduct);
    }

    @Override
    public void deleteProduct(final Long id) {
        log.info("Delete Product with ID:{}.", id);
        productRepository.deleteById(id);
    }
}
