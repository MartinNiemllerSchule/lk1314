/*
 * Binärer Suchbaum
 * implementiert nur die wichtigsten Prozeduren
 */
package suchbaum;

/**
 *
 * @author frank.baethge
 */
public class Suchbaum {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    Baum b = new Baum();
    b.einfügen(new Knoten("Rom"));
    b.einfügen(new Knoten("Berlin"));
    b.einfügen(new Knoten("Paris"));
    b.einfügen(new Knoten("Ulan Bator"));
    b.einfügen(new Knoten("London"));
    b.einfügen(new Knoten("Lissabon"));
    b.einfügen(new Knoten("Washington"));
    b.einfügen(new Knoten("Brüssel"));
    b.einfügen(new Knoten("Luxemburg"));
    b.einfügen(new Knoten("Wien"));
    b.einfügen(new Knoten("Amsterdam"));
    b.einfügen(new Knoten("Vatikanstadt"));
    
    System.out.println("Baum preorder: " + b);
    System.out.println("Graphviz: \n" + b.getGraphviz());
    
    Knoten stadt = b.finde("Berlin");
    System.out.println("Paris: " + stadt);
    if (stadt != null) {
      b.rotiereLinks(stadt);
      System.out.println("Graphviz: \n" + b.getGraphviz());
    }
    stadt = b.finde("Berlin");
    if (stadt != null) {
      b.rotiereRechts(stadt);
      System.out.println("Graphviz: \n" + b.getGraphviz());
    }  
  }
}
