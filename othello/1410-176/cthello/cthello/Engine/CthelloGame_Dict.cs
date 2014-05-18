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
    // Since there are no multi-dimensional immutable collections, we
    // use an ImmutableDictionary whose keys are Tuples of (Row, Column)
    // and whose values are Ints where -1 means empty, 0 means black and 
    // 1 means white.
    private ImmutableDictionary<Tuple<int, int>, int> _board;

    public CthelloGame()
      : base()
    {
    }

    static CthelloGame()
    {
      BuildStoneWeights();
    }

    public CthelloGame(CthelloGame other)
      : base(other)
    {
      _board = other._board;
    }

    public override Game<CthelloMove> Init()
    {
      base.Init();
      BuildBoard();
      return this;
    }

    private void BuildBoard()
    {
      Dictionary<Tuple<int, int>, int> boardBuilder = new Dictionary<Tuple<int, int>, int>(64);
      for(int row = 0; row < 8; row++) {
        for(int column = 0; column < 8; column++) {
          var rc = Tuple.Create(row, column);
          boardBuilder[rc] = -1;
        }
      }
      boardBuilder[Tuple.Create(3, 3)] = 1;
      boardBuilder[Tuple.Create(3, 4)] = 0;
      boardBuilder[Tuple.Create(4, 3)] = 0;
      boardBuilder[Tuple.Create(4, 4)] = 1;
      _board = boardBuilder.ToImmutableDictionary();
    }

    public int StoneAt(int row, int column)
    {
      return _board[Tuple.Create(row, column)];
    }

    public override Game<CthelloMove> MakeMove(CthelloMove move)
    {
      CthelloGame copy = new CthelloGame(this);
      copy.AddMove(move);
      if(!move.Pass) {
        copy.MoveAndFlip(move);
      }
      return copy;
    }

    protected void MoveAndFlip(CthelloMove move)
    {
      var builder = _board.ToBuilder();
      builder[Tuple.Create(move.Row, move.Column)] = move.Player;
      for(int dir = 0; dir < 8; ++dir) {
        var flip = FlippedStones(move.Player, move.Row, move.Column, dir);
        foreach(var f in flip) {
          builder[f] = move.Player;
        }
      }
      _board = builder.ToImmutable();
    }

    protected List<Tuple<int, int>> FlippedStones(int playerId, int row, int column, int direction)
    // direction: 0 = N, 1 = NW, 2 = W, 3 = SW, 4 = S, 5 = SE, 6 = E, 7 = NE
    {
      List<Tuple<int, int>> result = new List<Tuple<int, int>>();

      int dx = (direction % 4 == 0 ? 0 : 1) * (direction < 4 ? -1 : 1);
      int dy = ((direction + 2) % 4 == 0 ? 0 : 1) * ((direction + 2) % 8 < 4 ? -1 : 1);
      int opponentId = 1 - playerId;
      for(int d = 1; ; ++d) {
        int r = row + d * dy;
        int c = column + d * dx;
        if(r < 0 || r > 7 || c < 0 || c > 7) // Hitting the border means no row
                {
          result.Clear();
          break;
        }
        int stone = StoneAt(r, c);
        if(stone == playerId) // My stone ends row
          break;
        else if(stone == opponentId) // Opponent's stone might be turned
          result.Add(Tuple.Create(r, c));
        else // Empty field means no row
                {
          result.Clear();
          break;
        }
      }
      return result;
    }

    public override bool IsMoveLegal(CthelloMove move)
    {
      return IsMoveLegal(move.Player, move.Row, move.Column, move.Pass);
    }

    protected bool IsMoveLegal(int playerId, int row, int column, bool pass = false)
    {
      if(pass) {
        return MovesFor(playerId).ElementAt(0).Pass;
      }
      else {
        if(StoneAt(row, column) != -1)
          return false;
        for(int dir = 0; dir < 8; ++dir) {
          var flipped = FlippedStones(playerId, row, column, dir);
          if(flipped.Count != 0)
            return true;
        }
        return false;
      }
    }

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

    public string GetLogTop(int numEntries)
    {
      StringBuilder sb = new StringBuilder();
      for(int idx = _history.Count - numEntries; idx < _history.Count; ++idx)
        sb.Append(_history[idx].Coordinates + " - ");
      return sb.ToString();
    }

    public IEnumerable<CthelloMove> OldMovesFor(int playerId)
    {
      int moveCount = 0;
      for(int row = 0; row < 8; ++row) {
        for(int column = 0; column < 8; ++column) {
          if(IsMoveLegal(playerId, row, column)) {
            ++moveCount;
            yield return new CthelloMove(playerId, row, column);
          }
        }
      }
      if(moveCount == 0) {
        yield return new CthelloMove(playerId, 0, 0, true);
      }
    }

    public override IEnumerable<CthelloMove> MovesFor(int playerId)
    {
      int moveCount = 0;
      for(int shell = 0; shell < 4; ++shell) {
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

    public int CountStonesFor(int playerId)
    {
      int stoneCount = 0;
      for(int row = 0; row < 8; row++) {
        for(int column = 0; column < 8; column++) {
          if(StoneAt(row, column) == playerId) {
            ++stoneCount;
          }
        }
      }
      return stoneCount;
    }

    static int[,] _stoneWeights = null;

    static void BuildStoneWeights()
    {
      _stoneWeights = new int[8, 8] {
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

    public int CountStonesWeighted(int playerId)
    {
      int value = 0;
      for(int row = 0; row < 8; row++) {
        for(int column = 0; column < 8; column++) {
          if(StoneAt(row, column) == playerId) {
            int stoneVal = _stoneWeights[row, column];
            if(stoneVal < 0) {
              int cornerRow = row < 4 ? 0 : 7;
              int cornerCol = column < 4 ? 0 : 7;
              if(StoneAt(cornerRow, cornerCol) == playerId) {
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
      return (int)value;
    }

    public override Func<TGame, int, int> GetEvaluator<TGame>(int evalId)
    {
      switch(evalId) {
        case 1:
          return (g, p) =>
          {
            CthelloGame game = g as CthelloGame;
            return game.CountStonesWeighted(p) - game.CountStonesWeighted(1 - p);
          };
        case 2:
          return (g, p) =>
          {
            CthelloGame game = g as CthelloGame;
            int myMoves = game.MovesFor(p).Count();
            int yourMoves = game.MovesFor(1 - p).Count();
            if(myMoves < 2 && yourMoves < 2) {
              return game.CountStonesFor(p) - game.CountStonesFor(1 - p);
            }
            else {
              return (game.CountStonesWeighted(p) + 10 * myMoves) - (game.CountStonesWeighted(1 - p) + 10 * yourMoves);
            }
          };
        default:
          return (g, p) =>
          {
            CthelloGame game = g as CthelloGame;
            return game.CountStonesFor(p) - game.CountStonesFor(1 - p);
          };
      }
    }


  }
}
