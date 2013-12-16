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
    return "\"" + buchstabe + '"' + getHäufigkeit() + ' ' + kodierung + ' ';
  }
  @Override
  public Integer berechneBitlänge(int ebene) {
    return ebene * getHäufigkeit();
  }
  @Override
  protected String getGraphviz(int ebene) {
    return String.format(
            " \"%s\" [label=\"%s - %d\" shape=ellipse,style=filled,color=\".4 1.0 .7\"]",
            this,
            buchstabe,
            getHäufigkeit()
            );
  }
}