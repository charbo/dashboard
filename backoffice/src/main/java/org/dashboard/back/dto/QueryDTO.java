package org.dashboard.back.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class QueryDTO implements Serializable {

    private String name;
    private String sql;
    private String[] series;
    private String source;

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

    public String[] getSeries() {
        return series;
    }

    public void setSeries(String[] series) {
        this.series = series;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryDTO queryDTO = (QueryDTO) o;
        return Objects.equals(name, queryDTO.name) &&
                Objects.equals(sql, queryDTO.sql) &&
                Arrays.equals(series, queryDTO.series) &&
                Objects.equals(source, queryDTO.source);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, sql, source);
        result = 31 * result + Arrays.hashCode(series);
        return result;
    }
}
