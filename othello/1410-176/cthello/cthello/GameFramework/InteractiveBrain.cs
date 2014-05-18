using System;
using System.Threading.Tasks;

namespace GameFramework
{
  // Eine Spieler-Klasse, die ihre Entscheidung einem menschlichen
  // Spieler überlässt, der eine Client-Anwendung bedient.
  public class InteractiveBrain<TMove> : Brain<TMove>
  {
    protected TaskCompletionSource<TMove> _selectorTcs;

    // Als asynchrone Methode gibt SelectMoveAsync() einen Task zurück,
    // der dann "fertig" signalisiert, wenn der Benutzer im Client einen Zug
    // ausgewählt hat.
    public override Task<TMove> SelectMoveAsync(Game<TMove> game, int player)
    {
      if(_selectorTcs != null)
        throw new InvalidOperationException("Can only wait for one move.");

      _selectorTcs = new TaskCompletionSource<TMove>();
      return _selectorTcs.Task;
    }

    public override bool IsInteractive
    {
      get { return true; }
    }

    // Ein Client muss Attach() aufrufen, um diesen InteractiveBrain über
    // die Zugauswahl des Benutzers informieren zu können.
    public override void Attach(IMoveSelector<TMove> selector)
    {
      selector.MoveSelected += MoveSelected;
    }

    // Detach() hebt das Ereignis-Abonnenement auf und sollte immer aufgerufen
    // werden, bevor dieser InteractiveBrain aus dem Scope geht - anderenfalls
    // drohen Speicherlecks.
    public override void Detach(IMoveSelector<TMove> selector)
    {
      selector.MoveSelected -= MoveSelected;
    }

    // Hier landen Benachrichtigungen des Clients über die Zugauswahl des Benutzers.
    void MoveSelected(object sender, MoveSelectedEventArgs<TMove> e)
    {
      if(_selectorTcs != null) {
        // SetResult() signalisiert den Abschluss der Task, die SelectMoveAsync()
        // zurückgegeben hat.
        _selectorTcs.SetResult(e.Move);
        _selectorTcs = null;
      }
    }

  }

  // Dieses Interface muss eine Client-Anwendung (genauer: eines ihrer Fenster) implementieren.
  // Über das Ereignis MoveSelected benachrichtigt sie dann den InteractiveBrain, welchen Zug
  // der Benutzer gewählt hat.
  public interface IMoveSelector<TMove>
  {
    event MoveSelectEventHandler<TMove> MoveSelected;
  }

  public delegate void MoveSelectEventHandler<TMove>(object sender, MoveSelectedEventArgs<TMove> e);

  public class MoveSelectedEventArgs<TMove> : EventArgs
  {
    public MoveSelectedEventArgs(TMove move)
    {
      Move = move;
    }

    public TMove Move { get; private set; }
  }
}
