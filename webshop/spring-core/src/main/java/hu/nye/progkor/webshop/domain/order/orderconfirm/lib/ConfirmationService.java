package hu.nye.progkor.webshop.domain.order.orderconfirm.lib;

import hu.nye.progkor.webshop.domain.order.model.Product;

import java.util.List;

public interface ConfirmationService {

    void sendConfirmationMessageAbout(List<Product> products);
}
