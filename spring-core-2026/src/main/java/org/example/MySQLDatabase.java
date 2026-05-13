package org.example;

public class MySQLDatabase implements Database {

  @Override
  public void save(String data) {
    System.out.println("Saving data to MySQL: " + data);
  }
}
