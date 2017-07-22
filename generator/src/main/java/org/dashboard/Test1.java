package org.dashboard;

import org.dashboard.bean.Dashboard;
import org.dashboard.generator.JSONParser;

import java.io.File;
import java.io.IOException;

public class Test1 {

  public static final void main(String[] args) {
    JSONParser jsonParser = new JSONParser();
    ClassLoader classLoader = Test1.class.getClassLoader();
    try {
      Dashboard dashboard = jsonParser.parseJSon(new File(classLoader.getResource("test1.json").getFile()));
      System.out.println(dashboard.toHTML());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
