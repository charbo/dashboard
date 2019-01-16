package org.dashboard.back.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parameter_id_seq")
    @SequenceGenerator(name="parameter_id_seq", sequenceName = "PARAMETER_ID_SEQ", allocationSize = 100)
    private Long id;
    private String name;
    private String defaultValue;

    @ManyToOne
    @JoinColumn(name="QUERY_ID", nullable=false)
    private Query query;

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

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameter parameter = (Parameter) o;
        return Objects.equals(id, parameter.id) &&
                Objects.equals(name, parameter.name) &&
                Objects.equals(defaultValue, parameter.defaultValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, defaultValue);
    }
}
