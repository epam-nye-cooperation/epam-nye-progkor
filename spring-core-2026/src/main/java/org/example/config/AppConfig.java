package org.example.config;

import org.example.Database;
import org.example.MySQLDatabase;
import org.example.PostgresSQLDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.example")
public class AppConfig {

  @Bean
  public Database postgresSQLDatabase() {
    return new PostgresSQLDatabase();
  }

  @Bean
  public Database mySQLDatabase() {
    return new MySQLDatabase();
  }

}
