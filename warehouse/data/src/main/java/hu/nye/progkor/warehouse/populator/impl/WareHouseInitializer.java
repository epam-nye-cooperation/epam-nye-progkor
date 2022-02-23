package hu.nye.progkor.warehouse.populator.impl;

import hu.nye.progkor.warehouse.model.Storage;
import hu.nye.progkor.warehouse.model.WareHouse;
import hu.nye.progkor.warehouse.populator.DBPopulator;
import hu.nye.progkor.warehouse.repository.StorageRepository;
import hu.nye.progkor.warehouse.repository.WareHouseRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Order(3)
@Slf4j
public class WareHouseInitializer implements DBPopulator {

    private final WareHouseRepository wareHouseRepository;
    private final StorageRepository storageRepository;

    @Override
    public void populateDatabase() {
        log.info("Initialize WareHouses...");
        final List<Storage> storages = storageRepository.findAll();
        log.info("Storages:{}", storages);
        final List<WareHouse> wareHouses = List.of(
                new WareHouse(1L,
                        "Budapest",
                        storages.subList(0, 3)
                ),
                new WareHouse(2L, "Nyíregyháza",
                        storages.subList(3, 5)
                ),
                new WareHouse(3L, "Szeged",
                        storages.subList(5, 6)
                )
        );
        wareHouseRepository.saveAll(wareHouses);
        log.info("Finished initialization of WareHouses.");
    }
}
