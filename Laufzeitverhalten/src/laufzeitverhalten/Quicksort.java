/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laufzeitverhalten;

import java.util.Arrays;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * @author elias.pflume
 */
public class Quicksort {

    static long start;
    static long ende;

    public static long getDauer() {
        long zeit = ende - start;
        return NANOSECONDS.toMillis(zeit);

    }

    public static void sort(Adresse[] a) {
        start = System.nanoTime();
        quickSort(a, 0, (a.length - 1));
        ende = System.nanoTime();
        System.out.println(Arrays.toString(a));
    }

    public static void quickSort(Adresse[] a, int links, int rechts) {
        if ((rechts - links) <= 1) {
            return;
        }
        Adresse pivot = a[links];
        int l = links + 1;
        int r = rechts;
        Adresse tausche;

        do {
            while (a[l].compareTo(pivot) <= 0 && l < rechts) {
                l++;
            }
            while (a[r].compareTo(pivot) >= 0 && links < r) {
                r--;
            }
            if (l < r) {
                tausche = a[l];
                a[l] = a[r];
                a[r] = tausche;
            }
        } while (l < r);
        if (a[r].compareTo(pivot) < 0) {
            tausche = a[r];
            a[r] = a[links];
            a[links] = tausche;
        }

        quickSort(a, links, r - 1);
        quickSort(a, r + 1, rechts);
    }
}
