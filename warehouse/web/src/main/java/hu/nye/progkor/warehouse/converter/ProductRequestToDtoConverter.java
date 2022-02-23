package hu.nye.progkor.warehouse.converter;

import hu.nye.progkor.warehouse.model.dto.ProductDTO;
import hu.nye.progkor.warehouse.model.request.ProductRequest;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductRequestToDtoConverter implements Converter<ProductRequest, ProductDTO> {

    @Override
    public ProductDTO convert(@NonNull final ProductRequest source) {
        log.info("Convert ProductRequest:{} to ProductDTO.", source);
        return new ProductDTO(null,
                source.getName(),
                source.getNetValue(),
                source.getSize(),
                source.getFoodStorageType(),
                source.getDescription()
        );
    }
}
