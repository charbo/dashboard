package org.dashboard.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Function implements Serializable {
  private String name;
  private List<String> arguments = new ArrayList<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getArguments() {
    return arguments;
  }

  public void setArguments(List<String> arguments) {
    this.arguments = arguments;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Function function = (Function) o;

    return getName().equals(function.getName());
  }

  @Override
  public int hashCode() {
    return getName().hashCode();
  }
}
