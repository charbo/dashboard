package org.dashboard.bean.component;

import java.io.Serializable;
import java.text.MessageFormat;

public class Option implements Serializable, Comparable<Option> {
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

    Option option = (Option) o;

    return getName().equals(option.getName());
  }

  @Override
  public int hashCode() {
    return getName().hashCode();
  }

  public String toJavaScript(String parentId) {
    return MessageFormat.format("options{0}[''{1}''] = {2};", parentId, this.name, this.value);
  }

  @Override
  public int compareTo(Option o) {
    if (o == null) {
      return -1;
    }
    return this.name.compareTo(o.name);
  }
}
