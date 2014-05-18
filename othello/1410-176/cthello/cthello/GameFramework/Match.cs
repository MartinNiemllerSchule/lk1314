using System;
using System.Collections.Generic;

namespace GameFramework
{
  // Abstrakte Klasse, die ein Game und dessen zwei Spieler (Player) verwaltet.
  public abstract class Match<TMove>
  {
    protected Brain<TMove>[] _players;
    protected Game<TMove> _game;

    public Match(Brain<TMove> player1, Brain<TMove> player2)
    {
      _players = new Brain<TMove>[2];
      _players[0] = player1;
      _players[1] = player2;
    }

    public Brain<TMove> Player1
    { get { return _players[0]; } }

    public Brain<TMove> Player2
    { get { return _players[1]; } }

    public Brain<TMove> Opponent(Brain<TMove> player)
    {
      return _players[1 - GetPlayerId(player)];
    }

    public int GetPlayerId(Brain<TMove> player)
    {
      if(player == _players[0])
        return 0;
      else if(player == _players[1])
        return 1;
      else
        throw new ArgumentException("Can't get Id of unknown player.");
    }
  }
}
