package pzz;

/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author frank.baethge
 */
public class Primzahl {
  public static boolean isPrime(int zahl) {
    boolean istPz = (zahl % 2 != 0);
    if (!istPz) { 
      return false; 
    }
    for (int i = 3; i <= Math.sqrt(zahl); i += 2) {
      if (zahl % i == 0) {
        istPz = false;
        break;
      }
    }
    return istPz;
  }
}
