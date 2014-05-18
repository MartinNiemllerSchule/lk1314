using System;
using System.Collections.Generic;
using System.Collections.Immutable;

namespace GameFramework
{
  // Die abstrakte Klasse Game verwaltet nicht viel mehr als den Spielverlauf.
  // Der Typparameter TMove muss eine Klasse angeben, deren Ojekte Spielzüge darstellen.
  public abstract class Game<TMove>
  {
    protected ImmutableList<TMove> _history;

    public Game()
    {
    }

    public Game(Game<TMove> other)
    {
      _history = other._history;
    }

    // Init() ist bei neuen Objekten nach dem Konstruktor aufzurufen.
    // Die Trennung spart eine überflüssige Initialisierung von Kopien.
    public virtual Game<TMove> Init()
    {
      _history = ImmutableList<TMove>.Empty;
      return this;
    }

    protected void AddMove(TMove move)
    {
      _history = _history.Add(move);
    }

    public abstract string GetLog();

    // Diese Methode ist in abgeleiteten Klassen zu überschreiben.
    // Sie muss eine Kopie des aktuellen Objekts zurückgeben, in der der Zug durchgeführt wurde.
    public abstract Game<TMove> MakeMove(TMove move);

    public abstract bool IsMoveLegal(TMove move);

    public abstract IEnumerable<TMove> MovesFor(int player);

    public abstract bool IsGameOver
    {
      get;
    }

    public abstract Func<TGame, int, int> GetEvaluator<TGame>(int evalId);
  }
}
