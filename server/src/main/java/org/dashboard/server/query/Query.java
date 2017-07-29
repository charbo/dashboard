package org.dashboard.server.query;

import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class Query implements Serializable {
  private String name;
  private String sql;
  private Set<String> series = new TreeSet<>();

  public Query() {
  }

  public Query(String name, String sql, Set<String> series) {
    this.name = name;
    this.sql = sql;
    this.series = series;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSql() {
    return sql;
  }

  public void setSql(String sql) {
    this.sql = sql;
  }

  public Set<String> getSeries() {
    return series;
  }

  public void setSeries(Set<String> series) {
    this.series = series;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Query query = (Query) o;

    return getName().equals(query.getName());
  }

  @Override
  public int hashCode() {
    return getName().hashCode();
  }

  public boolean isSimpleSerie() {
    return CollectionUtils.isNotEmpty(series) && series.size() == 1;
  }
}

