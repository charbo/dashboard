package org.dashboard.back.model;

import javax.persistence.*;
import java.util.Objects;

public class ParameterDTO implements java.io.Serializable {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
