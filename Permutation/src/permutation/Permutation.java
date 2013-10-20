/*
 * Zeigt alle Permutationen einer Reihe von Buchstaben an
 * verwendet Backtracking und folglich auch rekursives Programmieren
 */
package permutation;

/**
 *
 * @author Frank
 */
public class Permutation {
  private static String text = "ABCD";
  private static int anzahl = 0;
  /**
   * @param vor - Text der bereits in der richtigen Reihenfolge ist
   * @param pos - Position an der der Buchstabe entnommen wird
   * @param nac - Buchstaben die noch zur Verfügung stehen
   */
  private static void perm(String vor, int pos, String nac) {
    if (nac.length() == 1) {
      // letzter Buchstabe - es kann alles angezeigt werden
      System.out.println(vor + nac /* + " Anzahl: " + (++anzahl) */);
    } else {
      // ausschneiden des Buchstabens, anzeigen und rekursive Aufrufe
      String vNeu = vor + nac.charAt(pos);
      String tNeu = nac.substring(0, pos) + nac.substring(pos+1,nac.length());
      // eine "Ebene" tiefer und wieder von vorne anfangen
      perm(vNeu, 0, tNeu);
      // auf gleicher "Ebene" weitere Möglichkeit abarbeiten 
      if (pos + 1 <= nac.length() - 1)
        perm(vor, pos+1, nac);
    }
  }
  
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    perm("",0,text);
  }
}
