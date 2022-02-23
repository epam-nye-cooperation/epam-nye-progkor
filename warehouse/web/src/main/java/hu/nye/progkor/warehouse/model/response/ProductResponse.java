package hu.nye.progkor.warehouse.model.response;

import hu.nye.progkor.warehouse.model.FoodStorageType;

public record ProductResponse(Long id, String name, Integer netValue, Double size, FoodStorageType foodStorageType, String description) {
}
