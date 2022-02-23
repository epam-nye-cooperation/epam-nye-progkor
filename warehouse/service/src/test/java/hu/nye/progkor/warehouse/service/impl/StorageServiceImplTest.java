package hu.nye.progkor.warehouse.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import hu.nye.progkor.warehouse.model.FoodStorageType;
import hu.nye.progkor.warehouse.model.Product;
import hu.nye.progkor.warehouse.model.Storage;
import hu.nye.progkor.warehouse.model.exception.NotEnoughWareHouseCapacityException;
import hu.nye.progkor.warehouse.repository.StorageRepository;
import hu.nye.progkor.warehouse.service.StorageService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class StorageServiceImplTest {

    private static final Long PRODUCT_ID = 22L;
    private static final Long STORAGE_ID = 1L;
    private static final Product PRODUCT = new Product(PRODUCT_ID, "Zsákos krumpli", 4999, 0.5, FoodStorageType.NORMAL_FOOD, "25 kg krumpli.");
    private static final List<Product> PRODUCTS = Arrays.asList(PRODUCT, PRODUCT);
    private static final String ERROR_MESSAGE = String.format("Nem áll rendelkezésre elegendő hely a kívánt mennyiségű termékhez. Termék ID:%s, Tároló egység ID:%s", PRODUCT_ID, STORAGE_ID);

    @Mock
    private StorageRepository storageRepository;

    private StorageService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new StorageServiceImpl(storageRepository);
    }

    @Test
    void addProductsToStorageShouldAddProductsWhenStorageHadEnoughCapacity() {
        // given
        final ArrayList<Product> products = new ArrayList<>(PRODUCTS);
        final Storage storage = new Storage(1L, FoodStorageType.NORMAL_FOOD, 10L, products);
        // when
        underTest.addProductsToStorage(storage, PRODUCT, 18L);
        // then
        verify(storageRepository).save(storage);
        assertThat(storage.getProducts(), IsCollectionWithSize.hasSize(20));
    }

    @Test
    void addProductsToStorageShouldNotEnoughWareHouseCapacityExceptionWhenStorageHadNoEnoughCapacity() {
        // given
        final ArrayList<Product> products = new ArrayList<>(PRODUCTS);
        final Storage storage = new Storage(1L, FoodStorageType.NORMAL_FOOD, 10L, products);
        // when - then
        final NotEnoughWareHouseCapacityException actual = assertThrows(NotEnoughWareHouseCapacityException.class, () -> underTest.addProductsToStorage(storage, PRODUCT, 19L));
        assertThat(actual.getMessage(), equalTo(ERROR_MESSAGE));
        assertThat(storage.getProducts(), IsCollectionWithSize.hasSize(2));
    }
}
