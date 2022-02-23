package hu.nye.progkor.warehouse.service.impl;

import hu.nye.progkor.warehouse.model.Product;
import hu.nye.progkor.warehouse.model.Storage;
import hu.nye.progkor.warehouse.model.exception.NotEnoughWareHouseCapacityException;
import hu.nye.progkor.warehouse.repository.StorageRepository;
import hu.nye.progkor.warehouse.service.StorageService;
import java.util.stream.LongStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageServiceImpl implements StorageService {

    private final StorageRepository storageRepository;

    @Override
    public void addProductsToStorage(final Storage storage, final Product product, final Long quantity) {
        log.info("Add {} pieces Product:{} to Storage:{}.", quantity, product, storage);
        if (doesStorageHaveEnoughCapacity(storage, product.getSize(), quantity)) {
            LongStream.range(0, quantity)
                    .forEach(counter -> storage.addProduct(product));
            storageRepository.save(storage);
        } else {
            throw new NotEnoughWareHouseCapacityException(String.format("Nem áll rendelkezésre elegendő hely a kívánt mennyiségű termékhez. Termék ID:%s, Tároló egység ID:%s", product.getId(), storage.getId()));
        }
    }

    private boolean doesStorageHaveEnoughCapacity(final Storage storage, final Double size, final Long quantity) {
        final Long maxCapacity = storage.getCapacity();
        final double reservedCapacity = storage.getProducts().stream()
                .mapToDouble(Product::getSize)
                .sum();
        final double freeCapacity = maxCapacity - reservedCapacity;
        final double requiredExtraCapacity = size * quantity;
        return freeCapacity >= requiredExtraCapacity;
    }
}
