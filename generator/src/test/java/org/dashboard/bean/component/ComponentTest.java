package org.dashboard.bean.component;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ComponentTest {

  private static final String TWO_CHILDS = "var optionsid = new Array();\n" +
          "\n" +
          "var id = new RenderFunction('chartid', 'id', ['child1', 'child2'], '', [], optionsid);\n" +
          "components['chartid'] = id;\n" +
          "const observableid = Rx.Observable.fromEvent($('#chartid'), 'click');";

  private static final String DATASET = "var optionsid = new Array();\n" +
          "\n" +
          "var id = new RenderFunction('chartid', 'id', ['child1', 'child2'], new Dataset('dataset', 'url'), [], optionsid);\n" +
          "components['chartid'] = id;\n" +
          "const observableid = Rx.Observable.fromEvent($('#chartid'), 'click');";

  private static  final String NO_CHILD = "var optionsid = new Array();\n" +
          "\n" +
          "var id = new RenderFunction('chartid', 'id', [], '', [], optionsid);\n" +
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
    component.setDataset(new Dataset("dataset", "url"));

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
  void html_avec_css() {
    Component component = new Component();
    component.setId("id");
    component.setCss("col-md-3");

    String result = component.toHTML();
    assertEquals(DIV_ID_ID_CLASS_COL_MD_4_DIV, result);
  }

}