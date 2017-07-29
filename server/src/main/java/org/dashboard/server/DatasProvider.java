package org.dashboard.server;


import org.dashboard.server.query.Query;
import org.dashboard.server.query.QueryParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * https://stackoverflow.com/questions/30196292/how-to-get-a-mysql-connection-from-remote-server-in-spring-jdbc
 * https://www.ccampo.me/java/spring/2016/02/13/multi-datasource-spring-boot.html
 */
@RestController
public class DatasProvider {

  private Resource queriesFile;

  private Map<String, Query> queries;

  private JdbcTemplate jdbcTemplate;

  public DatasProvider(JdbcTemplate jdbcTemplate, @Value(value = "classpath:queries.json") Resource queriesFile) {
//    DataSource dataSource = null;
//    try {
//      dataSource = DataSourceBuilder.create()
//              .driverClassName("com.mysql.jdbc.Driver")
//              .password("root")
//              .username("root")
//              .url("jdbc:mysql://localhost/sakila")
//              .build();
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
    this.jdbcTemplate = jdbcTemplate;
    this.queriesFile = queriesFile;
    initQueries();
    init();
  }

  private void initQueries() {
    try (InputStream stream = queriesFile.getInputStream()) {
      queries = QueryParser.parseJSon(stream);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @RequestMapping(value = "/datas",
          produces = MediaType.APPLICATION_JSON_VALUE,
          method = RequestMethod.POST)
  public List<DataSet> getDatas(@RequestBody final Request request) {
    List<DataSet> result;
    Query query = queries.get(request.getName());
    if (query.isSimpleSerie()) {
      result = getDataSetForSimpleSerie(request, query);
    } else {
      result = getDataSetForMultiSerie(request, query);
    }
    return result;
  }

  private List<DataSet> getDataSetForSimpleSerie(Request request, Query query) {
    List<Data> datas = new ArrayList<>();

    if (query != null) {
      String sql = query.getSql();
      for (Parameter parameter : request.getParameters()) {
        sql = sql.replace(":" + parameter.getName(), parameter.getValue());
      }
      datas = jdbcTemplate.query(
              sql,
              (rs, rowNum) -> new Data(rs.getString("key"), rs.getString("value"))
      );
    }

    return Collections.singletonList(new DataSet(query.getSeries().iterator().next(), datas));
  }

  private List<DataSet> getDataSetForMultiSerie(Request request, Query query) {
    List<Tuple3> tuples = new ArrayList<>();

    if (query != null) {
      String sql = query.getSql();
      for (Parameter parameter : request.getParameters()) {
        sql = sql.replace(":" + parameter.getName(), parameter.getValue());
      }
      tuples = jdbcTemplate.query(
              sql,
              (rs, rowNum) -> new Tuple3(rs.getString("serie"), rs.getString("key"), rs.getString("value"))
      );
    }

    List<DataSet> result = tuples.stream().collect(Collectors.groupingBy(
            Tuple3::getTwo,
            Collectors.mapping(t -> new Data(t.getOne(), t.getThree()), Collectors.toList())
    )).entrySet().stream().map(entry -> new DataSet(entry.getKey(), entry.getValue())).collect(Collectors.toList());

    return result;
  }

  private final class Tuple3 {
    private String one;
    private String two;
    private String three;

    public Tuple3(String one, String two, String three) {
      this.one = one;
      this.two = two;
      this.three = three;
    }

    public String getOne() {
      return one;
    }

    public String getTwo() {
      return two;
    }

    public String getThree() {
      return three;
    }
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
