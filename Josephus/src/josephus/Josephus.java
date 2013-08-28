/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package josephus;

/**
 *
 * @author frank
 */
public class Josephus {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    Person p = new Person(1);
    for (int i = 41; i > 1; i--) {
      p.setzeNachfolger(new Person(i));
    }
    
    Person aktuell = p.vorheriger;
    while (!aktuell.istLetzter()) {
      aktuell = aktuell.nächster.nächster;
      aktuell.nächster.töte();
      ausgabe(aktuell);
    }
    
  }
  public static void ausgabe(Person p) {
    Person a = p;
    do {
      System.out.print(a + " ");
      a = a.nächster;
    } while (a != p);
    System.out.println();
  }
}

class Person {
  /* 
   * beschreibt die Personen mit ihren Eigenschaften und Fähigkeiten
   */
  private int nummer = 0;
  public Person nächster, vorheriger;
  private String name = "frank";
  
  public Person(int nr) {
    nummer = nr;
    nächster = this;
    vorheriger = this;
  }
  public void setzeNachfolger(Person eingefügter) {
    nächster.vorheriger = eingefügter;
    eingefügter.nächster = nächster;
    eingefügter.vorheriger = this;
    nächster = eingefügter;
  }
  public void töte() {
    vorheriger.nächster = nächster;
    nächster.vorheriger = vorheriger;
  }
  public boolean istLetzter() {
    return (nächster == this);
  }
  @Override public String toString() { return Integer.toString(nummer); }
}