package org.dashboard.bean.component;

import org.dashboard.bean.component.Component;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ComponentTest {

  private static final String TWO_CHILDS = "var id = new RenderFunction('chartid', 'id', ['child1', 'child2'], '', []);\n" +
          "components['chartid'] = id;\n" +
          "const observableid = Rx.Observable.fromEvent($('#chartid'), 'click');";

  private static final String DATASET = "var id = new RenderFunction('chartid', 'id', ['child1', 'child2'], 'dataset', []);\n" +
          "components['chartid'] = id;\n" +
          "const observableid = Rx.Observable.fromEvent($('#chartid'), 'click');";

  private static  final String NO_CHILD = "var id = new RenderFunction('chartid', 'id', [], '', []);\n" +
          "components['chartid'] = id;\n" +
          "const observableid = Rx.Observable.fromEvent($('#chartid'), 'change');";

  public static final String DIV_ID_ID_CLASS_COL_MD_4_DIV = "<div id='id' class='col-md-3'></div>";

  @Test
  void two_childs() {
    Component component = new Component();
    component.setId("id");
    component.setEvent("click");
    component.setRenderFunction("RenderFunction");
    component.setChilds(Arrays.asList("child1", "child2"));

    String result = component.toJavaScript();
    assertEquals(TWO_CHILDS, result);
  }
  @Test
  void two_childs_and_datgaset_and_parameters() {
    Component component = new Component();
    component.setId("id");
    component.setEvent("click");
    component.setRenderFunction("RenderFunction");
    component.setChilds(Arrays.asList("child1", "child2"));
    component.setDataset("dataset");

    String result = component.toJavaScript();
    assertEquals(DATASET, result);
  }

  @Test
  void no_child() {
    Component component = new Component();
    component.setId("id");
    component.setEvent("change");
    component.setRenderFunction("RenderFunction");

    String result = component.toJavaScript();
    assertEquals(NO_CHILD, result);
  }

  @Test
  void html_avec_4_columns() {
    Component component = new Component();
    component.setId("id");

    String result = component.toHTML(4);
    assertEquals(DIV_ID_ID_CLASS_COL_MD_4_DIV, result);
  }

}