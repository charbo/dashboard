package java8;

import java8.bean.Enfant;
import java8.bean.Parent;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


public class MapVsFlatMap {

  @Test public void
  printMapOfParents() {
    List enfants1 = Arrays.asList(new Enfant("enfant1"), new Enfant("enfant2"), new Enfant("enfant3"));
    List enfants2 = Arrays.asList(new Enfant("enfant4"), new Enfant("enfant5"));

    Parent parent1 = new Parent("parent1", enfants1);
    Parent parent2 = new Parent("parent2", enfants2);

    Arrays.asList(parent1, parent2).stream().map(p -> p.enfants).forEach(System.out::println);
    Arrays.asList(parent1, parent2).stream().flatMap(p -> p.getEnfants()).forEach(System.out::println);

  }



}
