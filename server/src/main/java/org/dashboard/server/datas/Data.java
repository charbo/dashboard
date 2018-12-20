package org.dashboard.server.datas;


import java.io.Serializable;

public class Data implements Serializable {
  private String key;
  private String value;

  public Data(String key, String value) {
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Data data = (Data) o;

    if (!getKey().equals(data.getKey())) return false;
    return getValue().equals(data.getValue());
  }

  @Override
  public int hashCode() {
    int result = getKey().hashCode();
    result = 31 * result + getValue().hashCode();
    return result;
  }
}
