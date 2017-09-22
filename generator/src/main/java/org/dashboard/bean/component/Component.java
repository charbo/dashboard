package org.dashboard.bean.component;

import org.apache.commons.lang3.StringUtils;
import org.dashboard.bean.CustomizableItem;
import org.dashboard.bean.Line;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Component extends CustomizableItem implements Serializable, Comparable<Component> {
  private String id;
  private Integer index;
  private String renderFunction;
  private String event;
  private Dataset dataset;
  private boolean loadOnStartUp = false;

  private List<String> childs = new ArrayList<>();

  private List<Parameter> parameters = new ArrayList<>();

  private Set<Option> options = new TreeSet<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getIndex() {
    return index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }

  public String getRenderFunction() {
    return renderFunction;
  }

  public void setRenderFunction(String renderFunction) {
    this.renderFunction = renderFunction;
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public Dataset getDataset() {
    return dataset;
  }

  public void setDataset(Dataset dataset) {
    this.dataset = dataset;
  }

  public boolean isLoadOnStartUp() {
    return loadOnStartUp;
  }

  public void setLoadOnStartUp(boolean loadOnStartUp) {
    this.loadOnStartUp = loadOnStartUp;
  }

  public List<String> getChilds() {
    return childs;
  }

  public void setChilds(List<String> childs) {
    this.childs = childs;
  }

  public List<Parameter> getParameters() {
    return parameters;
  }

  public void setParameters(List<Parameter> parameters) {
    this.parameters = parameters;
  }

  public Set<Option> getOptions() {
    return options;
  }

  public void setOptions(Set<Option> options) {
    this.options = options;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Component component = (Component) o;

    return getIndex().equals(component.getIndex());
  }

  @Override
  public int hashCode() {
    return getIndex().hashCode();
  }

  @Override
  public int compareTo(Component o) {
    return index - o.getIndex();
  }

  public String toJavaScript() {
    StringBuilder result = new StringBuilder();
    result.append(generateOptionsMap());
    result.append("\n");
    result.append("var ").append(id).append(" = new ").append(renderFunction)
            .append("('chart").append(id).append("', '")
            .append(id)
            .append("', ");
    String childsArray = childs.stream().map(child -> "'" + child + "'").collect(Collectors.joining(", ", "[", "]"));
    String parametersArray = parameters.stream().map(Parameter::toJavaScript).collect(Collectors.joining(", ", "[", "]"));
    result.append(childsArray);
    result.append(", ");
    result.append(dataset != null ? dataset.toJavaScript() : "''");
    result.append(", ");
    result.append(parametersArray);
    result.append(", options");
    result.append(id);
    result.append(");");
    result.append("\n");
    result.append(addInComponentsArray());
    result.append("\n");
    if (StringUtils.isNotBlank(event)) {
      result.append(toObservable());
    }

    return result.toString();
  }

  private String generateOptionsMap() {
    StringBuilder result = new StringBuilder();
    result.append("var options").append(id).append(" = new Array();\n");
    result.append(this.options.stream().map(option -> option.toJavaScript(id)).collect(Collectors.joining("\n")));
    return result.toString();
  }

  private String toObservable() {
    StringBuilder result = new StringBuilder();
    result.append("const observable").append(id).append(" = Rx.Observable.fromEvent($('#chart").append(id).append("'), '").append(event).append("')").append(";");
    return result.toString();
  }

  public String toHTML() {
    StringBuilder html = new StringBuilder();
    html.append("<div id='").append(id).append("' class='").append(getCss()).append("'>").append("</div>");
    return html.toString();
  }


  private String addInComponentsArray() {
    StringBuilder result = new StringBuilder();
    result.append("components['chart").append(id).append("'] = ").append(id).append(";");
    return result.toString();
  }

  public boolean isObservable() {
    return StringUtils.isNotBlank(event);
  }

  public boolean isComplexeComponent() {
    return StringUtils.isNotBlank(renderFunction);
  }
}
