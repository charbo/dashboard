package org.dashboard.bean.component;

import java.io.Serializable;

public class Parameter implements Serializable {
  private String name;
  private String provider;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getProvider() {
    return provider;
  }

  public void setProvider(String provider) {
    this.provider = provider;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Parameter parameter = (Parameter) o;

    return getName().equals(parameter.getName());
  }

  @Override
  public int hashCode() {
    return getName().hashCode();
  }

  public String toJavaScript() {
    return "new Parameter('" + name + "', '" + provider + "')";
  }


}
