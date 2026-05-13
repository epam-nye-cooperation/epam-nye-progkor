package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@org.springframework.stereotype.Service
public class Service {

  private Database database;

  @Autowired
  public Service(@Qualifier("mySQLDatabase") Database database) {
    this.database = database;
  }

  public void saveSomething(String data) {
    database.save(data);
  }

}
