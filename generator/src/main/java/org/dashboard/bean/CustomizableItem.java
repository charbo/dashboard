package org.dashboard.bean;


import org.apache.commons.lang3.StringUtils;

public abstract class CustomizableItem {
  private String css = "";

  public String getCss() {
    return css != null ? css : "cdefault";
  }

  public void setCss(String css) {
    this.css = css;
  }

}
