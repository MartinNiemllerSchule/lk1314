package laufzeitverhalten;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * Created by frank on 13.12.14.
 */
public class InsertSort {
    static long dauer = 0;

    public static long getDauer() {
        return dauer;
    }

    public static void sort(Adresse[] daten) {
        Adresse a;
        long time = System.nanoTime();
        for (int l = 1; l < daten.length; l++) {
            int n = l - 1;
            while (n >= 0 && daten[n].compareTo(daten[n+1]) > 0){
                a = daten[n];
                daten[n] = daten[n + 1];
                daten[n + 1] = a;
                n--;
            }
        }
        dauer = NANOSECONDS.toMillis(System.nanoTime() - time);
    }
}
