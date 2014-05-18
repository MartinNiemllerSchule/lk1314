using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Threading;
using System.Threading.Tasks;

namespace GameFramework
{
  // Die eigentliche "Intelligenz" des Spiels.
  // Ausführliche Erklärung in c't 10/14, S. 176ff
  public class MinimaxBrain<TMove> : Brain<TMove>
  {
    protected Random _rnd;

    // So viele Halbzüge in die Zukunft wird der Spielbaum analysiert.
    public int Depth
    { get; set; }

    public MinimaxBrain()
    {
      _rnd = new Random();
    }

    int _valueCount = 0;
    TimeSpan _valueTime;
    Func<Game<TMove>, int, int> _evaluator;

    public async override Task<TMove> SelectMoveAsync(Game<TMove> game, int player)
    {
      // Die Stellungsbewertungsfunktion
      _evaluator = game.GetEvaluator<Game<TMove>>(2);
      return await Task<TMove>.Run(() =>
      {
        Stopwatch timer = Stopwatch.StartNew();
        _valueCount = 0;
        int bestValue = -int.MaxValue;
        var bestMoves = new List<TMove>();
        // Von den folgenden 2 Zeilen immer nur eine entkommentieren,
        // um zwischen paralleler oder serieller Analyse umzuschalten.
        // foreach(var move in game.MovesFor(player))
        Parallel.ForEach(game.MovesFor(player), move =>
        {
          // Von den folgenden 4 Zeilen ist immer nur eine zu entkommentieren!
          // int value = -MiniMax(game.MakeMove(move), 1 - player, Depth);
          // int value = -MiniMaxParallel(game.MakeMove(move), 1 - player, Depth);
          int value = -AlphaBeta(game.MakeMove(move), 1 - player, Depth, -int.MaxValue, -bestValue);
          // int value = -AlphaBetaParallel(game.MakeMove(move), 1 - player, Depth, -int.MaxValue, -bestValue, null);
          lock(bestMoves) { // Ist eigentlich nur bei paralleler Analyse nötig
            if(value > bestValue) {
              // Neuer bester Zug
              bestValue = value;
              bestMoves.Clear();
              bestMoves.Add(move);
            }
            else if(value == bestValue) {
              // Ein Zug mit derselben Bewertung wie der bisher beste
              bestMoves.Add(move);
            }
          }
        }); // Parallel.ForEach()
        // } // foreach()
        timer.Stop();
        _valueTime = timer.Elapsed;
        // Aus den gesammelten Zügen mit der besten Bewertung einen zufällig auswählen.
        return bestMoves[_rnd.Next(bestMoves.Count)];
      });
    }

    protected virtual int MiniMax(Game<TMove> game, int player, int depth)
    {
      if(depth == 0)
        return GameValue(game, player);
      int bestValue = -int.MaxValue;
      foreach(var move in game.MovesFor(player)) {
        int value = -MiniMax(game.MakeMove(move), 1 - player, depth - 1);
        if(value > bestValue) {
          bestValue = value;
        }
      }
      return bestValue;
    }

    protected virtual int MiniMaxParallel(Game<TMove> game, int player, int depth)
    {
      if(depth == 0)
        return GameValue(game, player);
      int bestValue = -int.MaxValue;
      object lockObject = new object();
      Parallel.ForEach(game.MovesFor(player), move =>
      {
        int value;
        // Auf den letzten Rekursionsstufen lohnt sich die Parallelisierung nicht mehr.
        if(depth > 2)
          value = -MiniMaxParallel(game.MakeMove(move), 1 - player, depth - 1);
        else
          value = -MiniMax(game.MakeMove(move), 1 - player, depth - 1);
        lock(lockObject) {
          if(value > bestValue) {
            bestValue = value;
          }
        }
      });
      return bestValue;
    }

    protected virtual int AlphaBeta(Game<TMove> game, int player, int depth, int alpha, int beta)
    {
      if(depth == 0)
        return GameValue(game, player);
      int bestValue = -int.MaxValue;
      foreach(var move in game.MovesFor(player)) {
        int value = -AlphaBeta(game.MakeMove(move), 1 - player, depth - 1, -beta, -alpha);
        if(value > bestValue) {
          bestValue = value;
        }
        if(value > alpha) {
          alpha = value;
          if(alpha >= beta) {
            break;
          }
        }
      }
      return bestValue;
    }

    protected virtual int AlphaBetaParallel(Game<TMove> game, int player, int depth, int alpha, int beta, ParallelLoopState outerState)
    {
      if(depth == 0)
        return GameValue(game, player);
      int bestValue = -int.MaxValue;
      object lockObject = new object();
      Parallel.ForEach(game.MovesFor(player), (move, loopState) =>
      {
        // Abbrechen, wenn in einer höheren Etage geschnitten wurde.
        if(outerState != null && outerState.IsStopped) {
          loopState.Stop();
        }
        else {
          int value;
          if(depth > 2)
            value = -AlphaBetaParallel(game.MakeMove(move), 1 - player, depth - 1, -beta, -alpha, loopState);
          else
            value = -AlphaBeta(game.MakeMove(move), 1 - player, depth - 1, -beta, -alpha);
          lock(lockObject) {
            if(value > bestValue) {
              bestValue = value;
            }
            if(value > alpha) {
              alpha = value;
              if(alpha >= beta) {
                loopState.Stop();
              }
            }
          }
        }
      });
      return bestValue;
    }

    protected virtual int GameValue(Game<TMove> game, int player)
    {
      Interlocked.Increment(ref _valueCount);
      return _evaluator(game, player);
    }

    public override string GetStatistics()
    {
      return string.Format("{0:n0} Stellungen in {1:%h}:{1:mm}:{1:ss},{1:%f} analysiert.", _valueCount, _valueTime);
    }

  }
}
