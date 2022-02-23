package hu.nye.progkor.warehouse.model.response;

import java.util.List;

public record WareHouseResponse(Long id, String location, List<StorageResponse> storages) {
}
