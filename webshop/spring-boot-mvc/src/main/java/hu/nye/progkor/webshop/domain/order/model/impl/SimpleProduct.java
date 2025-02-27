package hu.nye.progkor.webshop.domain.order.model.impl;

import hu.nye.progkor.webshop.domain.order.model.Product;
import lombok.Builder;

@Builder
public record SimpleProduct(Long id, String name, double netPrice) implements Product {

}
