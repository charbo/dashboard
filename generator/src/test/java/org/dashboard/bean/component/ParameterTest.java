package org.dashboard.bean.component;

import org.dashboard.bean.component.Parameter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParameterTest {

  public static final String PARAMETER_SCRIPT = "new Parameter('name', 'provider')";

  @Test
  void toJavaScript() {
    Parameter parameter = new Parameter();
    parameter.setName("name");
    parameter.setProvider("provider");

    String result = parameter.toJavaScript();
    assertEquals(PARAMETER_SCRIPT, result);
  }

}