package org.dashboard.server;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * https://stackoverflow.com/questions/30196292/how-to-get-a-mysql-connection-from-remote-server-in-spring-jdbc
 */
@RestController
public class DatasProvider {

  public static final String QUERIES_PROPERTIESS_FILE_NAME = "queries.properties";

  private Resource queriesFile;

  private Properties queries;

  private JdbcTemplate jdbcTemplate;

  public DatasProvider(JdbcTemplate jdbcTemplate, @Value(value = "classpath:queries.properties") Resource queriesFile) {
    this.jdbcTemplate = jdbcTemplate;
    this.queriesFile = queriesFile;
    initQueries();
    init();
  }

  private void initQueries() {
    try (InputStream stream = queriesFile.getInputStream()) {
      queries = new Properties();
      queries.load(stream);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @RequestMapping(value = "/datas",
          produces = MediaType.APPLICATION_JSON_VALUE,
          method = RequestMethod.POST)
  public List<Data> getDatas(@RequestBody final Query query) {

    String sql = queries.getProperty(query.getName());

    for (Parameter parameter : query.getParameters()) {
      sql = sql.replace(":" + parameter.getName(), parameter.getValue());
    }
    List<Data> datas = jdbcTemplate.query(
            sql,
            (rs, rowNum) -> new Data(rs.getString("key"), rs.getString("value"))
    );

    return datas;
  }


  private void init() {
    jdbcTemplate.execute("DROP TABLE datas IF EXISTS");
    jdbcTemplate.execute("CREATE TABLE datas(" +
            "key VARCHAR(255), value VARCHAR(255))");

    // Split up the array of whole names into an array of first/last names
    List<Object[]> splitUpNames = Arrays.asList("key1 10", "key2 20", "key3 30", "key4 40").stream()
            .map(name -> name.split(" "))
            .collect(Collectors.toList());


    // Uses JdbcTemplate's batchUpdate operation to bulk load data
    jdbcTemplate.batchUpdate("INSERT INTO datas(key, value) VALUES (?,?)", splitUpNames);
  }

}
