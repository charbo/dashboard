package org.dashboard.bean.header;

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
