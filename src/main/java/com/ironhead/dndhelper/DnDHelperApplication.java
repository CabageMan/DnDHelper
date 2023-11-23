package com.ironhead.dndhelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DnDHelperApplication {

//  private static final Logger log = LoggerFactory.getLogger(DnDHelperApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(DnDHelperApplication.class, args);
  }
}
