package org.dashboard.bean;

import java.io.Serializable;

public class Group implements Serializable, Comparable<Group> {
  private String linkedId;
  private String label;
  private Integer index;

  public Group() {
  }

  public Group(String linkedId, String label, Integer index) {
    this.linkedId = linkedId;
    this.label = label;
    this.index = index;
  }

  public String getLinkedId() {
    return linkedId;
  }

  public void setLinkedId(String linkedId) {
    this.linkedId = linkedId;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Integer getIndex() {
    return index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Group group = (Group) o;

    if (!getLinkedId().equals(group.getLinkedId())) return false;
    if (!getLabel().equals(group.getLabel())) return false;
    return getIndex().equals(group.getIndex());
  }

  @Override
  public int hashCode() {
    int result = getLinkedId().hashCode();
    result = 31 * result + getLabel().hashCode();
    result = 31 * result + getIndex().hashCode();
    return result;
  }

  @Override
  public int compareTo(Group o) {
    return o.index - index;
  }


  public String toHTML() {
    return "<li><a href='#" + this.linkedId + "'>" + this.label + "</a></li>";
  }
}
