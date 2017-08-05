package org.dashboard.bean;


import org.dashboard.bean.component.Component;
import org.dashboard.bean.header.Css;
import org.dashboard.bean.header.JavaScript;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class Dashboard implements Serializable {
  private String name;
  private String title;
  private String description;
  private List<JavaScript> javaScripts = new ArrayList<>();
  private List<Css> css = new ArrayList<>();
  private Set<Line> lines = new TreeSet<>();
  private List<Group> groups = new ArrayList<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title != null ? title : "";
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description != null ? description : "";
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<JavaScript> getJavaScripts() {
    return javaScripts;
  }

  public void setJavaScripts(List<JavaScript> javaScripts) {
    this.javaScripts = javaScripts;
  }

  public List<Css> getCss() {
    return css;
  }

  public void setCss(List<Css> css) {
    this.css = css;
  }

  public Set<Line> getLines() {
    return lines;
  }

  public void setLines(Set<Line> lines) {
    this.lines = lines;
  }

  public List<Group> getGroups() {
    return groups;
  }

  public void setGroups(List<Group> groups) {
    this.groups = groups;
  }

  public String toHTML(String template) throws IOException {
    if (template == null) {
      ClassLoader classLoader = getClass().getClassLoader();
      template = classLoader.getResource("template.html").getFile();
    }
    File htmlTemplateFile = new File(template);
    String htmlString = new String(Files.readAllBytes(htmlTemplateFile.toPath()));
    htmlString = htmlString.replace("${name}", getName());
    htmlString = htmlString.replace("${title}", getTitle());
    htmlString = htmlString.replace("${description}", getDescription());

    String scripts = javaScripts.stream().map(JavaScript::toHTML).collect(Collectors.joining("\n"));
    htmlString = htmlString.replace("${scripts}", scripts);

    String sCss = css.stream().map(Css::toHTML).collect(Collectors.joining("\n"));
    htmlString = htmlString.replace("${css}", sCss);

    String components = lines.stream().map(line -> line.getComponents()
            .stream().filter(Component::isComplexeComponent).map(Component::toJavaScript)
            .collect(Collectors.joining("\n\n"))).collect(Collectors.joining("\n\n"));

    htmlString = htmlString.replace("${components}", components);



    String listObservables = lines.stream().map(line -> line.getComponents()).flatMap(c -> c.stream())
            .filter(Component::isObservable).map(component -> "observable" + component.getId())
            .collect(Collectors.joining(", "));

    htmlString = htmlString.replace("${listObservables}", listObservables);

    htmlString = htmlString.replace("${groups}", groups.stream().map(Group::toHTML)
            .collect(Collectors.joining("\n\n")));

    htmlString = htmlString.replace("${layout}", lines.stream().map(Line::toHTML)
            .collect(Collectors.joining("\n\n")));

    String listToLoad = lines.stream().map(line -> line.getComponents()).flatMap(c -> c.stream())
            .filter(Component::isLoadOnStartUp).map(component -> "callServer('" + component.getId() + "', null);")
            .collect(Collectors.joining("\n"));

    htmlString = htmlString.replace("${load}", listToLoad);

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
