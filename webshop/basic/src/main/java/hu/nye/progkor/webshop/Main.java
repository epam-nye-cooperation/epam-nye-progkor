package hu.nye.progkor.webshop;

import hu.nye.progkor.webshop.config.AppConfig;
import hu.nye.progkor.webshop.presentation.CommandProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CommandProcessor commandProcessor = context.getBean(CommandProcessor.class);
        commandProcessor.process();
    }
}