package hu.nye.progkor.helloworld;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public GreetingGenerator greetingGenerator() {
        return new GreetingGenerator();
    }

}
