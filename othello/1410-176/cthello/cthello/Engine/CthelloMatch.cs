using GameFramework;
using System;

namespace Cthello.Engine
{
  // Match-Ableitung für ein Othell-Spiel
  public class CthelloMatch : Match<CthelloMove>
  {
    public CthelloMatch(Brain<CthelloMove> player1, Brain<CthelloMove> player2)
      : base(player1, player2)
    {
      _game = new CthelloGame().Init();
      _nextPlayer = player1;
    }

    // Wer ist am Zug?
    Brain<CthelloMove> _nextPlayer;
    public Brain<CthelloMove> NextPlayer
    {
      get { return _nextPlayer; }
    }

    public CthelloGame Game
    {
      get { return _game as CthelloGame; }
    }

    // Der Spieler, der dran ist, führt seinen nächsten Zug aus.
    public async void NextMove()
    {
      CthelloMove move = await _nextPlayer.SelectMoveAsync(_game, GetPlayerId(_nextPlayer));
      MakeMove(move);
      _nextPlayer = Opponent(_nextPlayer);
    }

    // Einen Zug ausführen
    public void MakeMove(CthelloMove move)
    {
      CthelloGame newGame = (CthelloGame)_game.MakeMove(move);
      _game = newGame;
      OnMoved(newGame);
    }

    // Ein Ereignis, das feuert, wenn ein Zug ausgeführt wurde.
    public event GameEventHandler Moved;

    public delegate void GameEventHandler(object sender, GameEventArgs e);

    protected void OnMoved(CthelloGame game)
    {
      if(Moved != null) {
        Moved(this, new GameEventArgs(game));
      }
    }

    public string GetLog()
    {
      return _game.GetLog();
    }
  }

  public class GameEventArgs : EventArgs
  {
    public CthelloGame Game { get; private set; }
    public GameEventArgs(CthelloGame game)
    {
      Game = game;
    }
  }


}
