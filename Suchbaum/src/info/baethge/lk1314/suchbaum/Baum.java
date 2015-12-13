/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.baethge.lk1314.suchbaum;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 *
 * @author frank.baethge
 */
public class Baum {
  private Knoten wurzel;

	/**
	 * Konstruktor
	 */
	public Baum() {
    wurzel = null;
  }

	/**
	 * anhängen eines weiteren Knoten
	 * @param k - anzuhängender Knoten
	 */
  public void einfügen(Knoten k) {
    if (wurzel == null) {
      wurzel = k;
    } else {
      wurzel.einfügen(k);
    }
  }
  
  /**
   * Sucht nach dem String toFind in den Werten des Baumes und gibt den jeweiligen Knoten zurück
   * wird toFind nicht gefunden wird null zurückgegeben
   * @param toFind
   */
  public Knoten finde(String toFind) {
    return wurzel.finde(toFind);
  }

  /**
   * beim Rechtsrotieren wird der linke Verweis zur Wurzel
   * @param k - wurzelKnoten, um den rotiert wird
   * 
   * zur Erklärung
   * die Struktur ist vorher k.zurück = kz, k.links == kl und k.links.rechts == klr
   * nachher soll der Baum 2, kl.rechts == k und klr.rechts.links = 3 sein
   * 
   */
  public void rotiereRechts(Knoten k) {
    Knoten kl = k.getLinks();
    if (kl != null) {
      Knoten klr = kl.getRechts();
      Knoten kz = k.getZurück();
      if (kz == null) { // Wurzel wird rotiert
        wurzel = kl;
      } else { // ein Knoten (nicht Wurzel) wird rotiert - wir müssen noch entscheiden ob sich links oder rechts ändert
        if (kz.getLinks() == k) {
          kz.setLinks(kl);
        } else {
          kz.setRechts(kl);
        }
      }
      kl.setZurück(kz);
      k.setZurück(kl);
      if (klr != null) klr.setZurück(k);
      kl.setRechts(k);
      k.setLinks(klr);
    } // else -> rechts rotieren geht nicht, es muss schon ein linker Zweig existieren
  }

	/**
	 * wie oben - nur anders herum
	 * @param k - wurzelKnoten, um den rotiert wird
	 */
  public void rotiereLinks(Knoten k) {
    Knoten kr = k.getRechts();
    if (kr != null) {
      Knoten krl = kr.getLinks();
      Knoten kz = k.getZurück();
      if (kz == null) { // Wurzel wird rotiert
        wurzel = kr;
      } else { // ein Knoten (nicht Wurzel) wird rotiert - es ist aber unklar, ob links oder rechts
        if (kz.getRechts() == k) {
          kz.setRechts(kr);
        } else {
          kz.setLinks(kr);
        }
      }
      kr.setZurück(kz);
      k.setZurück(kr);
      if (krl != null) krl.setZurück(k);
      kr.setLinks(k);
      k.setRechts(krl);
    } // else -> rechts rotieren geht nicht, es muss schon ein linker Zweig existieren
  }

	/**
   * durchläuft ganzen Baum inorder
   * @return String mit Liste der Einträge
   */
  @Override
  public String toString() { 
    if (wurzel == null) {
      return "der Baum ist leer";
    } else {
      return wurzel.inorder();
    }
  }

	/**
	 * @return - Text aus dem GraphViz den Graphen kreiert
	 */
  protected String getGraphviz(){
    return "digraph g {\n"
            + " graph [\n"
            + "  rankdir = \"TB\"\n"
            + "  bgcolor = \"white:lightblue\"\n"
            + "  style=\"filled\"\n"
            + "  gradientangle = 270\n"
            + " ];\n"
            + " node [shape=box,style=filled,color=\"lightgray\"];\n"
            + wurzel.getGraphviz()
            + "\n}";
  }

	/**
	 * ruft GraphViz auf, übergibt dort auf stdIn den Graphen als Text und liest stdOut des Prozesses, welches als Bild
	 * im Weiteren verwendet wird
	 * @return - Bild, dass GraphViz aus dem Text gemacht hat
	 */
  protected BufferedImage getGraphImage(){
    Runtime runtime = Runtime.getRuntime();
    String[] cmd = {"dot","-Tpng"};
    Process process;
    try {
      process = runtime.exec(cmd);
      // stdOutput meint den Output dieses Programms, d.h. die Logig ist auf der Kommandozeile vertauscht
      OutputStream stdIn = process.getOutputStream();
      String s = getGraphviz() + "\n";
      byte[] b = s.getBytes();
      stdIn.write(b);
      stdIn.flush();
      BufferedImage stdInput = ImageIO.read(process.getInputStream());
      BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
      return stdInput;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
