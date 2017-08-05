package org.dashboard.bean;

import org.dashboard.bean.component.Component;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Line extends CustomizableItem implements Serializable, Comparable<Line> {
  private String id;
  private Integer index;
  private Set<Component> components = new TreeSet<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getIndex() {
    return index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }

  public Set<Component> getComponents() {
    return components;
  }

  public void setComponents(Set<Component> components) {
    this.components = components;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Line line = (Line) o;

    return getIndex().equals(line.getIndex());
  }

  @Override
  public int hashCode() {
    return getIndex().hashCode();
  }

  @Override
  public int compareTo(Line o) {
    return index - o.index;
  }

  public String toHTML() {
    return components.stream().map(component -> component.toHTML()).collect(Collectors.joining("", getPrefix(), "</div></div>"));
  }

  private String getPrefix() {
    return "<div id='" + id + "' class='" + getCss() + "'><div class='row'>";
  }
}
