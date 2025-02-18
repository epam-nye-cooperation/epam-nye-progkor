package hu.nye.progkor.webshop.presentation;

import hu.nye.progkor.webshop.domain.cart.ShoppingCartService;
import hu.nye.progkor.webshop.domain.cart.impl.ShoppingCartServiceImpl;
import hu.nye.progkor.webshop.domain.exception.NoSuchProductException;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class CommandProcessor {

    private final ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();

    public void process() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome! Enter a command:");

        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting program...");
                running = false;
            } else if (input.matches("add product .+")) {
                handleAddProduct(input);
            } else if (input.equalsIgnoreCase("order cart")) {
                handleOrderCart();
            } else {
                System.out.println("Unknown command. Try again.");
            }
        }
        scanner.close();
    }

    private void handleAddProduct(String input) {
        Pattern pattern = Pattern.compile("add product (.+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            String productName = matcher.group(1);
            try {
                shoppingCartService.addProduct(productName);
                System.out.println("Product added: " + productName);
            } catch (NoSuchProductException e) {
                System.out.println("Product not found: " + productName);
            }
        }
    }

    private void handleOrderCart() {
        shoppingCartService.order();
        System.out.println("Order placed successfully.");
    }
}
