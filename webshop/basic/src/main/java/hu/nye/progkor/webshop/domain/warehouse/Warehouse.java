package hu.nye.progkor.webshop.domain.warehouse;

import hu.nye.progkor.webshop.domain.order.Observer;
import hu.nye.progkor.webshop.domain.order.model.Product;

import java.util.List;

public interface Warehouse extends Observer {

    void registerOrderedProducts(List<Product> products);
}
