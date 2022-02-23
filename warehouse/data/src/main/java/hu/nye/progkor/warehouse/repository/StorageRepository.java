package hu.nye.progkor.warehouse.repository;

import hu.nye.progkor.warehouse.model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<Storage, Long> {
}
