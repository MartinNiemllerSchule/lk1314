/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tournamentsort;

/**
 *
 * @author Frank
 */
public class Element {
    private int füllung;
    private boolean gefüllt;
    private Element mom = null;
    private Element dad = null;
    
    public Element() {
        gefüllt = false;
    }
    public Element(int f) {
        füllung = f;
        gefüllt = true;
    }
    public void baueEbene() {
        if (mom != null) {
            mom.baueEbene();
            dad.baueEbene();
        } else {
            mom = new Element();
            dad = new Element();
        }
    }
}
