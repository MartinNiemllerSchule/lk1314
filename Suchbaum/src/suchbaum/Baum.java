/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package suchbaum;

/**
 *
 * @author frank.baethge
 */
public class Baum {
  private Knoten wurzel;
  
  public Baum() {
    wurzel = null;
  }
  
  public void einfügen(Knoten k) {
    if (wurzel == null) {
      wurzel = k;
    } else {
      wurzel.einfügen(k);
    }
  }
  
  @Override
  public String toString() { 
    if (wurzel == null) {
      return "der Baum ist leer";
    } else {
      return wurzel.inorder();
    }
  }
  
  protected String getGraphviz(){
    return "digraph g {\n"
            + " graph [\n"
            + "  rankdir = \"TB\"\n"
            + "  bgcolor = \"white:lightblue\"\n"
            + "  style=\"filled\"\n"
            + "  gradientangle = 270\n"
            + " ];\n"
            + " node [shape=box,style=filled,color=\"lightgray\"];\n"
            + wurzel.getGraphviz()
            + "\n}";
  }
}
