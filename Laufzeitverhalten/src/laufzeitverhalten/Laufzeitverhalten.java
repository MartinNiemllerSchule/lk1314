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
 *
 * /usr/lib/jvm/java-7-openjdk-i386/bin/java -Didea.launcher.port=7532 -Didea.launcher.bin.path=/home/frank/Downloads/idea-IC-139.224.1/bin -Dfile.encoding=UTF-8 -classpath /usr/lib/jvm/java-7-openjdk-i386/jre/lib/charsets.jar:/usr/lib/jvm/java-7-openjdk-i386/jre/lib/management-agent.jar:/usr/lib/jvm/java-7-openjdk-i386/jre/lib/javazic.jar:/usr/lib/jvm/java-7-openjdk-i386/jre/lib/jce.jar:/usr/lib/jvm/java-7-openjdk-i386/jre/lib/resources.jar:/usr/lib/jvm/java-7-openjdk-i386/jre/lib/rt.jar:/usr/lib/jvm/java-7-openjdk-i386/jre/lib/compilefontconfig.jar:/usr/lib/jvm/java-7-openjdk-i386/jre/lib/jsse.jar:/usr/lib/jvm/java-7-openjdk-i386/jre/lib/rhino.jar:/usr/lib/jvm/java-7-openjdk-i386/jre/lib/ext/sunjce_provider.jar:/usr/lib/jvm/java-7-openjdk-i386/jre/lib/ext/dnsns.jar:/usr/lib/jvm/java-7-openjdk-i386/jre/lib/ext/localedata.jar:/usr/lib/jvm/java-7-openjdk-i386/jre/lib/ext/icedtea-sound.jar:/usr/lib/jvm/java-7-openjdk-i386/jre/lib/ext/java-atk-wrapper.jar:/usr/lib/jvm/java-7-openjdk-i386/jre/lib/ext/zipfs.jar:/usr/lib/jvm/java-7-openjdk-i386/jre/lib/ext/sunpkcs11.jar:/home/frank/workspace/lk1314/Laufzeitverhalten/out/production/Laufzeitverhalten:/home/frank/workspace/lk1314/Laufzeitverhalten/build/classes/ostermillerutils-1.08.02-javadoc.jar:/home/frank/workspace/lk1314/Laufzeitverhalten/build/classes/ostermillerutils-1.08.02.jar:/home/frank/workspace/lk1314/Laufzeitverhalten/build/classes/ostermillerutils-1.08.02-sources.jar:/home/frank/Downloads/idea-IC-139.224.1/lib/idea_rt.jar com.intellij.rt.execution.application.AppMain laufzeitverhalten.Laufzeitverhalten

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
        
        int intervalanzahl = 22;
        long[] java = new long[intervalanzahl];
        long[] merge = new long[intervalanzahl];
        long[] bubble = new long[intervalanzahl];
        long[] quick = new long[intervalanzahl];
        long[] select = new long[intervalanzahl];
        
        for(int i = 1; i < intervalanzahl; i++){
            //HIER            
            JavaSort.sort(copyArray((i) * (adressen.length / intervalanzahl), adressen));
            MergeSort.sort(copyArray((i) * (adressen.length / intervalanzahl), adressen));
            SelectionSort.sort(copyArray((i) * (adressen.length / intervalanzahl), adressen));
            Quicksort.sort(copyArray((i) * (adressen.length / intervalanzahl), adressen));
            Bubblesort.sort(copyArray((i) * (adressen.length / intervalanzahl), adressen));
            
            java[i] = JavaSort.getDauer();
            merge[i] = MergeSort.getDauer();
            select[i] = SelectionSort.getDauer();
            quick[i] = Quicksort.getDauer();
            bubble[i] = Bubblesort.getDauer();
            //HIER
        }
        System.out.println("n;Javasort;Mergesort;Bubblesort;Quicksort;Selectionsort;");
        for(int i = 1; i <= intervalanzahl; i++){
            System.out.println(
                (adressen.length*i/intervalanzahl)+";"+
                    java[i] + ";" +merge[i] + ";" + bubble[i] + ";" + quick[i] + ";" + select[i] + ";");
        }        
    }
    public static Adresse[] copyArray(int number, Adresse[] adr){
        if(number > adr.length ||number < 1)
            number = adr.length;
        Adresse[] arr = new Adresse[number];
        
        for (int i = 0; i < number; i++) {
            arr[i] = adr[i];
        }
        return arr;
    }
    public static void ausgabe(Adresse[] adr){
        for (Adresse a : adr) {
            System.out.println(a);
        }
    }
}