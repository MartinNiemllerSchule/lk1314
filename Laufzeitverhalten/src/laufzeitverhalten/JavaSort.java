/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laufzeitverhalten;

/**
 *
 * @author Hannes.Kuchelmeister
 */
import java.util.Arrays;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class JavaSort {
    static long startTime = 0;
    static long endTime = 0;
    
    public static void sort(Adresse inputArr[]) {
        startTime= System.nanoTime();
        Arrays.sort(inputArr);
        endTime = System.nanoTime();
    }
    public static long getDauer(){
        return NANOSECONDS.toMillis(endTime - startTime);
    }
}
