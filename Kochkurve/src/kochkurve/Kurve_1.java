/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kochkurve;
import java.awt.*;
/**
 *
 * @author Frank
 */
public class Kurve_1 {
  
  private int maxLevel = 1;
  private int gesamtLänge = 800;
  private String initiator = "F";
  private String generator = "FlFrrFlF";
  /**
   * Creates new form Kurve1
   */
  public Kurve_1() {
  }
  public Kurve_1(int AnzahlLevel) {
    maxLevel = AnzahlLevel;
  }
  public String KochkurveAlsText() {
    return KochkurveAlsText(0, initiator);
  }
  public String KochkurveAlsText(int level, String kurve) {
    if (level >= maxLevel) {
      return kurve;
    } else {
      // ersetze alle initiatoren durch die Generatoren
      String neueGeneration = kurve.replaceAll(initiator, generator);
      return KochkurveAlsText(level+1,neueGeneration);
    }
  }
  public void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.blue);
    String k = KochkurveAlsText();
    double länge = gesamtLänge/Math.pow(3, maxLevel);
    double x = 10, y = 50, x1 = 0, y1 = 0, winkel = 0;
    for (char c: k.toCharArray()) {
      switch (c) {
        case 'F': 
          x1 = x + länge * Math.cos(winkel);
          y1 = y + länge * Math.sin(winkel);
          g2d.drawLine((int)x, (int)y, (int)x1, (int)y1);
          x = x1;
          y = y1;
          break;
        case 'l': 
          winkel += Math.PI/3;
          break;
        case 'r':
          winkel -= Math.PI/3;
          break;
      }
    }
  }

  
}
