package com.ironhead.dndhelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration
public class DnDHelperApplication {

  private static final Logger log = LoggerFactory.getLogger(DnDHelperApplication.class);

  public static void main(String[] args) {
//    SpringApplication.run(DnDHelperApplication.class, args);

    SpringApplication.run(DnDHelperApplication.class, args);
  }
}
