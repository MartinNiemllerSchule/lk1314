using GameFramework;
using System;
using System.Collections.Generic;
using System.Collections.Immutable;
using System.Diagnostics;
using System.Linq;
using System.Text;

namespace Cthello.Engine
{
  public class CthelloGame : Game<CthelloMove>
  {
    // Gesetzte Bits in _board[0] sind schwarze, in _board[1] weiße Steine.
    private UInt64[] _board;

    public CthelloGame()
      : base()
    {
    }

    // Der statische Konstruktor initialisiert die Feldbewertungsmatrix.
    static CthelloGame()
    {
      BuildStoneWeights();
    }

    public CthelloGame(CthelloGame other)
      : base(other)
    {
      _board = new UInt64[2];
      other._board.CopyTo(_board, 0);
    }

    public override Game<CthelloMove> Init()
    {
      base.Init();
      BuildBoard();
      return this;
    }

    // Die Startaufstellung
    private void BuildBoard()
    {
      _board = new UInt64[2];
      _board[0] = (1ul << (3 * 8 + 4)) | (1ul << (4 * 8 + 3));
      _board[1] = (1ul << (3 * 8 + 3)) | (1ul << (4 * 8 + 4));
    }

    // Ist das Feld [row, column] von diesem Spieler besetzt?
    public bool IsStone(int playerId, int row, int column)
    {
      return (_board[playerId] & (1ul << row * 8 + column)) != 0;
    }

    // Was für ein Stein liegt auf Feld [row, column]? (Leer = -1)
    public int StoneAt(int row, int column)
    {
      if(IsStone(0, row, column))
        return 0;
      else if(IsStone(1, row, column))
        return 1;
      else
        return -1;
    }

    // Eine Kopie zurückgeben, in der der Zug gemacht wurde.
    public override Game<CthelloMove> MakeMove(CthelloMove move)
    {
      CthelloGame copy = new CthelloGame(this);
      copy.AddMove(move);
      if(!move.Pass) {
        copy.MoveAndFlip(move);
      }
      return copy;
    }

    // Einen neuen Stein setzen und alle eingeschlossenen Steine umdrehen
    protected void MoveAndFlip(CthelloMove move)
    {
      _board[move.Player] |= 1ul << 8 * move.Row + move.Column;
      for(int dir = 0; dir < 8; ++dir) {
        UInt64 flip = FlippedStones(move.Player, move.Row, move.Column, dir);
        _board[move.Player] |= flip;
        _board[1 - move.Player] &= ~flip;
      }
    }

    // Welche Steine in Richtung direction müssten umgedreht werden,
    // wenn Spieler playerId auf das Feld [row, column] setzen würde?
    // direction: 0 = N, 1 = NW, 2 = W, 3 = SW, 4 = S, 5 = SO, 6 = O, 7 = NO
    protected UInt64 FlippedStones(int playerId, int row, int column, int direction)
    {
      UInt64 result = 0;

      int dx = (direction % 4 == 0 ? 0 : 1) * (direction < 4 ? -1 : 1);
      int dy = ((direction + 2) % 4 == 0 ? 0 : 1) * ((direction + 2) % 8 < 4 ? -1 : 1);
      int opponentId = 1 - playerId;
      for(int d = 1; ; ++d) {
        int r = row + d * dy;
        int c = column + d * dx;
        if(r < 0 || r > 7 || c < 0 || c > 7) { // Am Rand angekommen => keine Reihe
          result = 0;
          break;
        }
        if(IsStone(playerId, r, c)) // Eigener Stein beendet die Reihe
          break;
        else if(IsStone(opponentId, r, c)) // Gegnerstein ist Umdreh-Kandidat
          result |= 1ul << 8 * r + c;
        else { // Leeres Feld => keine Reihe
          result = 0;
          break;
        }
      }
      return result;
    }

    public override bool IsMoveLegal(CthelloMove move)
    {
      return IsMoveLegal(move.Player, move.Row, move.Column, move.Pass);
    }

