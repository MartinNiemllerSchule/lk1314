package pzz;
import java.util.*;

/**
 * Stellt alle Funktionen für den Umgang mit Primzahlen bereit
 * @author frank.baethge
 */
public class Primzahl {
  private List<Integer> primzahlen;
  /**
   * gibt die letzte Primzahl in der Primzahlliste zurück
   * @return letztePrimzahl in primzahlen
   */
  private Integer letzte() { return primzahlen.get(primzahlen.size()-1); }
  /**
   * ergänzt noch fehlende Primzahlen in der Liste bis bisZahl
   * @param bisZahl 
   */
  private void ergänze(Integer bisZahl) {
    Integer nächsteZahl = letzte();
    do {
      nächsteZahl += 2;
      if (isPrime(nächsteZahl)) {
        primzahlen.add(nächsteZahl);
      }
    } while (nächsteZahl <= bisZahl);
  }
  /**
   * Konstruktoren
   * @param bisZahl 
   */
  public Primzahl(Integer bisZahl) {
    this.primzahlen = new LinkedList<>();
    primzahlen.add(2);
    primzahlen.add(3);
    ergänze(bisZahl);
    System.out.println("PZ:"+primzahlen);
  }
  public Primzahl() {
    this.primzahlen = new LinkedList<>();
    primzahlen.add(2);
    primzahlen.add(3);
    ergänze(100);    
  }
  /**
   * Überschreibe toString() -> Ausgabe aller Primzahlen zur Kontrolle
   * @return 
   */
  @Override
  public String toString() {
    return primzahlen.toString();
  }
  /**
   * Überprüft, ob eine Primzahl übergeben wurde oder nicht
   * @param zahl
   * @return wahr, falls zahl eine Primzahl ist
   */
  public boolean isPrime(int zahl) {
    // Überprüfe ob noch weitere Primzahlen berechnet werden müssen
    int wurzel = (int) Math.ceil(Math.sqrt(zahl));
    if (letzte() < wurzel) {
      ergänze(wurzel);
    }
    // versuche die Primzahl in der Liste zu finden
    //TODO - unfertig (eigentlich stehen ja alle pz in der Liste (aber die Berechnung muss auch ausgeführt werden)
    // Entscheide, ob eine Primzahl vorliegt
    Boolean teilerGefunden = false;
    Integer i = 0;
    while (!teilerGefunden && (i < primzahlen.size()) && (primzahlen.get(i) <= Math.sqrt(zahl))) {
      teilerGefunden = (zahl % primzahlen.get(i) == 0);
      i++;
    }
    return !teilerGefunden;
  }
  public boolean gibNächstePZZwillinge(int vonZahl) {
    
  }
}
