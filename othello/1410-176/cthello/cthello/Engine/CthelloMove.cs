using System;

namespace Cthello.Engine
{
  // Primitive Datenklasse, die einen Zug im Othello.Spiel speichert.
  // Der jeweilige Spieler ist als int kodiert; 0 ist Schwarz und 1 ist Weiß.
  public class CthelloMove
  {
    private int _playerId;
    public int Player
    {
      get { return _playerId; }
    }

    private int _row;
    public int Row
    {
      get { return _row; }
    }

    private int _column;
    public int Column
    {
      get { return _column; }
    }

    private bool _pass;
    public bool Pass
    {
      get { return _pass; }
    }

    public CthelloMove(int playerId, int row, int column, bool pass = false)
    {
      _playerId = playerId;
      _row = row;
      _column = column;
      _pass = pass;
    }

    private CthelloMove()
    {
    }

    public string Coordinates
    {
      get
      {
        if(_pass)
          return "Passe";
        else
          return "ABCDEFGH"[_column] + (_row + 1).ToString();
      }
    }
  }
}
