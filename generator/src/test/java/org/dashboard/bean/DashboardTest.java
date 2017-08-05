package org.dashboard.bean;

import org.dashboard.bean.component.Component;
import org.dashboard.bean.component.Dataset;
import org.dashboard.bean.component.Option;
import org.dashboard.bean.component.Parameter;
import org.dashboard.bean.header.Css;
import org.dashboard.bean.header.JavaScript;
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
          "<nav class=\"navbar navbar-default navbar-fixed-top\">\n" +
          "    <div class=\"container\">\n" +
          "        <div class=\"navbar-header\">\n" +
          "            <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#myNavbar\">\n" +
          "                <span class=\"icon-bar\"></span>\n" +
          "                <span class=\"icon-bar\"></span>\n" +
          "                <span class=\"icon-bar\"></span>\n" +
          "            </button>\n" +
          "            <a class=\"navbar-brand\" href=\"#myPage\">Logo</a>\n" +
          "        </div>\n" +
          "        <div class=\"collapse navbar-collapse\" id=\"myNavbar\">\n" +
          "            <ul class=\"nav navbar-nav navbar-right\">\n" +
          "                <li><a href='linked'>label</a></div>\n" +
          "            </ul>\n" +
          "        </div>\n" +
          "    </div>\n" +
          "</nav>\n" +
          "\n" +
          "<body id=\"myPage\" data-spy=\"scroll\" data-target=\".navbar\" data-offset=\"60\">\n" +
          "    <div class=\"jumbotron text-center\">\n" +
          "        <h1>title</h1>\n" +
          "        <p>héhéhé</p>\n" +
          "    </div>\n" +
          "\n" +
          "    <div id='line1' class='css'></div><div class='row'><div id='id1' class='css'></div><div id='id2' class=''></div></div></div>\n" +
          "\n" +
          "<div id='line2' class=''></div><div class='row'><div id='id3' class=''></div></div></div>\n" +
          "\n" +
          "<script>\n" +
          "    var components = new Array();\n" +
          "\n" +
          "    var extractparameters = function(component, event) {\n" +
          "        console.log('extractparameters: %s', component.parameters);\n" +
          "        var params = '{\"name\": \"' + component.dataset.name + '\",\"parameters\": [';\n" +
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
          "               url: component.dataset.url,\n" +
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
          "var id1 = new RenderFunction('chartid1', 'id1', ['child1', 'child2'], new Dataset('dataset', 'url'), [new Parameter('name1', 'provider1'), new " +
          "Parameter('name2', 'provider2')], optionsid1);\n" +
          "components['chartid1'] = id1;\n" +
          "const observableid1 = Rx.Observable.fromEvent($('#chartid1'), 'click');\n" +
          "\n" +
          "var optionsid2 = new Array();\n" +
          "\n" +
          "var id2 = new RenderFunction2('chartid2', 'id2', [], '', [], optionsid2);\n" +
          "components['chartid2'] = id2;\n" +
          "const observableid2 = Rx.Observable.fromEvent($('#chartid2'), 'change');\n" +
          "\n" +
          "var optionsid3 = new Array();\n" +
          "\n" +
          "var id3 = new RenderFunction3('chartid3', 'id3', [], '', [], optionsid3);\n" +
          "components['chartid3'] = id3;\n" +
          "\n" +
          "\n" +
          "        const all = Rx.Observable.merge(observableid1, observableid2);\n" +
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
          "    $(document).ready(function(){\n" +
          "  // Add smooth scrolling to all links in navbar + footer link\n" +
          "  $(\".navbar a, footer a[href='#myPage']\").on('click', function(event) {\n" +
          "    // Make sure this.hash has a value before overriding default behavior\n" +
          "    if (this.hash !== \"\") {\n" +
          "      // Prevent default anchor click behavior\n" +
          "      event.preventDefault();\n" +
          "\n" +
          "      // Store hash\n" +
          "      var hash = this.hash;\n" +
          "\n" +
          "      // Using jQuery's animate() method to add smooth page scroll\n" +
          "      // The optional number (900) specifies the number of milliseconds it takes to scroll to the specified area\n" +
          "      $('html, body').animate({\n" +
          "        scrollTop: $(hash).offset().top\n" +
          "      }, 900, function(){\n" +
          "\n" +
          "        // Add hash (#) to URL when done scrolling (default click behavior)\n" +
          "        window.location.hash = hash;\n" +
          "      });\n" +
          "    } // End if\n" +
          "  });\n" +
          "\n" +
          "  $(window).scroll(function() {\n" +
          "    $(\".slideanim\").each(function(){\n" +
          "      var pos = $(this).offset().top;\n" +
          "\n" +
          "      var winTop = $(window).scrollTop();\n" +
          "        if (pos < winTop + 600) {\n" +
          "          $(this).addClass(\"slide\");\n" +
          "        }\n" +
          "    });\n" +
          "  });\n" +
          "})\n" +
          "\n" +
          "</script>\n" +
          "</body>\n" +
          "\n" +
          "</html>\n";

  @Test
  void toHTML() throws IOException {
    Dashboard dashboard = new Dashboard();
    dashboard.setName("name");
    dashboard.setTitle("title");
    dashboard.setDescription("héhéhé");

    dashboard.setJavaScripts(Stream.of(new JavaScript("http://urlPath.js")).collect(Collectors.toList()));
    dashboard.setCss(Stream.of(new Css(".relativeUrl.css")).collect(Collectors.toList()));
    dashboard.setGroups(Stream.of(new Group("linked", "label", 1)).collect(Collectors.toList()));

    Component component1 = new Component();
    component1.setIndex(1);
    component1.setId("id1");
    component1.setRenderFunction("RenderFunction");
    component1.setChilds(Arrays.asList("child1", "child2"));
    component1.setDataset(new Dataset("dataset", "url"));
    component1.setCss("css");
    component1.setEvent("click");

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
    component2.setEvent("change");

    Component component3 = new Component();
    component3.setIndex(3);
    component3.setId("id3");
    component3.setRenderFunction("RenderFunction3");


    Set componentsLine1 = new TreeSet();
    componentsLine1.add(component1);
    componentsLine1.add(component2);

    Line line1 = new Line();
    line1.setIndex(1);
    line1.setId("line1");
    line1.setComponents(componentsLine1);
    line1.setCss("css");

    Set componentLine2 = new TreeSet();
    componentLine2.add(component3);

    Line line2 = new Line();
    line2.setIndex(2);
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