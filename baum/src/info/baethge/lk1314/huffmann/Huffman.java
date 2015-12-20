/*
 * Huffman-Algorithmus
 * eine Umsetzung
 */
package info.baethge.lk1314.huffmann;
import java.util.List;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 *
 * @author Frank Baethge
 */
public class Huffman {
  private Knoten wurzel;
  
  public Huffman(String text) {
    /* Buchstaben-Häufigkeiten auszählen - Hash-Tabelle */
    Hashtable<Character, Integer> buchstabenTabelle = new Hashtable<>();
    for (Character c : text.toCharArray()) {
      if (buchstabenTabelle.containsKey(c)) {
        buchstabenTabelle.put(c, buchstabenTabelle.get(c) + 1);
      } else {
        buchstabenTabelle.put(c, 1);
      }
    }
    /* Blätter in einer Liste erstellen - Collection of Knoten */
    List<Knoten> liste = new ArrayList<>();
    
    Iterator<Character> itr = buchstabenTabelle.keySet().iterator();
    while(itr.hasNext()) {
      Character c = itr.next();
      Knoten k = new Blatt(c,(Integer)buchstabenTabelle.get(c));
      liste.add(k);
    }
    
    /* Blätter nach Häufigkeit absteigend sortieren */
    Collections.sort(liste);
    
    /* Huffman-Baum bauen */
    while (liste.size() > 2) {
      /* erste zwei Elemente entfernen, verketten, einfügen und sortieren */
      Knoten k1 = liste.remove(0);
      Knoten k2 = liste.remove(1);
      Knoten k3 = new Knoten(k1,k2);
      liste.add(k3);
      Collections.sort(liste);
    }
    wurzel = new Knoten(liste.get(0),liste.get(1));
  }
  protected Knoten getWurzel() {return wurzel;}
}
