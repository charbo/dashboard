package org.dashboard.server.configuration;


import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource(value = "classpath:dashboard.properties", ignoreResourceNotFound=true)
})
public class DataBaseConfiguration {

  @ConfigurationProperties(prefix = "spring.datasource")
  @Bean
  @Primary
  public DataSource dataSource() {
    return DataSourceBuilder.create()
            .build();
  }
}
