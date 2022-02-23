package hu.nye.progkor.warehouse.converter;

import hu.nye.progkor.warehouse.model.dto.ProductDTO;
import hu.nye.progkor.warehouse.model.dto.StorageDTO;
import hu.nye.progkor.warehouse.model.response.ProductResponse;
import hu.nye.progkor.warehouse.model.response.StorageResponse;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StorageDtoToResponseConverter implements Converter<StorageDTO, StorageResponse> {

    private final Converter<ProductDTO, ProductResponse> productDtoToResponseConverter;

    @Override
    public StorageResponse convert(@NonNull final StorageDTO source) {
        log.info("Convert StorageDTO:{} to StorageResponse.", source);
        return new StorageResponse(source.id(),
                source.foodStorageType(),
                source.capacity(),
                convertProducts(source.products()),
                source.reservedArea(),
                source.saturationStatistic()
        );
    }

    private List<ProductResponse> convertProducts(final List<ProductDTO> products) {
        return products.stream()
                .map(productDtoToResponseConverter::convert)
                .toList();
    }
}
