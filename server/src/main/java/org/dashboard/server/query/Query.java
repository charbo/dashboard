package org.dashboard.server.query;

import java.io.Serializable;

public class Query implements Serializable {
    private String name;
    private String sql;
    private String label;

    public Query() {
    }

    public Query(String name, String sql, String label) {
        this.name = name;
        this.sql = sql;
        this.label = label;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
}

