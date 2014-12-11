package laufzeitverhalten;

import java.util.Arrays;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 *
 * @author lennart.harms
 */
public class SelectionSort {
    //http://inf-schule.de/grenzen/komplexitaet/sortieren/sortieralgorithmen/selectionsort
    static long dauer;
    static int o;
    // Hier kann man Werte verändern
    static int Messungen = 20;
    static int Schritt = 500;
    static int[] dauernElemente = new int[Messungen];
    static long[] dauern = new long[Messungen];
    
    public static void sort(Adresse[] Daten2){
        int anzahlDaten = Daten2.length;
        Adresse[] Daten = new Adresse[anzahlDaten];
        Daten = Arrays.copyOf(Daten2,anzahlDaten);
        
        long time = System.nanoTime();
        int k = 0;
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
//        for(int i = 0; i<Daten.length;i++)
//        {
//            System.out.println(i+". Datensatz: "+Daten[i].n);
//        }
        dauer = NANOSECONDS.toMillis(System.nanoTime()-time);
//        System.out.println("Die Laufzeit des Programmes mit "+ Daten.length()+ dauer);
        
    }
    public static long getDauer(){
        return dauer;
    }
}
