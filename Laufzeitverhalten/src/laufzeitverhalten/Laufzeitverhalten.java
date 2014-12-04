/*
 * Laufzeitverhalten von Sortieralgorithmen untersuchen
 * siehe http://www.inf-schule.de/grenzen/
 */
package laufzeitverhalten;
import com.Ostermiller.util.CSVParser;
import java.io.*;
import java.util.Arrays;

/**
 *
 * @author frank.baethge
 */
public class Laufzeitverhalten {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws FileNotFoundException, IOException {
    /* alle Adressen einlesen */
    CSVParser csvParser = new CSVParser( new FileInputStream("./data/adressdaten.csv") );
    String[][] s = csvParser.getAllValues();
    Adresse[] adressen = new Adresse[s.length];
    for (int zeile = 0; zeile < s.length; zeile++) {
      adressen[zeile] = new Adresse(s[zeile][0],s[zeile][1],s[zeile][2],s[zeile][3],s[zeile][4]);
//  System.out.println("plz: "+adressen[zeile].p);
    }

    // Sortieren nach Nach- und Vornamen mit der in Java eingebauten Routine
    javaSort(adressen);
  }

  public static void javaSort(Adresse[] adr) {
    Arrays.sort(adr);
  }
}
