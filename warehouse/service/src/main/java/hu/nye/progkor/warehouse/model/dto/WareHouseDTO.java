package hu.nye.progkor.warehouse.model.dto;

import java.util.List;


public record WareHouseDTO(Long id, String location, List<StorageDTO> storages) {
}
