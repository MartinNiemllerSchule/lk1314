using System;
using System.Threading.Tasks;

namespace GameFramework
{
  // Abstrakte Basisklasse für die Klassen, die Spieler-Klassen, die über den nächsten Zug entscheiden.
  public abstract class Brain<TMove>
  {
    public Brain()
    {
    }

    // Asynchrone Methode!
    public abstract Task<TMove> SelectMoveAsync(Game<TMove> game, int player);

    // Interaktive Ableitungen sollten diese Methode überschreieben und true zurückgeben.
    public virtual bool IsInteractive
    {
      get { return false; }
    }

    // Wird nur in interaktiven Ableitungen gebraucht.
    public virtual void Attach(IMoveSelector<TMove> selector)
    { }

    // Wird nur in interaktiven Ableitungen gebraucht.
    public virtual void Detach(IMoveSelector<TMove> selector)
    { }

    public virtual string GetStatistics()
    {
      return null;
    }

  }
}
