/*
 * Laufzeitverhalten von Sortieralgorithmen untersuchen
 * siehe http://www.inf-schule.de/grenzen/
 */
package laufzeitverhalten;
import java.util.Arrays;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
/**
 *
 * @author frank.baethge
 */
public class Bubblesort {
  static long dauer = 0;

  public static long getDauer() {
    return dauer;
  }

  public static void sort(Adresse[] adr) {
    long time = System.nanoTime();
    Adresse a;
    for (int i = adr.length - 1; i > 0; i--) {
      for (int n = 0; n < i; n++) {
        if (adr[n].compareTo(adr[n + 1]) > 0) {
          a = adr[n];
          adr[n] = adr[n + 1];
          adr[n + 1] = a;
        }
      }

    }
    dauer=NANOSECONDS.toMillis(System.nanoTime()-time);
  }

}
        