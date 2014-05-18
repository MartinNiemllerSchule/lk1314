using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace GameFramework
{
  // Minimal-Implementierung von Brain für Testzwecke.
  // Die Züge werden zufällig ausgewählt.
  public class RandomBrain<TMove> : Brain<TMove>
  {
    protected Random _rnd;

    public RandomBrain()
    {
      _rnd = new Random();
    }

    int _moveCount = 0;

    public async override Task<TMove> SelectMoveAsync(Game<TMove> game, int player)
    {
      return await Task.Run(() =>
      {
        var moves = (
          from m in game.MovesFor(player)
          select m
          ).ToList();
        _moveCount = moves.Count;
        int idx = _rnd.Next(_moveCount);
        return moves[idx];
      });
    }

    public override string GetStatistics()
    {
      return string.Format("Aus {0} möglichen Zügen ausgewählt.", _moveCount);
    }
  }
}
