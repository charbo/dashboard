package org.dashboard.bean;


import org.apache.commons.lang3.StringUtils;

public abstract class CustomizableItem {
  private String css;

  public String getCss() {
    return css;
  }

  public void setCss(String css) {
    this.css = css;
  }

  protected String getHtmlCss() {
    String result = "";
    if (StringUtils.isNotBlank(css)) {
      result = " " + css;
    }
    return result;
  }
}
