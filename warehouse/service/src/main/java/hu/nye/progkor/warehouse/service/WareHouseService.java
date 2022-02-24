package hu.nye.progkor.warehouse.service;

import hu.nye.progkor.warehouse.model.dto.WareHouseDTO;
import java.util.List;

public interface WareHouseService {

    List<WareHouseDTO> getWareHouses();

    WareHouseDTO getWareHouse(Long id);

    void moveProductsToWareHouse(final Long wareHouseId, final Long productId, final Long quantity);
}
