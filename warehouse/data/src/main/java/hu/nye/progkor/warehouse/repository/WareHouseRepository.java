package hu.nye.progkor.warehouse.repository;

import hu.nye.progkor.warehouse.model.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WareHouseRepository extends JpaRepository<WareHouse, Long> {
}