    // Ist dieser Zug erlaubt?
    protected bool IsMoveLegal(int playerId, int row, int column, bool pass = false)
    {
      if(pass) {
        // Passen geht nur, wenn es keinen anderen Zug gibt.
        return MovesFor(playerId).ElementAt(0).Pass;
      }
      else {
        if(StoneAt(row, column) != -1)
          // Feld schon besetzt => kein Zug
          return false;
        for(int dir = 0; dir < 8; ++dir) {
          if(FlippedStones(playerId, row, column, dir) != 0)
            // Es gibt (mindestens) eine gültige Reihe umzudrehender Steine => gültiger Zug
            return true;
        }
        // Keine Steine zum Umdrehen entdeckt => ungültig
        return false;
      }
    }

    // Das Spiel ist vorbei, wenn beide Spieler nacheinander gepasst haben.
    public override bool IsGameOver
    {
      get
      {
        return _history.Count >= 2
            && _history[_history.Count - 1].Pass
            && _history[_history.Count - 2].Pass;
      }
    }

    public override string GetLog()
    {
      StringBuilder sb = new StringBuilder();
      int i;
      string m1, m2, line;
      for(i = 0; i < _history.Count - 1; i += 2) {
        m1 = _history[i].Coordinates;
        m2 = _history[i + 1].Coordinates;
        line = string.Format("{0,2}: {1} - {2}", i / 2 + 1, m1, m2);
        sb.AppendLine(line);
      }
      if(i != _history.Count) {
        m1 = _history[i].Coordinates;
        line = string.Format("{0,2}: {1}", i / 2 + 1, m1);
        sb.AppendLine(line);
      }
      if(IsGameOver) {
        int s1 = CountStonesFor(0);
        int s2 = CountStonesFor(1);
        if(s1 > s2)
          line = string.Format("Schwarz gewinnt mit {0} : {1}.", s1, s2);
        else if(s2 > s1)
          line = string.Format("Weiß gewinnt mit {0} : {1}.", s2, s1);
        else
          line = string.Format("Unentschieden ({0} : {1})", s1, s2);
        sb.AppendLine();
        sb.AppendLine(line);
      }
      return sb.ToString();
    }

    // Naives Aufzählen der gültigen Züge Reihe für Reihe und Spalte für Spalte
    //public IEnumerable<CthelloMove> MovesFor(int playerId)
    //{
    //  int moveCount = 0;
    //  for(int row = 0; row < 8; ++row) {
    //    for(int column = 0; column < 8; ++column) {
    //      if(IsMoveLegal(playerId, row, column)) {
    //        ++moveCount;
    //        yield return new CthelloMove(playerId, row, column);
    //      }
    //    }
    //  }
    //  if(moveCount == 0) {
    //    yield return new CthelloMove(playerId, 0, 0, true);
    //  }
    //}

    // Aufzählen der gültigen Züge von innen nach außen
    public override IEnumerable<CthelloMove> MovesFor(int playerId)
    {
      int moveCount = 0;
      for(int shell = 2; shell >= 0; --shell) { // Die inneren Felder (shell = 3) sind ja von vornherein besetzt.
        int shell2 = 7 - shell;
        for(int pos = shell; pos < 4; ++pos) {
          int pos2 = 7 - pos;
          if(IsMoveLegal(playerId, pos, shell)) {
            ++moveCount;
            yield return new CthelloMove(playerId, pos, shell);
          }
          if(IsMoveLegal(playerId, pos2, shell)) {
            ++moveCount;
            yield return new CthelloMove(playerId, pos2, shell);
          }
          if(IsMoveLegal(playerId, pos, shell2)) {
            ++moveCount;
            yield return new CthelloMove(playerId, pos, shell2);
          }
          if(IsMoveLegal(playerId, pos2, shell2)) {
            ++moveCount;
            yield return new CthelloMove(playerId, pos2, shell2);
          }
          if(pos != shell) {
            if(IsMoveLegal(playerId, shell, pos)) {
              ++moveCount;
              yield return new CthelloMove(playerId, shell, pos);
            }
            if(IsMoveLegal(playerId, shell, pos2)) {
              ++moveCount;
              yield return new CthelloMove(playerId, shell, pos2);
            }
            if(IsMoveLegal(playerId, shell2, pos)) {
              ++moveCount;
              yield return new CthelloMove(playerId, shell2, pos);
            }
            if(IsMoveLegal(playerId, shell2, pos2)) {
              ++moveCount;
              yield return new CthelloMove(playerId, shell2, pos2);
            }
          }
        }
      }
      if(moveCount == 0) {
        yield return new CthelloMove(playerId, 0, 0, true);
      }
    }

