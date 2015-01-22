/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package primzahlTest;
import  java.math.BigInteger;

/**
 *
 * @author frank.baethge
 */

public class PrimzahlListe {
  private BigInteger pz; // speichert die jeweilige Primzahl ab
  private PrimzahlListe nächste; // zeigt auf das nächste Listenelement
  
  // Konstruktor
  public PrimzahlListe() {
    pz = BigInteger.ZERO;
    nächste = null;
  }
  // Konstruktor
  public PrimzahlListe(BigInteger primzahl) {
    pz = primzahl;
    nächste = null;
  }
 // Konstruktor
  public PrimzahlListe(Long primzahl) {
    pz = new BigInteger(primzahl.toString());
    nächste = null;
  }  
  public void anhängen(PrimzahlListe p) {
    PrimzahlListe m = this;
    while (m.nächste != null) m = m.nächste;
    m.nächste = p;
  }
  public PrimzahlListe getNächste(PrimzahlListe e) {
    // falls es keine weitere Primzahl gibt, muss diese erst erstellt werden
    if (nächste == null) {
      Boolean gefunden = false;
      BigInteger p = pz;
      BigInteger z2 = new BigInteger("2");
      while (!gefunden) {
        p = p.add(z2);
        gefunden = e.istPrim(p);
      }
      PrimzahlListe ergebnis = new PrimzahlListe(p);
      anhängen(ergebnis);
      System.out.println(p);
      return ergebnis;
    } else {
      return nächste;
    }
  }
  
  public void ausgeben() {
    PrimzahlListe p = this;
    byte b = 0;
    while (p != null) {
      System.out.print(" " + p.pz);
      b++;
      if (b == 10) {
        System.out.println();
        b = 0;  
      }
      p = p.nächste;
    }
    System.out.println();
  }
  
  public boolean istPrim(BigInteger zahl) {
    PrimzahlListe p = this; // istPrim wird immer mit erste aufgerufen
    BigInteger wurzel = this.sqrt(zahl);
    while (p != null && p.pz.compareTo(wurzel) <= 0) {
      if (zahl.mod(p.pz) == BigInteger.ZERO) {
        return false;
      } else {
        p = p.nächste;
      }
    }
    return true;
  }

  public BigInteger findeFaktor(BigInteger zahl) {
    PrimzahlListe e = this; // findeFaktor wird immer mit erste aufgerufen
    PrimzahlListe p = this;
    BigInteger wurzel = this.sqrt(zahl);
    while (p.pz.compareTo(wurzel) <= 0) {
      if (zahl.mod(p.pz) == BigInteger.ZERO) {
        return p.pz;
      } else {
        p = p.getNächste(e);
      }
    }
    return BigInteger.ZERO;
  }

  /* http://faruk.akgul.org/blog/javas-missing-algorithm-biginteger-sqrt/ */
  public BigInteger sqrt(BigInteger n) {
    BigInteger a = BigInteger.ONE;
    BigInteger b = new BigInteger(n.shiftRight(5).add(new BigInteger("8")).toString());
    while (b.compareTo(a) >= 0) {
      BigInteger mid = new BigInteger(a.add(b).shiftRight(1).toString());
      if (mid.multiply(mid).compareTo(n) > 0) {
        b = mid.subtract(BigInteger.ONE);
      } else {
        a = mid.add(BigInteger.ONE);
      }
    }
    return a.subtract(BigInteger.ONE);
  }
}
