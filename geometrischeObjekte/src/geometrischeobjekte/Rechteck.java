/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geometrischeobjekte;
import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 *
 * @author Frank
 */
public class Rechteck extends Linie {
  protected Color füllFarbe = new Color(200,200,80);
  
  public Rechteck(Punkt p1, Punkt p2) {
    super(p1,p2);
    setZeichenFarbe( new Color(125,167,116) );
  }
  protected int getBreite() { return _bis.getX() - _von.getX(); }
  protected int getHöhe()   { return _bis.getY() - _von.getY(); }
  
  @Override
  public void zeichne( Graphics g ) {
    super.zeichne(g);
    Graphics2D g2d = (Graphics2D)g;
    g2d.fillRect(_von.getX(), _von.getY(), getBreite(), getHöhe());
    
    // Author: Hannes - Zeichnen einer Umrandung (als Polygon
    int xPunkte[] = {_von.getX(), _von.getX(), _bis.getX(), _bis.getX()};
    int yPunkte[] = {_von.getY(), _bis.getY(), _bis.getY(), _von.getY()};
    
    GeneralPath polygon =new GeneralPath(GeneralPath.WIND_EVEN_ODD, xPunkte.length);
    polygon.moveTo(xPunkte[0], yPunkte[0]);
    for (int i = 1; i < xPunkte.length; i++) {
      polygon.lineTo(xPunkte[i], yPunkte[i]);  
    }
    polygon.closePath();
    g2d.setColor( füllFarbe );
    //g2d.setStroke(  );
    g2d.draw(polygon);
  }
}
