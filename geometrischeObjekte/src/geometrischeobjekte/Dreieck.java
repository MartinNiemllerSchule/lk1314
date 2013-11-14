/*
 * Implementiert die Klasse Dreieck, zur Darstellung von Dreiecken
 */
package geometrischeobjekte;

import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 *
 * @author Frank
 */
public class Dreieck extends Linie {
  protected Punkt pkt;
  protected Color füllFarbe;

  public Dreieck(Punkt von, Punkt bis, Punkt p) {
    super(von, bis);
    pkt = p;
    füllFarbe = new Color(200,100,100);
    setZeichenFarbe( new Color(180,100,80) );
  }
  
  @Override
  public void zeichne( Graphics g ) {
    Graphics2D g2d = (Graphics2D)g;
    
    GeneralPath polygon =new GeneralPath(GeneralPath.WIND_EVEN_ODD, 3);
    polygon.moveTo(_von.getX(), _von.getY());
    polygon.lineTo(_bis.getX(), _bis.getY());
    polygon.lineTo(pkt.getX(), pkt.getY());
    polygon.closePath();
    g2d.setColor( getZeichenFarbe() );
    //g2d.setStroke( new Stroke() );
    g2d.draw(polygon);
    g2d.setColor( füllFarbe );
    g2d.fill(polygon);
  }
  
}
