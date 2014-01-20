/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package suchbaum;

/**
 *
 * @author frank.baethge
 */
public class Baum {
  private Knoten wurzel;
  
  public Baum() {
    wurzel = null;
  }
  
  public void einfügen(Knoten k) {
    if (wurzel == null) {
      wurzel = k;
    } else {
      wurzel.einfügen(k);
    }
  }
  
  /**
   * Sucht nach dem String toFind in den Werten des Baumes und gibt den jeweiligen Knoten zurück
   * wird toFind nicht gefunden wird null zurückgegeben
   * @param toFind
   */
  public Knoten finde(String toFind) {
    return wurzel.finde(toFind);
  }
  /**
   * beim Rechtsrotieren wird der linke Verweis zur Wurzel
   * @param k 
   * 
   * zur Erklärung
   * die Struktur ist vorher k == 1, k.links == 2 und k.links.rechts == 3
   * nachher soll der Baum 2, 2.rechts == 1 und 2.rechts.links = 3 sein
   * 
   */
  public void rotiereRechts(Knoten k) {
    Knoten k1 = k;
    Knoten k2 = k.getLinks();
    if (k2 != null) {
      Knoten k3 = k2.getRechts();
      Knoten k0 = k1.getZurück();
      if (k0 == null) { // Wurzel wird rotiert
        wurzel = k2;
      } else { // ein Knoten (nicht Wurzel) wird rotiert - es ist aber unklar, ob links oder rechts
        if (k0.getLinks() == k1) {
          k0.setLinks(k2);
        } else {
          k0.setRechts(k2);
        }
      }
      k2.setZurück(k0);
      k1.setZurück(k2);
      if (k3 != null) k3.setZurück(k1);
      k2.setRechts(k1);
      k1.setLinks(k3);
    } // else -> rechts rotieren geht nicht, es muss schon ein linker Zweig existieren
  }
  public void rotiereLinks(Knoten k) {
    Knoten k1 = k;
    Knoten k2 = k.getRechts();
    if (k2 != null) {
      Knoten k3 = k2.getLinks();
      Knoten k0 = k1.getZurück();
      if (k0 == null) { // Wurzel wird rotiert
        wurzel = k2;
      } else { // ein Knoten (nicht Wurzel) wird rotiert - es ist aber unklar, ob links oder rechts
        if (k0.getRechts() == k1) {
          k0.setRechts(k2);
        } else {
          k0.setLinks(k2);
        }
      }
      k2.setZurück(k0);
      k1.setZurück(k2);
      if (k3 != null) k3.setZurück(k1);
      k2.setLinks(k1);
      k1.setRechts(k3);
    } // else -> rechts rotieren geht nicht, es muss schon ein linker Zweig existieren
  }
  
  @Override
  public String toString() { 
    if (wurzel == null) {
      return "der Baum ist leer";
    } else {
      return wurzel.inorder();
    }
  }
  
  protected String getGraphviz(){
    return "digraph g {\n"
            + " graph [\n"
            + "  rankdir = \"TB\"\n"
            + "  bgcolor = \"white:lightblue\"\n"
            + "  style=\"filled\"\n"
            + "  gradientangle = 270\n"
            + " ];\n"
            + " node [shape=box,style=filled,color=\"lightgray\"];\n"
            + wurzel.getGraphviz()
            + "\n}";
  }
}
