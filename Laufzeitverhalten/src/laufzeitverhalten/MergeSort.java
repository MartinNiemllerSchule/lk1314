package laufzeitverhalten;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hannes.kuchelmeister
 */
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class MergeSort {
    //Bottom-up

    static Adresse[] arr;
    static Adresse[] tmpArr;
    static long startTime = 0;
    static long endTime = 0;
    /**
     * 
     * @return gibt die Dauer des letztens sortiervorgangs zurück. 
     */
    public static long getDauer(){
        return NANOSECONDS.toMillis(endTime - startTime);
    }
    public static void sort(Adresse inputArr[]) {
        startTime= System.nanoTime();
        
        arr = inputArr;
        tmpArr = new Adresse[arr.length];
        doMergeSort(0, arr.length - 1);
        
        endTime = System.nanoTime();
    }
    private static void doMergeSort(int lowerIndex, int higherIndex) {

        if (lowerIndex < higherIndex) {
            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
            // links
            doMergeSort(lowerIndex, middle);
            // rechts
            doMergeSort(middle + 1, higherIndex);
            // beide seiten
            merge(lowerIndex, middle, higherIndex);
        }
    }
    private static void merge(int low, int mid, int high) {
        for (int i = low; i <= high; i++) {
            tmpArr[i] = arr[i];
        }
        int i = low;
        int j = mid + 1;
        int k = low;
        while (i <= mid && j <= high) {
            if (tmpArr[i].compareTo(tmpArr[j]) <= 0) {
                arr[k] = tmpArr[i];
                i++;
            } else {
                arr[k] = tmpArr[j];
                j++;
            }
            k++;
        }
        while (i <= mid) {
            arr[k] = tmpArr[i];
            k++;
            i++;
        }
    }
}
