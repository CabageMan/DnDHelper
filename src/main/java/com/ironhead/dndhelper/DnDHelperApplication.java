package com.ironhead.dndhelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// Read more about annotation and solution.
// https://stackoverflow.com/questions/51221777/failed-to-configure-a-datasource-url-attribute-is-not-specified-and-no-embedd
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class DnDHelperApplication {
  public static void main(String[] args) {
    SpringApplication.run(DnDHelperApplication.class, args);
  }

}
