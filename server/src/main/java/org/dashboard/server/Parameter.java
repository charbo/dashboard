package org.dashboard.server;

public class Parameter {
  private String name;
  private String value;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Parameter parameter = (Parameter) o;

    if (!getName().equals(parameter.getName())) return false;
    return getValue().equals(parameter.getValue());
  }

  @Override
  public int hashCode() {
    int result = getName().hashCode();
    result = 31 * result + getValue().hashCode();
    return result;
  }
}
