/*
 * Binärer Suchbaum
 * implementiert nur die wichtigsten Prozeduren
 */
package info.baethge.lk1314.suchbaum;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.util.Arrays;

/**
 * zur Demonstration der Techniken in binären Bäumen
 * ergänzt um die Anzeige von Graphviz-Bildern, die den Zustand des Baums illustrieren
 * ACHTUNG: Graphviz - dot wird auf der Kommandozeile ausgeführt, also sollte das auch ausführbar sein
 * @author Frank Baethge
 */
public class Suchbaum {
	private String[] städte = {"Rom", "Berlin","Paris","Ulan Bator","London","Lissabon","Washington","Brüssel",
			"Luxemburg","Wien","Amsterdam","Vatikanstadt"};
	private GraphViz graphViz;

	/**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
	  Suchbaum suchbaum = new Suchbaum();
	  suchbaum.run();
  }

	private void run() {
		// UI initialisieren
		graphViz = new GraphViz();

		// Baum aus den oben gegebenen Orten bauen (in der angegebenen Reihenfolge)
		Baum baum = new Baum();
		for(String stadt : städte) {
			baum.einfügen(new Knoten(stadt));
		}
		//
		System.out.println("Baum inorder: " + baum);
		graphViz.addPic(baum.getGraphImage());

		// versuche den Baum besser zu verteilen
		Knoten stadt;

		stadt = baum.finde("Paris");
		if (stadt != null) {
			baum.rotiereRechts(stadt);
			graphViz.addPic(baum.getGraphImage());
		}
		stadt = baum.finde("Berlin");
		if (stadt != null) {
			baum.rotiereLinks(stadt);
			graphViz.addPic(baum.getGraphImage());
		}
		stadt = baum.finde("London");
		if (stadt != null) {
			baum.rotiereRechts(stadt);
			graphViz.addPic(baum.getGraphImage());
		}

		Baum optimalerBaum = getOptimalenBaum();
		graphViz.addPic(optimalerBaum.getGraphImage());

		// saveAllTabs() sollte eigentlich mit Strg+S aufgerufen werden
		// graphViz.saveAllTabs();

	}
	private Baum getOptimalenBaum() {
		Baum oBaum = new Baum();
		String[] st = städte.clone();
		Arrays.sort(st);
		einfügenInOptimalenBaum(st,0,st.length-1,oBaum);
		return oBaum;
	}
	private void einfügenInOptimalenBaum(String [] st, int linkeGrenze, int rechteGrenze, Baum b) {
		int mitte = (linkeGrenze + rechteGrenze) / 2;
		b.einfügen(new Knoten(st[mitte]));
		if (linkeGrenze <= mitte - 1) {
			einfügenInOptimalenBaum(st,linkeGrenze,mitte-1,b);
		}
		if (mitte + 1 <= rechteGrenze ){
			einfügenInOptimalenBaum(st,mitte+1,rechteGrenze,b);
		}
	}
}
