/*
 * binäre Bäume
 *  und wie man damit umgeht
 * 
 * Thema: Komprimiere einen Text
 * Beispiel: iQ1Bäume_131203
 * "Bäume sind auch in der Informatik ein wichtiges Thema. Viele Daten werden zunächst in Bäumen abgespeichert. Sie werden vor allem zur Suche und Sortierung verwendet." 
 * 
 */
package baum;

/**
 *
 * @author frank.baethge
 */
public class Baum {
  private static Ast wurzel;
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    wurzel = new Ast(
      new Ast( //21
        new Ast( //13
          new Blatt(' ',7), new Blatt('E',6)
        ),
        new Blatt('I',8)
      ),
      new Ast( //32
        new Ast( //16
          new Ast( //13
            new Ast( //7
              new Blatt('A',3), new Blatt('N',4)
            ),
            new Ast( //6
              new Blatt('T',3), new Blatt('M',3)
            )
          ),
          new Blatt('H',3)
        ),
        new Ast( //16
          new Ast( //8
            new Blatt('R',2),
            new Ast( //6
              new Ast(
                new Blatt('U',2), new Blatt('S',2)
              ),
              new Ast(
                new Blatt('.',1), new Blatt('L',1)
              )
            )
          ),
          new Ast( //8
            new Ast( //4
              new Ast(
                new Blatt('K',1), new Blatt('W',1)
              ),
              new Ast(
                new Blatt('G',1), new Blatt('V',1)
              )
            ),
            new Ast( //4
              new Ast(
                new Blatt('B',1), new Blatt('A',1)
              ),
              new Ast(
                new Blatt('F',1), new Blatt('O',1)
              )
            )
          )
        )
      )
    );
    System.out.println("Bitlänge des komprimierten Textes: " + wurzel.berechneBitlänge(0));
    //System.out.println("Wurzel: " + wurzel.ausgeben(""));
    
    // rotiere Wurzel links
    Ast alteWurzel = wurzel;
    wurzel = wurzel.getRechts();
    alteWurzel.setRechts(wurzel.getLinks());
    wurzel.setLinks(alteWurzel);
    System.out.println("Bitlänge des komprimierten Textes nach der Rotation: " + wurzel.berechneBitlänge(0));
    
  }
}
