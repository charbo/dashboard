package java8.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Parent {

  public String name;
  public List<Enfant> enfants = new ArrayList<>();

  public Parent(String name, List<Enfant> enfants) {
    this.name = name;
    this.enfants = enfants;
  }

  public Stream<Enfant> getEnfants() {
    return enfants.stream();
  }

  @Override
  public String toString() {
    return "Parent{" +
            "name='" + name + '\'' +
            ", enfants=" + enfants +
            '}';
  }

  public static Parent getParentNull() {
    return null;
  }
}
