package org.dashboard.bean;


public class Css extends HeaderElement {
  public Css() {
  }

  public Css(String url) {
    super(url);
  }

  public String toHTML() {
    return "<link rel=\"stylesheet\" type=\"text/css\" href=\"" + getUrl() + "\">";
  }

}
