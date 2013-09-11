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
    private static int anzahl = 8;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        baueBaum();
    }
    
    private static void baueBaum() {
        int anz = 1;
        while ( 2 * anz < anzahl) {
            // fügt eine komplette Ebene an
            sieger.baueEbene();
            anz *= 2;
            System.out.println("anz: "+anz);
        }
    }
    
    private static void fülleBaum() {
        
    }
}