    // Wie viele Steine hat dieser Spieler?
    public int CountStonesFor(int playerId)
    {
      UInt64 bits = _board[playerId];
      int stoneCount = 0;
      while(bits != 0) {
        ++stoneCount;
        bits &= (bits - 1);
      }
      return stoneCount;
    }

    static int[,] StoneWeights = null;
    const int MoveWeight = 10;

    // Gewichtete Werte der Spielfelder; siehe "Computer-Othello" bei wikipedia.de
    static void BuildStoneWeights()
    {
      StoneWeights = new int[8, 8] {
        { 50, -20, 10, 5, 5, 10, -20,  50},
        {-20, -30,  1, 1, 1,  1, -30, -20},
        { 10,   1,  1, 1, 1,  1,   1,  10},
        {  5,   1,  1, 1, 1,  1,   1,   5},
        {  5,   1,  1, 1, 1,  1,   1,   5},
        { 10,   1,  1, 1, 1,  1,   1,  10},
        {-20, -30,  1, 1, 1,  1, -30, -20},
        { 50, -20, 10, 5, 5, 10, -20,  50}
      };
    }

    // Die gewichteten Steine
    public int CountStonesWeighted(int playerId)
    {
      int value = 0;
      int stoneCount = 0;
      for(int row = 0; row < 8; row++) {
        for(int column = 0; column < 8; column++) {
          if(IsStone(playerId, row, column)) {
            ++stoneCount;
            int stoneVal = StoneWeights[row, column];
            if(stoneVal < 0) {
              int cornerRow = row < 4 ? 0 : 7;
              int cornerCol = column < 4 ? 0 : 7;
              if(IsStone(playerId, cornerRow, cornerCol)) {
                if(row == 0 || column == 0)
                  stoneVal = 10;
                else
                  stoneVal = 1;
              }
            }
            value += stoneVal;
          }
        }
      }
      return stoneCount == 0 ? -1000 : value;
    }

    public override Func<TGame, int, int> GetEvaluator<TGame>(int evalId)
    {
      switch(evalId) {
        case 1:
          // Nur die gewichteten Steine zählen
          return (g, p) =>
          {
            CthelloGame game = g as CthelloGame;
            return game.CountStonesWeighted(p) - game.CountStonesWeighted(1 - p);
          };
        case 2:
          // Gewichtete Steine und Anzahl der möglichen Züge werten.
          return (g, p) =>
          {
            CthelloGame game = g as CthelloGame;
            var myMoves = game.MovesFor(p);
            bool iPass = myMoves.First().Pass;
            var yourMoves = game.MovesFor(1 - p);
            bool youPass = yourMoves.First().Pass;
            if(iPass && youPass) {
              // Wenn jeder nur noch einen Zug (nämlich "Passe") hat,
              // ist das Spiel zu Ende und es zählen nur die Steine.
              return game.CountStonesFor(p) - game.CountStonesFor(1 - p);
            }
            else {
              return (game.CountStonesWeighted(p) + MoveWeight * myMoves.Count()) - (game.CountStonesWeighted(1 - p) + MoveWeight * yourMoves.Count());
            }
          };
        default:
          // Fallback: Steine zählen
          return (g, p) =>
          {
            CthelloGame game = g as CthelloGame;
            return game.CountStonesFor(p) - game.CountStonesFor(1 - p);
          };
      }
    }

  }
}
