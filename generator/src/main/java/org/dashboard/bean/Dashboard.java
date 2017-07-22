package org.dashboard.bean;


import org.dashboard.bean.component.Component;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Dashboard implements Serializable {
  private String name;
  private Integer nbLines;
  private Integer nbMaxColumns;
  private Set<JavaScript> javaScripts = new HashSet<>();
  private Set<Css> css = new HashSet<>();
  private Set<Line> lines = new TreeSet<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getNbLines() {
    return nbLines;
  }

  public void setNbLines(Integer nbLines) {
    this.nbLines = nbLines;
  }

  public Integer getNbMaxColumns() {
    return nbMaxColumns;
  }

  public void setNbMaxColumns(Integer nbMaxColumns) {
    this.nbMaxColumns = nbMaxColumns;
  }

  public Set<JavaScript> getJavaScripts() {
    return javaScripts;
  }

  public void setJavaScripts(Set<JavaScript> javaScripts) {
    this.javaScripts = javaScripts;
  }

  public Set<Css> getCss() {
    return css;
  }

  public void setCss(Set<Css> css) {
    this.css = css;
  }

  public Set<Line> getLines() {
    return lines;
  }

  public void setLines(Set<Line> lines) {
    this.lines = lines;
  }

  public String toHTML() throws IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    File htmlTemplateFile = new File(classLoader.getResource("template.html").getFile());
    String htmlString = new String(Files.readAllBytes(htmlTemplateFile.toPath()));
    htmlString = htmlString.replace("${title}", getName());

    String scripts = javaScripts.stream().map(JavaScript::toHTML).collect(Collectors.joining("\n"));
    htmlString = htmlString.replace("${scripts}", scripts);

    String sCss = css.stream().map(Css::toHTML).collect(Collectors.joining("\n"));
    htmlString = htmlString.replace("${css}", sCss);

    String components = lines.stream().map(line -> line.getComponents()
            .stream().map(Component::toJavaScript)
            .collect(Collectors.joining("\n\n"))).collect(Collectors.joining("\n\n"));

    htmlString = htmlString.replace("${components}", components);

    String listObservables = lines.stream().map(line -> line.getComponents()
            .stream().map(component -> "observable" + component.getId())
            .collect(Collectors.joining(", "))).collect(Collectors.joining(", "));

    htmlString = htmlString.replace("${listObservables}", listObservables);

    htmlString = htmlString.replace("${layout}", lines.stream().map(Line::toHTML)
            .collect(Collectors.joining("\n\n", "<div class='container'>", "</div>")));

    return htmlString;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Dashboard dashboard = (Dashboard) o;

    return getName().equals(dashboard.getName());
  }

  @Override
  public int hashCode() {
    return getName().hashCode();
  }


}
