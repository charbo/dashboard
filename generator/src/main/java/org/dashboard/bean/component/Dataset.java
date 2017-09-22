package org.dashboard.bean.component;

import java.io.Serializable;

public class Dataset implements Serializable{
  private String name;
  private String url;

  public Dataset() {
  }

  public Dataset(String name, String url) {
    this.name = name;
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Dataset dataset = (Dataset) o;

    return getName().equals(dataset.getName());
  }

  @Override
  public int hashCode() {
    return getName().hashCode();
  }

  public String toJavaScript() {
    return "new Dataset('" + name + "', '" + url + "')";
  }
}
