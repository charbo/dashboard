package org.dashboard;

import org.dashboard.bean.Dashboard;
import org.dashboard.generator.JSONParser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

  public static final void main(String[] args) {
    JSONParser jsonParser = new JSONParser();

    if (args.length == 0) {
      useDefaultFile(jsonParser);
    } else {
      String pathToModel = args[0];
      String fileToCreate = args[1];
      String template = args[2];
      System.out.println(pathToModel);
      System.out.println(fileToCreate);
      try (PrintWriter out = new PrintWriter(fileToCreate)) {
        Dashboard dashboard = jsonParser.parseJSon(new File(pathToModel));
        out.println(dashboard.toHTML(template));
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }

  private static void useDefaultFile(JSONParser jsonParser) {
    ClassLoader classLoader = Main.class.getClassLoader();
    try {
      Dashboard dashboard = jsonParser.parseJSon(new File(classLoader.getResource("test1.json").getFile()));
      System.out.println(dashboard.toHTML(null));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
