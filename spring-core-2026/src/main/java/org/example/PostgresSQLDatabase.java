package org.example;

public class PostgresSQLDatabase implements Database {

  @Override
  public void save(String data) {
    System.out.println("Saving data to PstgresSQL: " + data);
  }
}
