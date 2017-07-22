package org.dashboard.bean;

import java.io.Serializable;

public class JavaScript extends HeaderElement {
  public JavaScript() {
  }

  public JavaScript(String url) {
    super(url);
  }

  public String toHTML() {
    return "<script src=\"" + getUrl() + "\"></script>";
  }
}
