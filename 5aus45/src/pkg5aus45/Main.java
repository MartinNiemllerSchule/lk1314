/*
 * 6 aus 49 mit Objekten
 *  die Modellierung gestaltet eine einfach verkettete Liste
 */
package pkg5aus45;

/**
 * @author frank
 */
import java.util.Random;

public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    Ziehung ziehung = new Ziehung((byte)6,(byte)49);
    ziehung.zieheAlle();
    ziehung.ausgabeAlle();
  }
}

class Kugel {
  private byte nummer;
  private Kugel nächste = null;
  
  // Konstuktor
  public Kugel(byte nr) {
    nummer = nr;
  }
  
  // anfügen einer weiteren Kugel (hinten)
  public void append(Kugel k) {
    // erst das Ende der Liste suchen
    Kugel aktuelleKugel = this;
    while (aktuelleKugel.nächste != null) aktuelleKugel = aktuelleKugel.nächste;
    // am Ende der Kette die neue Kugel anhängen
    aktuelleKugel.nächste = k;    
  }
  
  // bestimmen der Länge von dieser Kugel aus gesehen
  public int length() {
    int i = 0;
    Kugel k = this;
    while (k.nächste != null) {
      i++;
      k = k.nächste;
    }
    return i;
  }
  
  // Ausgabe, die eine Kugel macht
  @Override public String toString() { return Integer.toString(nummer); }
  
  // getter und setter
  public byte getNummer() { return nummer; }
  public Kugel getNächste() { return nächste; }
  public void setLetzte() { nächste = null; }
  public void entferneNächste() { // setter für nächste
    if (nächste != null) nächste = nächste.nächste;
  }
}

class Ziehung {
  private Kugel topf;
  private Kugel gezogene;
  private byte anzahlAlle;
  private byte anzahlGezogene;
  
  // Konstruktor
  public Ziehung(byte aG, byte aA) {
    anzahlGezogene = aG;
    anzahlAlle = aA;
    topf = new Kugel( (byte)1 );
    for (byte b = 2; b <= anzahlAlle ; b++) { topf.append(new Kugel(b)); }
    gezogene = null;
  }

  // zufälliges Entnehmen einer Kugel
  public void ziehe() {
    // Zufallszahl finden (von 0 bis length()-1)
    int stelle = (new Random()).nextInt(topf.length());
    
    // Kugel ausbinden
    Kugel entnommene;
    if (stelle == 0) { 
      // der Einstiegspunkt muss besonders behandelt werden
      entnommene = topf;
      topf = topf.getNächste();
      // nächste muss null werden (sonst ist die entnommene Kugel noch erste Kugel einer Kette)
      entnommene.setLetzte(); 
      
      if (gezogene == null)
        gezogene = entnommene;
      else
        gezogene.append(entnommene);
      
    } else {
      /* zu so und so vielten Kugel gehen
       * vorherige Kugel auswählen, damit diese auch noch bearbeitet werden kann
       */
      Kugel vorherigeKugel = topf;
      for (int i = 0; i < stelle - 1; i++) { vorherigeKugel = vorherigeKugel.getNächste(); }

      entnommene = vorherigeKugel.getNächste(); // merken, welche Kugel aus der Kette entnommen wird
      vorherigeKugel.entferneNächste(); // die Kette ohne die Entnommene 
      entnommene.setLetzte(); // der Zeiger auf die weiteren Kugeln muss null werden
      if (gezogene == null)
        gezogene = entnommene;
      else
        gezogene.append(entnommene);
    }
  }
  
  public void zieheAlle() {
    for (int i = 0; i < anzahlGezogene; i++) ziehe();
  }

  // Ausgabe
  private void ausgabe(Kugel k) {
    while (k != null) {
      System.out.print(k+" ");
      k = k.getNächste();
    }
    System.out.println();
  }
  public void ausgabeAlle() {
    ausgabe(gezogene);
    ausgabe(topf);
  }
}