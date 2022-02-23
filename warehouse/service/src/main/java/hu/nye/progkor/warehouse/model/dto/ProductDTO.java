package hu.nye.progkor.warehouse.model.dto;

import hu.nye.progkor.warehouse.model.FoodStorageType;

public record ProductDTO(Long id, String name, Integer netValue, double size, FoodStorageType foodStorageType, String description) {
}
