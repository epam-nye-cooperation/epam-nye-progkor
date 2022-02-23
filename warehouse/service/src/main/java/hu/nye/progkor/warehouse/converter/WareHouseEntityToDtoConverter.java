package hu.nye.progkor.warehouse.converter;

import hu.nye.progkor.warehouse.model.Storage;
import hu.nye.progkor.warehouse.model.WareHouse;
import hu.nye.progkor.warehouse.model.dto.StorageDTO;
import hu.nye.progkor.warehouse.model.dto.WareHouseDTO;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WareHouseEntityToDtoConverter implements Converter<WareHouse, WareHouseDTO> {

    private final Converter<Storage, StorageDTO> storageEntityToDtoConverter;

    @Override
    public WareHouseDTO convert(@NonNull final WareHouse source) {
        log.info("Convert WareHouse:{} to WareHouseDTO.", source);
        return new WareHouseDTO(source.getId(),
                source.getLocation(),
                convertStorages(source.getStorages())
        );
    }

    private List<StorageDTO> convertStorages(final List<Storage> storages) {
        return storages.stream()
                .map(storageEntityToDtoConverter::convert)
                .toList();
    }
}
