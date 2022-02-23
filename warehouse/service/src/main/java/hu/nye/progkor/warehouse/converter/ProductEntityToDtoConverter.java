package hu.nye.progkor.warehouse.converter;

import hu.nye.progkor.warehouse.model.Product;
import hu.nye.progkor.warehouse.model.dto.ProductDTO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductEntityToDtoConverter implements Converter<Product, ProductDTO> {

    @Override
    public ProductDTO convert(@NonNull final Product source) {
        log.info("Convert Product:{} to ProductDTO.", source);
        return new ProductDTO(source.getId(),
                source.getName(),
                source.getNetValue(),
                source.getSize(),
                source.getSuggestedFoodStorageType(),
                source.getDescription()
        );
    }
}
