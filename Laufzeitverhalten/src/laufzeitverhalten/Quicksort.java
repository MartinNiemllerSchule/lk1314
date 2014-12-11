/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laufzeitverhalten;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 *
 * @author elias.pflume
 */
public class Quicksort {

    /* public static void start() {
     int[] a = new int[100];
     for (int i = 0; i < a.length; i++) {
     a[i] = (int) (Math.random() * 100);
     }
     quicksort(a, 0, (a.length - 1));
     for (int i = 0; i < a.length; i++) {
     System.out.println(a[i]);
     }
     }*/
    static long start;
    static long ende;

    public static long getDauer() {
        long zeit = ende - start;
        return NANOSECONDS.toMillis(zeit);

    }

    public static void sort(Adresse[] a) {
        start = System.nanoTime();
        quicksort(a, 0, (a.length - 1));
        ende = System.nanoTime();
    }

    public static void quicksort(Adresse[] a, int links, int rechts) {
        if ((rechts - links) <= 1) {
            return;
        }
        Adresse p = a[links];
        //System.out.println("p=" + p + " links: " + links + " rechts: " + rechts);
        int z1 = links;
        int z2 = rechts;
        do {
            if (a[z1].compareTo(p) > 0) {
                if (a[z2].compareTo(p) <= 0) {
                    Adresse zwischenwert = a[z1];
                    a[z1] = a[z2];
                    a[z2] = zwischenwert;
                } else {
                    z2--;
                }
            } else {
                z1++;
            }
        } while (z1 < z2);
        Adresse zwischenwert = a[z1 - 1];
        a[z1 - 1] = a[links];
        a[links] = zwischenwert;
        quicksort(a, links, (z1 - 1));
        quicksort(a, z2, rechts);
    }
}
