/*
 * Blattklasse
 *  zeigt den Buchstaben an, der über diesen Pfad kodiert werden soll
 */
package baum;

/**
 *
 * @author frank.baethge
 */
public class Blatt extends Ast {
  private char buchstabe;

  public Blatt(char c, int h) {
    buchstabe = c;
    setHäufigkeit(h);
  }
  
  @Override
  public String ausgeben(String kodierung) {
    return "\"" + buchstabe + '"' + kodierung + ' ';
  }
  @Override
  public Integer berechneBitlänge(int ebene) {
    return ebene * getHäufigkeit();
  }
}