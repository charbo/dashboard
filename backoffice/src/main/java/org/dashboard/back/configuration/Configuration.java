package org.dashboard.back.configuration;


import com.sun.org.apache.xpath.internal.operations.String;
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
        String[] toto = new String[]{};
        return DataSourceBuilder.create()
                .build();
    }

}
