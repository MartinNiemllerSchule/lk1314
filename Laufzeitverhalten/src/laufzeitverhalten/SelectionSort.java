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
/*        int k = 0;
        int s1 = 0;     //Kleinstes Element
		Adresse tausch;

		for (int b = 0; b < Daten.length; b++) {
			for (int i = k; i < Daten.length; i++) { // k definiert sortierten Bereich, k erstes unsortiertes Element
				if (Daten[s1].compareTo(Daten[i]) <= 0) {
				} else{
					s1 = i;
				}
			}
			tausch = Daten[k];
			Daten[k] = Daten[s1];
			Daten[s1] = tausch;
			k++;
		}
*/
        dauer = NANOSECONDS.toMillis(System.nanoTime() - time);
/*
		for(int i = 0; i<daten.length;i++)
		{
			System.out.println(i + ". Datensatz: " + daten[i].n);
		}
		System.out.println("Die Laufzeit des Programmes mit " + Daten.length() + dauer);
*/

    }

    public static long getDauer() {
        return dauer;
    }
}
