package hu.nye.progkor.warehouse.converter;

import hu.nye.progkor.warehouse.model.Product;
import hu.nye.progkor.warehouse.model.Storage;
import hu.nye.progkor.warehouse.model.dto.ProductDTO;
import hu.nye.progkor.warehouse.model.dto.StorageDTO;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class StorageDtoToEntityConverter implements Converter<StorageDTO, Storage> {

    private final Converter<ProductDTO, Product> productDtoToEntityConverter;

    @Override
    public Storage convert(@NonNull final StorageDTO source) {
        log.info("Convert StorageDTO:{} to Storage.", source);
        return new Storage(source.id(),
                source.foodStorageType(),
                source.capacity(),
                convertProducts(source.products())
        );
    }

    private List<Product> convertProducts(final List<ProductDTO> products) {
        return products.stream()
                .map(productDtoToEntityConverter::convert)
                .toList();
    }
}
