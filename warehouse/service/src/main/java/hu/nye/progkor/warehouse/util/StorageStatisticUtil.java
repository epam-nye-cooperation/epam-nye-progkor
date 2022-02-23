package hu.nye.progkor.warehouse.util;

import hu.nye.progkor.warehouse.model.Product;
import hu.nye.progkor.warehouse.model.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StorageStatisticUtil {

    public Double aggregateReservedCapacity(final Storage storage) {
        log.info("Aggregate reserved capacity of Storage:{}", storage);
        return storage.getProducts().stream()
                .mapToDouble(Product::getSize)
                .sum();
    }

    public Double calculateReservedCapacityPercentage(final Storage storage) {
        log.info("Calculate percentage of reserved capacity of Storage:{}", storage);
        final Double aggregatedReservedCapacityOfProducts = this.aggregateReservedCapacity(storage);
        return Math.floor((aggregatedReservedCapacityOfProducts * 100) / storage.getCapacity());
    }
}
