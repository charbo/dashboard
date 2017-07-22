package org.dashboard.bean.component;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class OptionTest {
  @Test
  void toJavaScript() {

    String result = "optionsparentId['name'] = value;";

    Option option = new Option();
    option.setName("name");
    option.setValue("value");

    assertEquals(result, option.toJavaScript("parentId"));
  }

}