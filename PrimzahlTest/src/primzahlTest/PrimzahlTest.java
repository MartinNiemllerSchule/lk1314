/*
 * In einer Liste werden Primzahlen gespeichert (und eventuell auch berechnet)
 * und für große Zahlen überprüft, ob ein Teiler gefundenen werden kann.
 * Mögliche weitere Ausbaustufen: Faktorisierung der großen Zahl
 */
package primzahlTest;
import  java.math.BigInteger;

/**
 *
 * @author frank.baethge
 */
public class PrimzahlTest {

  private static primzahlTest.PrimzahlListe erste;
  
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    erste =  new primzahlTest.PrimzahlListe(2L);
    erste.anhängen(new primzahlTest.PrimzahlListe(3L));

    BigInteger zahl0 = new BigInteger("2017");
    BigInteger zahl1 = new BigInteger("383095318228681");
    BigInteger zahl2 = new BigInteger("40610371693540247718059");

    BigInteger teilerVonZahl1 = erste.findeFaktor(zahl1);
    if (teilerVonZahl1 != BigInteger.ZERO) System.out.println("Teiler gefunden: "+teilerVonZahl1);

    // und Ausgeben der gefundenen Primzahlen
    // erste.ausgeben();
  }

}