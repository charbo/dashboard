package org.dashboard.bean;

import org.dashboard.bean.component.Component;
import org.dashboard.bean.component.Option;
import org.dashboard.bean.component.Parameter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class DashboardTest {
  String result = "<!doctype html>\n" +
          "<html>\n" +
          "\n" +
          "<head>\n" +
          "    <meta charset=\"UTF-8\">\n" +
          "    <title>name</title>\n" +
          "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
          "    <link rel=\"stylesheet\" type=\"text/css\" href=\".relativeUrl.css\">\n" +
          "    <script src=\"http://urlPath.js\"></script>\n" +
          "</head>\n" +
          "\n" +
          "<body>\n" +
          "<div class='container'><div class='row css'><div id='id1' class='col-md-6 css'></div><div id='id2' class='col-md-6'></div></div>\n" +
          "\n" +
          "<div class='row'><div id='id3' class='col-md-12'></div></div></div>\n" +
          "\n" +
          "<script>\n" +
          "    var components = new Array();\n" +
          "\n" +
          "    var extractparameters = function(component, event) {\n" +
          "        console.log('extractparameters: %s', component.parameters);\n" +
          "        var params = '{\"name\": \"' + component.dataset + '\",\"parameters\": [';\n" +
          "        var oParam = new Array();\n" +
          "        for (index in component.parameters) {\n" +
          "            var parameter = component.parameters[index];\n" +
          "            var provider = components['chart' + parameter.provider];\n" +
          "            var value = provider.selectedValue(event);\n" +
          "            oParam[oParam.length] = '{\"name\" : \"' + parameter.name + '\", \"value\" : \"' + value + '\"}';\n" +
          "        }\n" +
          "        params += oParam.join(\",\");\n" +
          "        params +=']}';\n" +
          "\n" +
          "\n" +
          "        return params;\n" +
          "    };\n" +
          "\n" +
          "    var callServer = function(name, event) {\n" +
          "        var component = components['chart' + name];\n" +
          "\n" +
          "        var params = extractparameters(component, event);\n" +
          "        $.ajax({\n" +
          "               type: \"POST\",\n" +
          "               url: \"http://localhost:9090/datas\",\n" +
          "               contentType: 'application/json; charset=utf-8',\n" +
          "               data: params,\n" +
          "               success: function(resp){\n" +
          "                   // we have the response\n" +
          "                    component.refresh(resp);\n" +
          "                },\n" +
          "               error: function(e){\n" +
          "                 console.log('unable to retreive datas ' + e);\n" +
          "               }\n" +
          "             });\n" +
          "    }\n" +
          "\n" +
          "    window.onload = function() {\n" +
          "        var optionsid1 = new Array();\n" +
          "optionsid1['name'] = ['value1', 'value2'];\n" +
          "var id1 = new RenderFunction('chartid1', 'id1', ['child1', 'child2'], 'dataset', [new Parameter('name1', 'provider1'), new Parameter('name2', " +
          "'provider2')], optionsid1);\n" +
          "components['chartid1'] = id1;\n" +
          "const observableid1 = Rx.Observable.fromEvent($('#chartid1'), 'null');\n" +
          "\n" +
          "var optionsid2 = new Array();\n" +
          "\n" +
          "var id2 = new RenderFunction2('chartid2', 'id2', [], '', [], optionsid2);\n" +
          "components['chartid2'] = id2;\n" +
          "const observableid2 = Rx.Observable.fromEvent($('#chartid2'), 'null');\n" +
          "\n" +
          "var optionsid3 = new Array();\n" +
          "\n" +
          "var id3 = new RenderFunction3('chartid3', 'id3', [], '', [], optionsid3);\n" +
          "components['chartid3'] = id3;\n" +
          "const observableid3 = Rx.Observable.fromEvent($('#chartid3'), 'null');\n" +
          "\n" +
          "        const all = Rx.Observable.merge(observableid1, observableid2, observableid3);\n" +
          "\n" +
          "        all.subscribe(function (event) {\n" +
          "           components[event.currentTarget.id].childs.forEach(function(name){console.log(\"call server for \" + name);callServer(name, event)});\n" +
          "          },\n" +
          "          function (err) {\n" +
          "            console.log('Error: %s', err);\n" +
          "          },\n" +
          "          function () {\n" +
          "            console.log('Completed');\n" +
          "          });\n" +
          "    };\n" +
          "\n" +
          "</script>\n" +
          "</body>\n" +
          "\n" +
          "</html>\n";

  @Test
  void toHTML() throws IOException {
    Dashboard dashboard = new Dashboard();
    dashboard.setName("name");

    dashboard.setJavaScripts(Stream.of(new JavaScript("http://urlPath.js")).collect(Collectors.toSet()));
    dashboard.setCss(Stream.of(new Css(".relativeUrl.css")).collect(Collectors.toSet()));

    Component component1 = new Component();
    component1.setIndex(1);
    component1.setId("id1");
    component1.setRenderFunction("RenderFunction");
    component1.setChilds(Arrays.asList("child1", "child2"));
    component1.setDataset("dataset");
    component1.setCss("css");

    Parameter parameter1 = new Parameter();
    parameter1.setName("name1");
    parameter1.setProvider("provider1");

    Parameter parameter2 = new Parameter();
    parameter2.setName("name2");
    parameter2.setProvider("provider2");

    component1.setParameters(Arrays.asList(parameter1, parameter2));

    Set options = new TreeSet();
    Option option = new Option();
    option.setName("name");
    option.setValue("['value1', 'value2']");
    options.add(option);
    component1.setOptions(options);

    Component component2 = new Component();
    component2.setIndex(2);
    component2.setId("id2");
    component2.setRenderFunction("RenderFunction2");

    Component component3 = new Component();
    component3.setIndex(3);
    component3.setId("id3");
    component3.setRenderFunction("RenderFunction3");


    Set componentsLine1 = new TreeSet();
    componentsLine1.add(component1);
    componentsLine1.add(component2);

    Line line1 = new Line();
    line1.setIndex(1);
    line1.setNbColumns(2);
    line1.setId("line1");
    line1.setComponents(componentsLine1);
    line1.setCss("css");

    Set componentLine2 = new TreeSet();
    componentLine2.add(component3);

    Line line2 = new Line();
    line2.setIndex(2);
    line2.setNbColumns(1);
    line2.setId("line2");
    line2.setComponents(componentLine2);

    Set lines = new TreeSet();
    lines.add(line1);
    lines.add(line2);

    dashboard.setLines(lines);

    String html = dashboard.toHTML();
    assertNotNull(html);
    assertEquals(result, html);


  }

}