package hu.nye.progkor.helloworld;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("hu.nye.progkor");
        GreetingGenerator greetingGenerator = applicationContext.getBean(GreetingGenerator.class);
        System.out.println(greetingGenerator.generateGreeting());
    }

}
