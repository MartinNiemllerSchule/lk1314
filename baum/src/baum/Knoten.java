/*
 * Astklasse
 *  nur in den Blättern des Baumes stehen unsere Informationen
 *  die Äste führen zu den Blättern
 */
package baum;

/**
 *
 * @author frank.baethge
 */
public class Knoten implements Comparable<Knoten> {
  private Knoten rechts;
  private Knoten links;
  private Integer häufigkeit;
  
  public Knoten() {
    rechts = null;
    links = null;
    häufigkeit = 0;
  }

  public Knoten(Knoten l, Knoten r) {
    rechts = r;
    links = l;
    häufigkeit = 0;
    if (r != null) {
      häufigkeit += r.häufigkeit;
    }
    if (l != null) {
      häufigkeit += l.häufigkeit;
    }
  }  
  
    
  /**
   * Traversierung des Baums
   *  es gibt mehrere Möglichkeiten einen Baum zu durchlaufen
   *    - pre-order => W-L-R
   *    - post-order => L-R-W
   *    - in-order => L-W-R (reverse in-order => R-W-L)
   *    - level-order => Breitensuche (jede Ebene nacheinander)
   *  hier wird am ehesten pre-order verwendet (Wuzel-Links-Rechts)
   * @param kodierung
   * @return 
   */
  public String ausgeben(String kodierung) {
    return "Häufigkeit: " + getHäufigkeit() + " l: (" + getLinks().ausgeben(kodierung + '0') + ")" + 
           "r: (" + getRechts().ausgeben(kodierung+'1') + ")";
  }
  /**
   * Berechnet die Summe für alle Blätter über das Produkt aus ebene * häufigkeit und 
   *  damit auch die Länge des komprimierten Textes
   * @param ebene - die Ebene kodiert gleichzeitig die neue Länge des jeweiligen Buchstabens 
   * @return 
   */
  public Integer berechneBitlänge(int ebene) {
    // Abstieg zu den Blättern und Rückgabe der berechneten Werte
    return getLinks().berechneBitlänge(ebene + 1) + getRechts().berechneBitlänge(ebene + 1);
  }

  /**
   * @return the rechts
   */
  public Knoten getRechts() {
    return rechts;
  }

  /**
   * @param rechts the rechts to set
   */
  public void setRechts(Knoten rechts) {
    this.rechts = rechts;
  }

  /**
   * @return the links
   */
  public Knoten getLinks() {
    return links;
  }

  /**
   * @param links the links to set
   */
  public void setLinks(Knoten links) {
    this.links = links;
  }

  /**
   * @return the häufigkeit
   */
  public Integer getHäufigkeit() {
    return häufigkeit;
  }

  /**
   * @param häufigkeit the häufigkeit to set
   */
  public void setHäufigkeit(Integer häufigkeit) {
    this.häufigkeit = häufigkeit;
  }
  
  protected String getGraphviz(int ebene) {
    String gv = String.format(
            " \"%s\" [label=\"%d\"]", 
            this, 
            getHäufigkeit() 
            );
    String linkedTo = String.format(
            "\"%s\" -> \"%s\"\n\"%s\" -> \"%s\"",
            this, links, this, rechts );
    return gv + "\n" + links.getGraphviz(ebene+1) + "\n" + rechts.getGraphviz(ebene+1) +
            "\n" + linkedTo;
  }
  
  @Override
  public int compareTo(Knoten k) {
    return this.häufigkeit.compareTo(k.häufigkeit);
  }
}
