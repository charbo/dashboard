package org.dashboard.back.dto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QueryDTO implements Serializable {

    private String name;
    private String sql;
    private boolean multi;
    private List<ParameterDTO> parameters = new ArrayList<>();
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

    public boolean isMulti() {
        return multi;
    }

    public void setMulti(boolean multi) {
        this.multi = multi;
    }

    //TODO clone
    public List<ParameterDTO> getParameters() {
        return parameters;
    }

    //TODO clone
    public void setParameters(List<ParameterDTO> parameters) {
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

    public void addParameter(ParameterDTO parameter) {
        this.parameters.add(parameter);
    }
}
