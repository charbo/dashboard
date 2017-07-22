package org.dashboard.generator;

import org.apache.commons.collections4.CollectionUtils;
import org.dashboard.bean.component.Component;
import org.dashboard.bean.Dashboard;
import org.dashboard.bean.Line;
import org.dashboard.bean.component.Option;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JSONParserTest {
  @Test
  public void fichier_de_reference() throws IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("dashboard.json").getFile());
    Dashboard dashboard = new JSONParser().parseJSon(file);
    assertNotNull(dashboard);
    assertNotNull(dashboard.getJavaScripts());
    assertEquals(1, dashboard.getJavaScripts().size());
    assertNotNull(dashboard.getCss());
    assertEquals(1, dashboard.getCss().size());
    assertNotNull(dashboard.getLines());
    assertEquals(2, dashboard.getLines().size());
    Iterator<Line> iterator = dashboard.getLines().iterator();
    Line line1 = iterator.next();
    assertNotNull(line1.getCss());
    assertEquals("cssLine1", line1.getCss());
    assertNotNull(line1.getComponents());
    assertEquals(1, line1.getComponents().size());

    Component component = line1.getComponents().iterator().next();
    assertEquals(component.getId(), "component1");
    assertNotNull(component.getRenderFunction());
    assertEquals("renderComponent1", component.getRenderFunction());
    assertNotNull(component.getCss());
    assertEquals("cssComponent1", component.getCss());
    assertEquals("dataset11", component.getDataset());
    assertTrue(CollectionUtils.isNotEmpty(component.getParameters()));
    assertEquals(2, component.getParameters().size());
    assertEquals("name1", component.getParameters().get(0).getName());
    assertEquals("provider1", component.getParameters().get(0).getProvider());

    assertEquals(component.getRenderFunction(), "renderComponent1");
    assertEquals(component.getIndex(), Integer.valueOf(1));
    assertEquals(component.getChilds().size(), 2);
    assertEquals(component.getChilds().get(0), "component2");

    assertNotNull(component.getOptions());
    assertEquals(1, component.getOptions().size());
    Option option = component.getOptions().iterator().next();
    assertEquals("colors", option.getName());
    assertEquals("['#F00', '#0F0', '#00F']", option.getValue());

    Line line2 = iterator.next();
    assertNotNull(line2.getComponents());
    assertEquals(line2.getComponents().size(), 2);
  }

}