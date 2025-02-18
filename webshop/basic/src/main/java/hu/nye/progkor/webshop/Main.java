package hu.nye.progkor.webshop;

import hu.nye.progkor.webshop.presentation.CommandProcessor;

public class Main {
    public static void main(String[] args) {
        CommandProcessor commandProcessor = new CommandProcessor();
        commandProcessor.process();
    }
}