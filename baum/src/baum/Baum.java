/*
 * binäre Bäume
 *  und wie man damit umgeht
 * 
 * Thema: Komprimiere einen Text
 * Beispiel: iQ1Bäume_131203
 * "Bäume sind auch in der Informatik ein wichtiges Thema. Viele Daten werden zunächst in Bäumen abgespeichert. Sie werden vor allem zur Suche und Sortierung verwendet." 
 * 
 * bessere Fassung wäre
 * "Bäume sind auch in der Informatik ein wichtiges Thema. Viele Daten werden zunächst in Binärbäumen abgespeichert. Bäume werden beispielsweise zur Suche und Sortierung verwendet."
 * 
 * in der Stunde ausgezählt:
 * "Bäume sind auch in der Informatik ein wichtiges Thema. Viele"
 */
package baum;

/**
 *
 * @author frank.baethge
 */
public class Baum {
  private static Knoten wurzel;
  private static String getGraphviz(){
    return "digraph g {\n"
            + " graph [\n"
            + "  rankdir = \"TB\"\n"
            + "  bgcolor = \"white:lightblue\"\n"
            + "  style=\"filled\"\n"
            + "  gradientangle = 270\n"
            + " ];\n"
            + " node [shape=box,style=filled,color=\"lightgray\"];\n"
            + wurzel.getGraphviz(0)
            + "\n}";
  }
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    
    erstelleBaum();
    System.out.println("Bitlänge des komprimierten Textes: " + wurzel.berechneBitlänge(0));
    //System.out.println("Wurzel: " + wurzel.ausgeben(""));
    // System.out.println("Graphviz-Ausgabe: \n"+getGraphviz());
    // rotiere Wurzel links
    /*
    Knoten alteWurzel = wurzel;
    wurzel = wurzel.getRechts();
    alteWurzel.setRechts(wurzel.getLinks());
    wurzel.setLinks(alteWurzel);
    System.out.println("Bitlänge des komprimierten Textes nach der Rotation: " + wurzel.berechneBitlänge(0));
    */
    Huffman h = new Huffman("Bäume sind auch in der Informatik ein wichtiges Thema. Viele Daten werden zunächst in Bäumen abgespeichert. Sie werden vor allem zur Suche und Sortierung verwendet.");
    wurzel = h.getWurzel();
    // System.out.println("Graphviz-Ausgabe: \n"+getGraphviz());
    System.out.println("Bitlänge des komprimierten Textes: " + wurzel.berechneBitlänge(0));
  }
  /**
   * erstellt den Baum für den gesamten Text: siebe Notebook-Datei oder Moodle
   */
  private static void erstelleBaum() {
    wurzel = new Knoten(
      /*81*/new Knoten(
        /*47*/new Knoten(new Blatt(' ',24), new Blatt('e',23)),
        /*34*/new Knoten(new Blatt('n',13),new Knoten(new Blatt('i',11),new Blatt('r',10)))
      ),
      /*83*/new Knoten(
        /*47*/new Knoten(
          /*7*/new Knoten(new Blatt('u',8),new Blatt('t',7)),
          /*12*/new Knoten(
            new Knoten(new Blatt('a',6),new Blatt('d',6)),
            new Knoten(new Blatt('h',6),new Blatt('c',5))
          )
        ),
        /*63*/new Knoten(
          /*20*/new Knoten(
            /*11*/new Knoten(
              new Knoten(new Blatt('w',4), new Blatt('.',3)),
              new Knoten(new Blatt('S',3), new Blatt('g',3))
            ),
            /*9*/new Knoten(
              new Knoten(new Blatt('m',5),new Blatt('s',4)),
              new Knoten(new Blatt('l',3), new Blatt('o',3))
            )
          ),
          /*20*/new Knoten(
            /*1*/new Knoten(
              /*3*/new Knoten(new Blatt('a',3),new Blatt('B',2)),
              /*4*/new Knoten(new Blatt('v',2),new Blatt('Z',2))
            ),
            /*2*/new Knoten(
              /*5*/new Knoten(
                /*11*/new Knoten(new Blatt('D',1),new Blatt('I',1)),
                /*12*/new Knoten(new Blatt('T',1),new Blatt('V',1))
              ),
              /*6*/new Knoten(
                /*13*/new Knoten(new Blatt('b',1),new Blatt('f',1)),
                /*14*/new Knoten(new Blatt('k',1),new Blatt('p',1))
              )
            )
          )
            )
      )
    );
  }
  /**
   * erstellt den Baum für die verkürzte Version des Textes (im Unterricht von Hand ausgezählt)
   */
  private static void erstelleKurzenText() {
    wurzel = new Knoten(
      new Knoten( //21
        new Knoten( //13
          new Blatt(' ',7), new Blatt('E',6)
        ),
        new Blatt('I',8)
      ),
      new Knoten( //32
        new Knoten( //16
          new Knoten( //13
            new Knoten( //7
              new Blatt('A',3), new Blatt('N',4)
            ),
            new Knoten( //6
              new Blatt('T',3), new Blatt('M',3)
            )
          ),
          new Blatt('H',3)
        ),
        new Knoten( //16
          new Knoten( //8
            new Blatt('R',2),
            new Knoten( //6
              new Knoten(
                new Blatt('U',2), new Blatt('S',2)
              ),
              new Knoten(
                new Blatt('.',1), new Blatt('L',1)
              )
            )
          ),
          new Knoten( //8
            new Knoten( //4
              new Knoten(
                new Blatt('K',1), new Blatt('W',1)
              ),
              new Knoten(
                new Blatt('G',1), new Blatt('V',1)
              )
            ),
            new Knoten( //4
              new Knoten(
                new Blatt('B',1), new Blatt('A',1)
              ),
              new Knoten(
                new Blatt('F',1), new Blatt('O',1)
              )
            )
          )
        )
      )
    );
  }
}