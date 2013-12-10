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
public class Ast {
  private Ast rechts;
  private Ast links;
  private int häufigkeit;
  
  public Ast() {
    rechts = null;
    links = null;
    häufigkeit = 0;
  }

  public Ast(Ast l, Ast r) {
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
    //System.out.println(kodierung + " l:" + getLinks() + " r:" + getRechts());
    return " l: (" + getLinks().ausgeben(kodierung + '0') + ")" + 
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
  public Ast getRechts() {
    return rechts;
  }

  /**
   * @param rechts the rechts to set
   */
  public void setRechts(Ast rechts) {
    this.rechts = rechts;
  }

  /**
   * @return the links
   */
  public Ast getLinks() {
    return links;
  }

  /**
   * @param links the links to set
   */
  public void setLinks(Ast links) {
    this.links = links;
  }

  /**
   * @return the häufigkeit
   */
  public int getHäufigkeit() {
    return häufigkeit;
  }

  /**
   * @param häufigkeit the häufigkeit to set
   */
  public void setHäufigkeit(int häufigkeit) {
    this.häufigkeit = häufigkeit;
  }
}
