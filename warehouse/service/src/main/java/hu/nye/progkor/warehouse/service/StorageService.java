package hu.nye.progkor.warehouse.service;

import hu.nye.progkor.warehouse.model.Product;
import hu.nye.progkor.warehouse.model.Storage;

public interface StorageService {

    void addProductsToStorage(Storage storage, Product product, Long quantity);
}
