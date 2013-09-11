/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tournamentsort;
import java.util.Random;

/**
 *
 * @author Frank
 */
public class Element {

  private int füllung;
  private boolean gefüllt;
  private Element mom = null;
  private Element dad = null;

  public Element() {
    gefüllt = false;
  }

  public Element(int f) {
    füllung = f;
    gefüllt = true;
  }

  public void baueEbene() {
    if (mom != null) {
      mom.baueEbene();
      dad.baueEbene();
    } else {
      mom = new Element();
      dad = new Element();
    }
  }
  public int fülleEbene(int anzahl) {
    if (mom != null) {
      // Absteigen und Finden der Elemente, 
      // in denen die zu sortierenden Elemente eingehängt werden sollen
      anzahl = mom.fülleEbene(anzahl);
      anzahl = dad.fülleEbene(anzahl);
    } else {
      // Generieren der neuen Elemente und füllen mit Zufallszahlen
      Random r = new Random();
      if (anzahl > 0) {
        mom = new Element(r.nextInt(100));
        anzahl--;
      }
      if (anzahl > 0) {
        dad = new Element(r.nextInt(100));
        anzahl--;
      }
    }
    return anzahl;
  }
  
  public void ersterDurchgang() {
    // erst nach unten absteigen, um dann die Sieger nach oben zu bringen
    if (mom != null) {
      mom.ersterDurchgang();
      dad.ersterDurchgang();
      // nach dem Abstieg Gewinner nach oben ziehen
      if (mom.füllung > dad.füllung) {
        füllung = mom.füllung;
        gefüllt = true;
        mom.gefüllt = false;
        mom.nachrücken();
      } else {
        füllung = dad.füllung;
        gefüllt = true;
        dad.gefüllt = false;
        dad.nachrücken();
      }
    } else {
      // ganz unten ist nichts zu tun
    }
  }
  private void nachrücken() {
    // das Element wurde geleer, jetzt muss mom oder dad nachrücken
    if (mom != null) {
      if (mom.gefüllt) {
        if (dad.gefüllt) {
          // beide gefüllt - entscheide, wer größer ist
          if (mom.füllung > dad.füllung) {
            mom.gefüllt = false;
            füllung = mom.füllung;
            gefüllt = true;
            mom.nachrücken();
          } else {
            dad.gefüllt = false;
            füllung = dad.füllung;
            gefüllt = true;
            dad.nachrücken();
          }
        } else {
          // nur mom ist gefüllt
          mom.gefüllt = false;
          füllung = mom.füllung;
          gefüllt = true;
          mom.nachrücken();
        }
      } else {
        // mom ist nicht gefüllt
        if (dad.gefüllt) {
          dad.gefüllt = false;
          füllung = dad.füllung;
          gefüllt = true;
          dad.nachrücken();
        } else {        
          // mom und dad sind nicht gefüllt
          gefüllt = false;
        }
      }
    } else gefüllt = false;
  }
  
  public String sortiere() {
    String Ergebnis = "";
    while (gefüllt) {
      Ergebnis += " " + füllung;
      gefüllt = false;
      this.nachrücken();
    }
    return Ergebnis;
  }
  
  @Override public String toString() {
    String wert = (gefüllt) ? " Wert: " + füllung : " ungefüllt";
    String momS, dadS;
    if (mom != null) {
      momS = " mom: " + mom.hashCode() + "\n" + mom.toString() + " z";
    } else { momS = " null"; }
    if (dad != null) {
      dadS = " dad: " + dad.hashCode() + "\n" + dad.toString() + " z";
    } else {
      dadS = " null";
    }
    return "Element: "+this.hashCode()+ wert + momS + dadS;
  }
}
