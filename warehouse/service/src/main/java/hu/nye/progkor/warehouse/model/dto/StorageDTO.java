package hu.nye.progkor.warehouse.model.dto;

import hu.nye.progkor.warehouse.model.FoodStorageType;
import java.util.List;

public record StorageDTO(Long id, FoodStorageType foodStorageType, Long capacity, List<ProductDTO> products, Double reservedArea, Double saturationStatistic) {
}
