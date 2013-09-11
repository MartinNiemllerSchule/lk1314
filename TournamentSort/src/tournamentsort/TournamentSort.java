/*
 * TournamentSort 
 * mit Objekten
 */
package tournamentsort;

/**
 *
 * @author Frank
 */
public class TournamentSort {

  private static Element sieger = new Element();
  private static int anzahl = 10; // TODO: im Moment geht das Programm nur mit gerader Anzahl 

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // TODO code application logic here
    baueBaum();
    System.out.println(sieger.toString());
    sieger.ersterDurchgang();
    System.out.println("\n");
    System.out.println(sieger.toString());
    System.out.println("\n");
    
    entnimmAlle();
  }

  private static void entnimmAlle() {
    System.out.println(sieger.sortiere());
  }
  
  private static void baueBaum() {
    // erstellt den oberen Teil des Baumes, 
    int anz = 1;
    while (2 * anz < anzahl) {
      // fügt eine komplette Ebene an
      sieger.baueEbene();
      anz *= 2;
      System.out.println("anz: " + anz);
    }
    
    // fülle die Ast-Enden mit den Elementen, die die Zufallszahlen tragen
    sieger.fülleEbene(anzahl);
  }
}
