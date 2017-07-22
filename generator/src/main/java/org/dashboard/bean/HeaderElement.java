package org.dashboard.bean;

import java.io.Serializable;

public abstract class HeaderElement implements Serializable {
  protected String url;

  public HeaderElement() {
  }

  public HeaderElement(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
