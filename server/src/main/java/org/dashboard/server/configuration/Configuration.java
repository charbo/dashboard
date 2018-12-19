package org.dashboard.server.configuration;


import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource(value = "classpath:dashboard.properties", ignoreResourceNotFound=true)
})
@EnableTransactionManagement
public class Configuration {

  @ConfigurationProperties(prefix = "spring.datasource")
  @Bean
  @Primary
  public DataSource dataSource() {
    return DataSourceBuilder.create()
            .build();
  }
}
