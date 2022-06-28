package io.davidarchanjo.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Import({ 
  DatabaseConfig.EnableDB.class, 
  DatabaseConfig.DisableDB.class 
})
public class DatabaseConfig {

  @Profile("db")
  @EnableAutoConfiguration
  static class EnableDB {
  }

  @Profile("!db")
  @EnableAutoConfiguration(exclude = {
      DataSourceAutoConfiguration.class,
      HibernateJpaAutoConfiguration.class,
      DataSourceTransactionManagerAutoConfiguration.class
  })
  static class DisableDB {
  }
}