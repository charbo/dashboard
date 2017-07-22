package org.dashboard.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dashboard.bean.Dashboard;

import java.io.*;

public class JSONParser {

  public Dashboard parseJSon(File file) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(file, Dashboard.class);
  }
}
