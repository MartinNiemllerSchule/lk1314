/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dameproblem;

/**
 *
 * @author frank.baethge
 */
public class Dameproblem {
    private static int[][] brett = new int[8][8];
    private static int anzahl;
    /**
     * @param args the command line arguments
     */
    private static void setze(int zeile, int spalte, int addiere) {
        brett[zeile][spalte] -= addiere;
        // sperre Zeile
        for (int i = 0; i < 8; i++) {
            if (i != spalte) {
                brett[zeile][i] += addiere;
            }
        }
        // sperre Spalte
        for (int i = 0; i < 8; i++) {
            if (i != zeile) {
                brett[i][spalte] += addiere;
            }
        }
        // sperre Diagonalen
        int z = zeile +  1;
        int s = spalte + 1;
        while ( (s < 8) && (z < 8)  ) {
            brett[z][s] += addiere;
            s++;
            z++;
        }
        z = zeile -  1;
        s = spalte - 1;
        while ( (0 <= s) && (0 <= z)  ) {
            brett[z][s] += addiere;
            s--;
            z--;
        }
        z = zeile +  1;
        s = spalte - 1;
        while ( (0 <= s) && (z < 8)  ) {
            brett[z][s] += addiere;
            s--;
            z++;
        }
        z = zeile -  1;
        s = spalte + 1;
        while ( (s < 8) && (0 <= z)  ) {
            brett[z][s] += addiere;
            s++;
            z--;
        }

    }
    private static int nächsteFreieSpalte(int zeile, int spalte) {
      while ((spalte < 8) && (brett[zeile][spalte] != 0)) spalte++;
      return spalte;
    }
    private static void dame(int zeile, int spalte) {
      int nfs = nächsteFreieSpalte(zeile, spalte);
      if (nfs < 8) { // es geht weiter
        if (zeile == 7) { // Lösung gefunden -> Ausgabe
          setze(zeile,nfs,1);
          zeichne();
          setze(zeile,nfs,-1);
        } else { // gehe in die nächste Zeile
          setze(zeile,nfs,1);
          dame(zeile + 1, 0);
          setze(zeile,nfs,-1);
          if (nfs < 7) dame(zeile,nfs+1);
        }
      } // else keine Lösung gefunden -> zurück
    }
    private static void zeichne() {
        for (int z = 0; z < 8; z++) {
            for (int s = 0; s < 8; s++) {
                if (brett[z][s] == -1) {
                    System.out.print(" D");
                } else {
                    System.out.print(" "+brett[z][s]);
                }
            }
            System.out.println();
        }
        System.out.println(++anzahl);        
    }
    public static void main(String[] args) {
        // TODO code application logic here
        dame(0,0);
    }
}
