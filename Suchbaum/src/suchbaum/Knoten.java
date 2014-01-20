package suchbaum;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author frank.baethge
 */
public class Knoten {
  private Knoten links;
  private Knoten rechts;
  private String wert;

  protected Knoten(String w) {
    links = null;
    rechts = null;
    wert = w;
  }
  
  protected void einfügen(Knoten k) {
    if (k.getWert().compareTo(wert) < 0) {
      if (links == null) {
        links = k;
      } else {
        links.einfügen(k);
      }
    } else {
      if (k.getWert().compareTo(wert) == 0) {
        // mache nichts
      } else {
        // Wert ist größer
        if (rechts == null) {
          rechts = k;
        } else {
          rechts.einfügen(k);
        }
      }
    }
  }
  
  @Override
  public String toString() { 
    return wert; 
  }
  
  public String inorder() { //LWR
    String ergebnis = "";
    if (links != null) {
      ergebnis += links.inorder();
    }
    ergebnis += " " + wert + " ";
    if (rechts != null) {
      ergebnis += rechts.inorder();
    }
    return ergebnis;
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
   * @return the wert
   */
  public String getWert() {
    return wert;
  }

  /**
   * @param wert the wert to set
   */
  public void setWert(String wert) {
    this.wert = wert;
  }

  protected String getGraphviz() {
    String gv = String.format(" \"%s\" [label=\"%s\"]", this, this);
    String linkedTo = "";
    if (links != null) {
      linkedTo += String.format("\"%s\" -> \"%s\"\n%s", this, links, links.getGraphviz());
    }
    if (rechts != null) {
      linkedTo += String.format("\"%s\" -> \"%s\"\n%s", this, rechts, rechts.getGraphviz());
    }
    return String.format("%s\n%s", gv, linkedTo);
  }
    
}
