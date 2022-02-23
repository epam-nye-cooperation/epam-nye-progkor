package hu.nye.progkor.warehouse.model.response;

import hu.nye.progkor.warehouse.model.FoodStorageType;
import java.util.List;

public record StorageResponse(Long id, FoodStorageType foodStorageType, Long capacity, List<ProductResponse> products, Double reservedArea, Double saturationStatistic) {
}
