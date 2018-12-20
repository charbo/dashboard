package org.dashboard.server.datas;


import org.dashboard.server.Parameter;
import org.dashboard.server.sources.DataSourceParser;
import org.dashboard.server.sources.Query;
import org.dashboard.server.sources.QueryParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * https://stackoverflow.com/questions/30196292/how-to-get-a-mysql-connection-from-remote-server-in-spring-jdbc
 * https://www.ccampo.me/java/spring/2016/02/13/multi-datasource-spring-boot.html
 */
@RestController
public class DatasProvider {

  private Resource queriesFile;

  private Resource datasourcesFile;

  private Map<String, Query> queries;

  private Map<String, JdbcTemplate> dataSources;

  public DatasProvider(@Value(value = "classpath:queries.json") Resource queriesFile, @Value(value = "classpath:datasources.json") Resource datasourcesFile) {
    this.queriesFile = queriesFile;
    this.datasourcesFile = datasourcesFile;
    init();
  }

  private void init() {
    try (InputStream streamQueries = queriesFile.getInputStream(); InputStream streamDataSources = datasourcesFile.getInputStream();) {
      this.queries = QueryParser.parseJSon(streamQueries);
      this.dataSources = DataSourceParser.parseJSon(streamDataSources);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @RequestMapping(value = "/datas",
          produces = MediaType.APPLICATION_JSON_VALUE,
          method = RequestMethod.POST)
  public List<DataSet> getDatas(@RequestBody final Request request) throws Exception {
    List<DataSet> result;
    Query query = queries.get(request.getName());
    if (query.isSimpleSerie()) {
      result = getDataSetForSimpleSerie(request, query);
    } else {
      result = getDataSetForMultiSerie(request, query);
    }
    return result;
  }

  private List<DataSet> getDataSetForSimpleSerie(Request request, Query query) throws Exception {
    List<Data> datas = new ArrayList<>();

    if (query != null) {
      String sql = query.getSql();
      for (Parameter parameter : request.getParameters()) {
        sql = sql.replace(":" + parameter.getName(), parameter.getValue());
      }
      datas = dataSources.get(query.getSource()).query(
              sql,
              (rs, rowNum) -> new Data(rs.getString("key"), rs.getString("value"))
      );
    }

    return Collections.singletonList(new DataSet(query.getSeries().iterator().next(), datas));
  }

  private List<DataSet> getDataSetForMultiSerie(Request request, Query query) throws Exception {
    List<Tuple3> tuples = new ArrayList<>();

    if (query != null) {
      String sql = query.getSql();
      for (Parameter parameter : request.getParameters()) {
        sql = sql.replace(":" + parameter.getName(), parameter.getValue());
      }
      tuples = dataSources.get(query.getSource()).query(
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


}
