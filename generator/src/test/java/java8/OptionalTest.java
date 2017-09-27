package java8;

import org.apache.commons.lang3.StringUtils;
import org.dashboard.OrderPrefixe;
import java8.bean.Client;
import java8.bean.Enfant;
import java8.bean.Parent;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OptionalTest {

  @Test
  public void
  optional_list_of_methods() {
    //créer un optional
    Optional<Parent> parent = Optional.of(new Parent("Georges", null));
    System.out.println(parent);

    //Récupérer la valeur d'un optional
    System.out.println(parent.get());

    //Appliquer une fonction à un optional
    Optional<Enfant> enfant = parent.map(p -> new Enfant(p.name + " JR"));
    System.out.println(enfant.get());

    Optional<Enfant> enfant1 = parent.flatMap(p -> Optional.of(new Enfant(p.name + " Jr")));
    System.out.println(enfant1.get());
    //Use map if the function returns the object or flatMap if the function returns an Optional
    //https://stackoverflow.com/questions/30864583/java-8-difference-between-optional-flatmap-and-optional-map

    //Vérifier la présence
    boolean present = parent.isPresent();
    System.out.println(present);

    present = Optional.ofNullable(null).isPresent();
    System.out.println(present);

    //Récupérer "correctement" la valeur d'un optional
    System.out.println(parent.isPresent() ? parent.get() : null);

    //Fail: Optional.ofNullable(null).get();
    //Fail Optional.of(null);
  }

  @Test
  public void
  using_orElse_when_value_missing() {
    OrderPrefixe.ORDER_PREFIXES.stream()
            .filter(ot -> StringUtils.startsWithIgnoreCase("1234567", ot.prefixe))
            .findFirst()
            .orElse(OrderPrefixe.P_INCONNU);

  }

  @Test
  public void
  using_orElseGet() {
    Enfant enfant = getEnfant().filter(e -> e != null).orElseGet(() -> new Enfant("to_be_born"));

    //Qui donne le même résultat que getEnfant().filter(e -> e != null).orElse(new Enfant("to_be_born"))

    System.out.println(enfant);
  }

  @Test
  public void
  diffrence_between_orElse_and_orElseGet() {
    Optional<String> s = Optional.of("Value's supposed to be");
    String ss = s.orElse(wontRunThis());
    System.out.println("optional returns " + ss);

    System.out.println("*****************");

    s = Optional.of("Value's supposed to be");
    ss = s.orElseGet(() -> wontRunThis());
    System.out.println("optional returns " + ss);

    // Lesson learned. Optional.orElse() can be used to assigning literal value but we shouldn't expect a control flow with it.
    // Il  n'est pas possible de faire 'simplement" du flow controle avec orElse et orElseGet Java 9 implémente Optional#ifPresentOrElse
  }

  private static String wontRunThis() {
    System.out.println("won't run this");
    return "something";
  }

  public Optional<Enfant> getEnfant() {
    return Optional.ofNullable(null);
  }

  public Client getClient(String id) {
    return null;
  }
}
