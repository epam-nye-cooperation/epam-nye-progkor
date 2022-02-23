package hu.nye.progkor.warehouse.converter;

import hu.nye.progkor.warehouse.model.Product;
import hu.nye.progkor.warehouse.model.Storage;
import hu.nye.progkor.warehouse.model.dto.ProductDTO;
import hu.nye.progkor.warehouse.model.dto.StorageDTO;
import hu.nye.progkor.warehouse.util.StorageStatisticUtil;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StorageEntityToDtoConverter implements Converter<Storage, StorageDTO> {

    private final StorageStatisticUtil storageStatisticUtil;
    private final Converter<Product, ProductDTO> productEntityToDtoConverter;

    @Override
    public StorageDTO convert(@NonNull final Storage source) {
        log.info("Convert Storage:{} to StorageDTO.", source);
        return new StorageDTO(source.getId(),
                source.getFoodStorageType(),
                source.getCapacity(),
                convertProducts(source.getProducts()),
                storageStatisticUtil.aggregateReservedCapacity(source),
                storageStatisticUtil.calculateReservedCapacityPercentage(source)
        );
    }

    private List<ProductDTO> convertProducts(final List<Product> products) {
        return products.stream()
                .map(productEntityToDtoConverter::convert)
                .toList();
    }
}
