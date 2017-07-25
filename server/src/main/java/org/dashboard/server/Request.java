package org.dashboard.server;

import java.util.List;

public class Request {
  private String name;
  private List<Parameter> parameters;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Parameter> getParameters() {
    return parameters;
  }

  public void setParameters(List<Parameter> parameters) {
    this.parameters = parameters;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Request query = (Request) o;

    return getName().equals(query.getName());
  }

  @Override
  public int hashCode() {
    return getName().hashCode();
  }
}
