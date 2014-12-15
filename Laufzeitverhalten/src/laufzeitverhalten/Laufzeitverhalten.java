/*
 * Laufzeitverhalten von Sortieralgorithmen untersuchen
 * siehe http://www.inf-schule.de/grenzen/
 */
package laufzeitverhalten;

import com.Ostermiller.util.CSVParser;

import java.io.*;
import java.util.Arrays;

/**
 * @author frank.baethge
 */
public class Laufzeitverhalten {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        /* alle Adressen einlesen */
        CSVParser csvParser = new CSVParser(new FileInputStream("./data/adressdaten.csv"));
        String[][] s = csvParser.getAllValues();
      /* Adressen als Objekte im Feld speichern */
        Adresse[] adressen = new Adresse[s.length];
        for (int zeile = 0; zeile < adressen.length; zeile++) {
            adressen[zeile] = new Adresse(s[zeile][0], s[zeile][1], s[zeile][2], s[zeile][3], s[zeile][4]);
        }

        int intervalanzahl = adressen.length / 1000;
        long[] java = new long[intervalanzahl];
        long[] merge = new long[intervalanzahl];
        long[] bubble = new long[intervalanzahl];
        long[] quick = new long[intervalanzahl];
        long[] select = new long[intervalanzahl];
        long[] insert = new long[intervalanzahl];


        for (int i = 1; i <= 1/*intervalanzahl*/; i++) {
            //HIER            
            JavaSort.sort(copyArray((i) * (adressen.length / intervalanzahl), adressen));
            MergeSort.sort(copyArray((i) * (adressen.length / intervalanzahl), adressen));
            SelectionSort.sort(copyArray((i) * (adressen.length / intervalanzahl), adressen));
            Quicksort.sort(copyArray((i) * (adressen.length / intervalanzahl), adressen));
            Bubblesort.sort(copyArray((i) * (adressen.length / intervalanzahl), adressen));
            InsertSort.sort(copyArray((i) * (adressen.length / intervalanzahl), adressen));

            java[i - 1] = JavaSort.getDauer();
            merge[i - 1] = MergeSort.getDauer();
            select[i - 1] = SelectionSort.getDauer();
            quick[i - 1] = Quicksort.getDauer();
            bubble[i - 1] = Bubblesort.getDauer();
            insert[i - 1] = InsertSort.getDauer();
            //HIER
        }
        System.out.println("n,Javasort;Mergesort,Bubblesort,Quicksort,Selectionsort,InsertSort");
        for (int i = 0; i < intervalanzahl; i++) {
            System.out.println(
                    ((i + 1) * adressen.length / intervalanzahl) + "," + java[i] + "," + merge[i] + "," + bubble[i]
                            + "," + quick[i] + "," + select[i] + "," + insert[i]);
        }
    }

    public static Adresse[] copyArray(int number, Adresse[] adr) {
        if (number > adr.length || number < 1)
            number = adr.length;
        Adresse[] arr = new Adresse[number];

        for (int i = 0; i < number; i++) {
            arr[i] = adr[i];
        }
        return arr;
    }

    public static void ausgabe(Adresse[] adr) {
        for (Adresse a : adr) {
            System.out.println(a);
        }
    }
}