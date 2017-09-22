package demo.bean;


public class Enfant {

  public String name;

  public Enfant(String name) {
    this.name = name;
  }

  public boolean isBorn(){
    return false;
  }


  @Override
  public String toString() {
    return "Enfant{" +
            "name='" + name + '\'' +
            '}';
  }
}
