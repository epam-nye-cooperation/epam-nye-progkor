package hu.nye.progkor.warehouse.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.converter.Converter;

class WareHouseServiceImplTest {

    private static final Long WARE_HOUSE_ID = 11L;
    private static final Long PRODUCT_ID = 22L;
    public static final long PRODUCT_QUANTITY = 250L;
    private static final String MISS_TYPE_STORAGE_ERROR_MESSAGE = "Nincs megfelelő tároló egysége a raktárnak ehhez a tárolás típushoz:"
            + FoodStorageType.TO_BE_FROZEN_FOOD + ", Termék ID:" + PRODUCT_ID + ", Raktár ID:" + WARE_HOUSE_ID;
    private static final String NO_WAREHOUSE_ERROR_MESSAGE = "Nem létezik a Raktár, ID:" + WARE_HOUSE_ID;
    private static final String NO_PRODUCT_ERROR_MESSAGE = "Nem létezik a Termék, ID:" + PRODUCT_ID;

    @Mock
    private WareHouseRepository wareHouseRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private StorageService storageService;
    @Mock
    private Converter<WareHouse, WareHouseDTO> wareHouseEntityToDtoConverter;
    @Mock
    private WareHouse wareHouse;
    @Mock
    private Product product;
    @Mock
    private Storage firstStorage;
    @Mock
    private Storage secondStorage;

    private WareHouseService underTest;
    private List<WareHouse> wareHouses;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new WareHouseServiceImpl(wareHouseRepository, productRepository, storageService, wareHouseEntityToDtoConverter);
        wareHouses = List.of(wareHouse);
    }

    @Test
    void getWareHousesShouldDelegateFindAllWhenProvidedWareHouses() {
        // given
        given(wareHouseRepository.findAll()).willReturn(wareHouses);
        // when
        underTest.getWareHouses();
        // then
        verify(wareHouseRepository).findAll();
        verify(wareHouseEntityToDtoConverter).convert(wareHouse);
    }

    @Test
    void getWareHousesShouldNotDelegateFindAllWhenProvidedNoWareHouses() {
        // given
        given(wareHouseRepository.findAll()).willReturn(Collections.emptyList());
        // when
        underTest.getWareHouses();
        // then
        verify(wareHouseRepository).findAll();
        verify(wareHouseEntityToDtoConverter, Mockito.never()).convert(any(WareHouse.class));
    }

    @Test
    void moveProductsToWareHouseShouldDelegateAddProductsToStorageWhenGivenValidParameters() {
        // given
        given(wareHouseRepository.findById(WARE_HOUSE_ID)).willReturn(Optional.of(wareHouse));
        given(productRepository.findById(PRODUCT_ID)).willReturn(Optional.of(product));
        given(wareHouse.getId()).willReturn(WARE_HOUSE_ID);
        given(wareHouse.getStorages()).willReturn(List.of(firstStorage, secondStorage));
        given(firstStorage.getFoodStorageType()).willReturn(FoodStorageType.NORMAL_FOOD);
        given(secondStorage.getFoodStorageType()).willReturn(FoodStorageType.TO_BE_COOLED_FOOD);
        given(product.getSuggestedFoodStorageType()).willReturn(FoodStorageType.NORMAL_FOOD);
        // when
        underTest.moveProductsToWareHouse(WARE_HOUSE_ID, PRODUCT_ID, PRODUCT_QUANTITY);
        // then
        verify(storageService).addProductsToStorage(firstStorage, product, PRODUCT_QUANTITY);
    }

    @Test
    void moveProductsToWareHouseShouldThrowNoStorageExceptionWhenWareHouseHadNoCorrespondingStorage() {
        // given
        given(wareHouseRepository.findById(WARE_HOUSE_ID)).willReturn(Optional.of(wareHouse));
        given(productRepository.findById(PRODUCT_ID)).willReturn(Optional.of(product));
        given(wareHouse.getId()).willReturn(WARE_HOUSE_ID);
        given(wareHouse.getStorages()).willReturn(List.of(firstStorage, secondStorage));
        given(firstStorage.getFoodStorageType()).willReturn(FoodStorageType.NORMAL_FOOD);
        given(secondStorage.getFoodStorageType()).willReturn(FoodStorageType.TO_BE_COOLED_FOOD);
        given(product.getSuggestedFoodStorageType()).willReturn(FoodStorageType.TO_BE_FROZEN_FOOD);
        // when - then
        final NoStorageException actual = assertThrows(NoStorageException.class, () -> underTest.moveProductsToWareHouse(WARE_HOUSE_ID, PRODUCT_ID, PRODUCT_QUANTITY));
        assertThat(actual.getMessage(), equalTo(MISS_TYPE_STORAGE_ERROR_MESSAGE));
    }

    @Test
    void moveProductsToWareHouseShouldThrowNotFoundExceptionWhenWareHouseIsNotExisted() {
        // given
        given(wareHouseRepository.findById(WARE_HOUSE_ID)).willReturn(Optional.empty());
        given(productRepository.findById(PRODUCT_ID)).willReturn(Optional.of(product));
        // when - then
        final NotFoundException actual = assertThrows(NotFoundException.class, () -> underTest.moveProductsToWareHouse(WARE_HOUSE_ID, PRODUCT_ID, PRODUCT_QUANTITY));
        assertThat(actual.getMessage(), equalTo(NO_WAREHOUSE_ERROR_MESSAGE));
    }

    @Test
    void moveProductsToWareHouseShouldThrowNotFoundExceptionWhenProductIsNotExisted() {
        // given
        given(productRepository.findById(PRODUCT_ID)).willReturn(Optional.empty());
        // when - then
        final NotFoundException actual = assertThrows(NotFoundException.class, () -> underTest.moveProductsToWareHouse(WARE_HOUSE_ID, PRODUCT_ID, PRODUCT_QUANTITY));
        assertThat(actual.getMessage(), equalTo(NO_PRODUCT_ERROR_MESSAGE));
    }
}
