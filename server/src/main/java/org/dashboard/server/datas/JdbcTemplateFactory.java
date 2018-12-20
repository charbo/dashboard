package org.dashboard.server.datas;

import com.zaxxer.hikari.HikariDataSource;
import org.dashboard.server.sources.Source;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JdbcTemplateFactory {

    public static final JdbcTemplate createJdbcTemplate(Source source) {
        DataSource datasource = DataSourceBuilder.create().type(HikariDataSource.class).driverClassName(source.getDriver()).username(source.getUser()).password(source.getPassword()).url(source.getUrl()).build();
        return new JdbcTemplate(datasource);
    }
}
