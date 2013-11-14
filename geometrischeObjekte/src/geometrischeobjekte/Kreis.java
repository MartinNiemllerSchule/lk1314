/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geometrischeobjekte;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Kreis erweitert die Linie insofern, dass _von den Mittelpunkt darstellt und
 * _bis einen Punkt auf dem Kreis (damit steht der Radius fest - _länge)
 * @author Frank
 */
public class Kreis extends Linie {

  public Kreis(Punkt von, Punkt bis) {
    super(von, bis);
    _von.translate( _von.getX()-_bis.getX(), _von.getY()-_bis.getY() );
    setZeichenFarbe( new Color(142,19,31) );
  }
  public double getRadius() {return getLänge()/2; }
  public int getDurchmesser() {return (int)getLänge();} 
  @Override
  public void zeichne( Graphics g) {
    super.zeichne(g);
    Graphics2D g2d = (Graphics2D)g;
    int d = getDurchmesser();
    g2d.drawOval(_von.getX(), _von.getY(), d, d);
    g2d.setColor( new Color(250,210,220) );
    Ellipse2D k = new Ellipse2D.Double(_von.getX(), _von.getY(), d, d);
    g2d.fill(k);
  } 
}