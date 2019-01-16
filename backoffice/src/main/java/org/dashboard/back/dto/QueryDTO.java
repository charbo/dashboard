package org.dashboard.back.dto;

import org.dashboard.back.model.ParameterDTO;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class QueryDTO implements Serializable {

    private String name;
    private String sql;
    private boolean multiSeries;
    private ParameterDTO[] parameters = new ParameterDTO[]{};
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

    public boolean isMultiSeries() {
        return multiSeries;
    }

    public void setMultiSeries(boolean multiSeries) {
        this.multiSeries = multiSeries;
    }

    public ParameterDTO[] getParameters() {
        return parameters;
    }

    public void setParameters(ParameterDTO[] parameters) {
        this.parameters = parameters;
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
                Objects.equals(source, queryDTO.source);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, sql, source);
        result = 31 * result;
        return result;
    }
}
