package hu.nye.progkor.warehouse.populator.impl;

import hu.nye.progkor.warehouse.model.FoodStorageType;
import hu.nye.progkor.warehouse.model.Product;
import hu.nye.progkor.warehouse.populator.DBPopulator;
import hu.nye.progkor.warehouse.repository.ProductRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Order(1)
public class ProductInitializer implements DBPopulator {

    private static final List<Product> PRODUCTS = List.of(
            new Product(1L, "Fagylalt", 699, 0.5, FoodStorageType.TO_BE_FROZEN_FOOD, "Vanilia és csokoládé ízesítéső fagylalt 1 literes kiszerelésben."),
            new Product(2L, "Narancs", 399, 0.75, FoodStorageType.TO_BE_COOLED_FOOD, "1 kg narancs."),
            new Product(3L, "Friss csirke mell hús", 1399, 0.60, FoodStorageType.TO_BE_COOLED_FOOD, "1 kg friss tanyasi csirkemell hús."),
            new Product(4L, "1 liter 2,6%-os tej", 229, 0.50, FoodStorageType.TO_BE_COOLED_FOOD, "1 kg friss tanyasi csirkemell hús."),
            new Product(5L, "Zsákos földi mogyoró", 39_000, 1.5, FoodStorageType.NORMAL_FOOD, "50 kg-os kiszerelésben."),
            new Product(6L, "Barna bab csomag", 4_999, 0.75, FoodStorageType.NORMAL_FOOD,
                    """
                            5 kg-os kiszerelésben.
                            Csak mi forgalmazzuk!
                            """),
            new Product(7L, "Krumpli", 4_999, 2.5, FoodStorageType.NORMAL_FOOD, "25 kg zsákos krumpli"),
            new Product(8L, "Fagyasztott harcsafilé", 2_390, 0.85, FoodStorageType.TO_BE_COOLED_FOOD, "1 kg fagyasztott afrikai harcsa filé")

    );

    private final ProductRepository productRepository;

    public ProductInitializer(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void populateDatabase() {
        log.info("Initialize Products...");
        productRepository.saveAll(PRODUCTS);
        log.info("Finished initialization of Products.");
    }
}
