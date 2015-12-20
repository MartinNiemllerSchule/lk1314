/*
 * Blattklasse
 *  zeigt den Buchstaben an, der über diesen Pfad kodiert werden soll
 */
package info.baethge.lk1314.huffmann;

/**
 *
 * @author frank.baethge
 */
public class Blatt extends Knoten {
  private char buchstabe;

  public Blatt(Integer h, char c) {
    buchstabe = c;
    setHäufigkeit(h);
  }
  public Blatt(char c, Integer h) {
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