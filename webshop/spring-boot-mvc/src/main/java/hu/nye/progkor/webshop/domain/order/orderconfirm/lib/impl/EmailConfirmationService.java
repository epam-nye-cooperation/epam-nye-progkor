package hu.nye.progkor.webshop.domain.order.orderconfirm.lib.impl;

import hu.nye.progkor.webshop.domain.order.model.Product;
import hu.nye.progkor.webshop.domain.order.orderconfirm.lib.ConfirmationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class EmailConfirmationService implements ConfirmationService {

    @Override
    public void sendConfirmationMessageAbout(List<Product> products) {
        log.info("Sending an e-mail confirmation about {} products", products);
    }
}
