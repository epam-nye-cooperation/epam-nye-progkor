package hu.nye.progkor.warehouse.service.impl;

import hu.nye.progkor.warehouse.model.FoodStorageType;
import hu.nye.progkor.warehouse.model.Product;
import hu.nye.progkor.warehouse.model.Storage;
import hu.nye.progkor.warehouse.model.WareHouse;
import hu.nye.progkor.warehouse.model.dto.WareHouseDTO;
import hu.nye.progkor.warehouse.model.exception.NoStorageException;
import hu.nye.progkor.warehouse.model.exception.NotFoundException;
import hu.nye.progkor.warehouse.repository.ProductRepository;
import hu.nye.progkor.warehouse.repository.WareHouseRepository;
import hu.nye.progkor.warehouse.service.StorageService;
import hu.nye.progkor.warehouse.service.WareHouseService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WareHouseServiceImpl implements WareHouseService {

    private static final String MISS_TYPE_STORAGE_ERROR_MESSAGE = "Nincs megfelelő tároló egysége a raktárnak ehhez a tárolás típushoz:%s, Termék ID:%s, Raktár ID:%s";
    private static final String NO_WAREHOUSE_ERROR_MESSAGE = "Nem létezik a Raktár, ID:";
    private static final String NO_PRODUCT_ERROR_MESSAGE = "Nem létezik a Termék, ID:";

    private final WareHouseRepository wareHouseRepository;
    private final ProductRepository productRepository;
    private final StorageService storageService;
    private final Converter<WareHouse, WareHouseDTO> wareHouseEntityToDtoConverter;

    @Override
    public List<WareHouseDTO> getWareHouses() {
        return wareHouseRepository.findAll().stream()
                .map(wareHouseEntityToDtoConverter::convert)
                .toList();
    }

    public boolean isProductStored(final long id) {
        return getWareHouses().stream()
                .flatMap(wareHouseDTO -> wareHouseDTO.storages().stream())
                .flatMap(storageDTO -> storageDTO.products().stream())
                .anyMatch(productDTO -> productDTO.id() == id);
    }

    @Override
    public WareHouseDTO getWareHouse(final Long id) {
        return wareHouseRepository.findById(id)
                .map(wareHouseEntityToDtoConverter::convert)
                .orElseThrow(() -> new NotFoundException(NO_WAREHOUSE_ERROR_MESSAGE + id));
    }

    @Override
    public void moveProductsToWareHouse(final Long wareHouseId, final Long productId, final Long quantity) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(NO_PRODUCT_ERROR_MESSAGE + productId));
        final FoodStorageType foodStorageType = product.getSuggestedFoodStorageType();
        final WareHouse wareHouse = wareHouseRepository.findById(wareHouseId)
                .orElseThrow(() -> new NotFoundException(NO_WAREHOUSE_ERROR_MESSAGE + wareHouseId));
        final Storage correspondingStorage = getCorrespondingStorage(wareHouse, foodStorageType)
                .orElseThrow(() -> new NoStorageException(String.format(MISS_TYPE_STORAGE_ERROR_MESSAGE, foodStorageType, productId, wareHouseId)));
        storageService.addProductsToStorage(correspondingStorage, product, quantity);
    }

    private Optional<Storage> getCorrespondingStorage(final WareHouse wareHouse, final FoodStorageType foodStorageType) {
        return wareHouse.getStorages().stream()
                .filter(storage -> storage.getFoodStorageType() == foodStorageType)
                .findFirst();
    }
}
