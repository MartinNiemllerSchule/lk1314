package laufzeitverhalten;

import java.util.Arrays;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * @author lennart.harms
 */
public class SelectionSort {
    //http://inf-schule.de/grenzen/komplexitaet/sortieren/sortieralgorithmen/selectionsort
    static long dauer;

    public static void sort(Adresse[] daten) {

        long time = System.nanoTime();
        int m; //Minimum - kleinstes Element im unsortierten Bereich
        Adresse tausch; // ein Element vom Datentyp des Feldes
        for (int l = 0; l < daten.length - 1; l++) {
            // l - links - definiert unsortierten Bereich
            m = l;
            for (int i = l + 1; i < daten.length; i++) {
                if (daten[m].compareTo(daten[i]) > 0) {
                    m = i;
                }
            }
            if (m > l) {
                tausch = daten[l];
                daten[l] = daten[m];
                daten[m] = tausch;
            }
        }
        dauer = NANOSECONDS.toMillis(System.nanoTime() - time);
    }

    public static long getDauer() {
        return dauer;
    }
}
