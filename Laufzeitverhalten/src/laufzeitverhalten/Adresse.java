/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laufzeitverhalten;

/**
 *
 * @author frank.baethge
 */
public class Adresse {
  protected String n;
  protected String v;
  protected String s;
  protected String p;
  protected String o;
  
  public Adresse(String nachName, String vorName, String strasse, String postleitzahl, String ort) {
    n = nachName;
    v = vorName;
    s = strasse;
    p = postleitzahl;
    o = ort;
  }
  
}
