/*
 * Die Klasse Punkt stellt die Basisfunktionalitäten aller geometrischen Objekte her. 
 */
package geometrischeobjekte;

/**
 *
 * @author Frank
 */
public class Punkt {
  private int _x;
  private int _y;
  public Punkt(int x, int y) {
    _x = x;
    _y = y;
  }
  public int getX() {return _x;}
  public int getY() {return _y;}
  public void translate(int x, int y) {
    _x += x;
    _y += y;
  }
  @Override
  public String toString() {
    return "Ich bin ein Punkt am Ort(" + _x + ", " + _y + ")";
  }
  
}
