/*
 * Implementiert die Klasse Linie, zur Darstellung einer Strecke
 */
package geometrischeobjekte;
import java.awt.*;

/**
 * Konstruktoren
 * @author Frank
 */
public class Linie {
  protected Punkt _von;
  protected Punkt _bis;
  protected Color _zeichenFarbe;

  public Linie(int vonX, int vonY, int bisX, int bisY) {
    _von = new Punkt(vonX,vonY);
    _bis = new Punkt(bisX,bisY);    
    setZeichenFarbe(Color.blue);    
  }
  public Linie(Punkt von, Punkt bis) {
    _von = von;
    _bis = bis;
    setZeichenFarbe(Color.blue);
  }
  
  @Override
  public String toString() {
    return "Ich bin eine Linie von " + _von + " bis " + _bis + " mit der Länge " + getLänge();
  }

  // getter und setter
  protected double getLänge() {
    double deltaX = _bis.getX() - _von.getX();
    double deltaY = _bis.getY() - _von.getY();
    return Math.sqrt(deltaX*deltaX+deltaY*deltaY);
  }
  public Color getZeichenFarbe() {
    return _zeichenFarbe;
  }
  public void setZeichenFarbe(Color zeichenFarbe) {
    _zeichenFarbe = zeichenFarbe;
  }
  
  // Programmlogik
  public void zeichne(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(_zeichenFarbe);
    g2d.drawLine(_von.getX(), _von.getY(), _bis.getX(), _bis.getY());
  }
}