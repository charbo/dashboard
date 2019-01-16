package org.dashboard.back.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Query implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "query_id_seq")
    @SequenceGenerator(name="query_id_seq", sequenceName = "QUERY_ID_SEQ", allocationSize = 100)
    private Long id;
    private String name;
    private String sql;
    private Long sourceId;

    @OneToMany(mappedBy = "query", cascade = CascadeType.ALL)
    private List<Parameter> parameters = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Query query = (Query) o;
        return Objects.equals(name, query.name) &&
                Objects.equals(sql, query.sql) &&
                Objects.equals(sourceId, query.sourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sql, sourceId);
    }

    public void addParameter(Parameter parameter) {
        parameter.setQuery(this);
        this.parameters.add(parameter);
    }
}
