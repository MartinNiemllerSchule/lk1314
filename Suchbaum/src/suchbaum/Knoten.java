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
  private Knoten zurück;
  private String wert;

  protected Knoten(String w) {
    links = null;
    rechts = null;
    zurück = null;
    wert = w;
  }
  
  protected void einfügen(Knoten k) {
    if (k.getWert().compareTo(getWert()) < 0) {
      if (getLinks() == null) {
        setLinks(k);
        k.setZurück(this);
      } else {
        getLinks().einfügen(k);
      }
    } else {
      if (k.getWert().compareTo(getWert()) == 0) {
        // mache nichts, denn der Wert ist schon vorhanden
      } else {
        // Wert ist größer
        if (getRechts() == null) {
          setRechts(k);
          k.setZurück(this);
        } else {
          getRechts().einfügen(k);
        }
      }
    }
  }
  
  protected Knoten finde(String toFind) {
    Integer ct = getWert().compareTo(toFind);
    if (ct == 0) { // gefunden
      return this;
    } else {
      if (ct > 0) { // das Suchergebnis befindet sich links
        if (getLinks() == null) {
          return null;
        } else {
          return getLinks().finde(toFind);
        }
      } else { // das Suchergebnis befindet sich rechts
        if (getRechts() == null) {
          return null;
        } else {
          return getRechts().finde(toFind);
        }
      } 
    }
  }
  
  @Override
  public String toString() { 
    return getWert(); 
  }
  
  public String inorder() { //LWR
    String ergebnis = "";
    if (getLinks() != null) {
      ergebnis += getLinks().inorder();
    }
    ergebnis += " " + getWert() + " ";
    if (getRechts() != null) {
      ergebnis += getRechts().inorder();
    }
    return ergebnis;
  }
  
  protected String getGraphviz() {
    String gv = String.format(" \"%s\" [label=\"%s\"]", this, this);
    String linkedTo = "";
    if (getLinks() != null) {
      linkedTo += String.format("\"%s\" -> \"%s\"\n%s", this, getLinks(), getLinks().getGraphviz());
    }
    if (getRechts() != null) {
      linkedTo += String.format("\"%s\" -> \"%s\"\n%s", this, getRechts(), getRechts().getGraphviz());
    }
    return String.format("%s\n%s", gv, linkedTo);
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
   * @return the zurück
   */
  public Knoten getZurück() {
    return zurück;
  }

  /**
   * @param zurück the zurück to set
   */
  public void setZurück(Knoten zurück) {
    this.zurück = zurück;
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
    
}