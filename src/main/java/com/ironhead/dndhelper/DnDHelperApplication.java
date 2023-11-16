package com.ironhead.dndhelper;

import com.ironhead.dndhelper.sandBox.HelloWorldController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;


@SpringBootApplication
//@EnableJpaRepositories
//@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class DnDHelperApplication {

//  private static final Logger log = LoggerFactory.getLogger(DnDHelperApplication.class);
//  @Bean(name = {"entityManagerFactory"})
//  public LocalSessionFactoryBean sessionFactory() {
//    return new LocalSessionFactoryBean();
//  }

  public static void main(String[] args) {
    SpringApplication.run(DnDHelperApplication.class, args);
  }
}
