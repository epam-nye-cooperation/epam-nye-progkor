package hu.nye.progkor.warehouse.populator.impl;

import hu.nye.progkor.warehouse.model.FoodStorageType;
import hu.nye.progkor.warehouse.model.Storage;
import hu.nye.progkor.warehouse.populator.DBPopulator;
import hu.nye.progkor.warehouse.repository.StorageRepository;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Order(2)
@Slf4j
public class StorageInitializer implements DBPopulator {

    private static final List<Storage> STORAGES = List.of(
            new Storage(null, FoodStorageType.TO_BE_COOLED_FOOD, 200L, Collections.emptyList()),
            new Storage(null, FoodStorageType.TO_BE_FROZEN_FOOD, 100L, Collections.emptyList()),
            new Storage(null, FoodStorageType.NORMAL_FOOD, 100L, Collections.emptyList()),
            new Storage(null, FoodStorageType.TO_BE_COOLED_FOOD, 50L, Collections.emptyList()),
            new Storage(null, FoodStorageType.TO_BE_FROZEN_FOOD, 50L, Collections.emptyList()),
            new Storage(null, FoodStorageType.NORMAL_FOOD, 120L, Collections.emptyList()));

    private final StorageRepository storageRepository;

    @Override
    public void populateDatabase() {
        log.info("Initialize Storages...");
        storageRepository.saveAll(STORAGES);
        log.info("Finished initialization of Storages.");
    }
}
