package hu.nye.progkor.warehouse.converter;

import hu.nye.progkor.warehouse.model.dto.StorageDTO;
import hu.nye.progkor.warehouse.model.dto.WareHouseDTO;
import hu.nye.progkor.warehouse.model.response.StorageResponse;
import hu.nye.progkor.warehouse.model.response.WareHouseResponse;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class WareHouseDtoToResponseConverter implements Converter<WareHouseDTO, WareHouseResponse> {

    private final Converter<StorageDTO, StorageResponse> storageDtoToResponseConverter;

    @Override
    public WareHouseResponse convert(@NonNull final WareHouseDTO source) {
        log.info("Convert WareHouseDTO:{} to WareHouseResponse.", source);
        return new WareHouseResponse(source.id(),
                source.location(),
                convertStorages(source.storages())
        );
    }

    private List<StorageResponse> convertStorages(final List<StorageDTO> storages) {
        return storages.stream()
                .map(storageDtoToResponseConverter::convert)
                .toList();
    }
}
