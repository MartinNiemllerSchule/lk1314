using Cthello.Engine;
using GameFramework;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;

namespace Cthello
{
  /// <summary>
  /// Interaktionslogik für MainWindow.xaml
  /// </summary>
  public partial class MainWindow : Window, IMoveSelector<CthelloMove>
  {
    public MainWindow()
    {
      InitializeComponent();
    }

    private void Window_Loaded(object sender, RoutedEventArgs e)
    {
      CollectStones();
    }

    FlipControl[,] _stones;
    CthelloMatch _match = null;

    // Wir sammeln die Stein-Controls des Spielbretts ein, 
    // um einfacher per Zeile/Spalte auf sie zugreifen zu können.
    void CollectStones()
    {
      _stones = new FlipControl[8, 8];
      foreach(var ch in GameGrid.Children) {
        Border field = ch as Border;
        if(field != null) {
          int r = Grid.GetRow(field) - 1;
          int c = Grid.GetColumn(field) - 1;
          FlipControl stone = field.Child as FlipControl;
          _stones[r, c] = stone;
          if((r == 3 && c == 4) || (r == 4 && c == 3))
            stone.Flip(false);
        }
      }
    }

    // Ein Zug wurde gemacht - Spielfeld aktualisieren und nächsten Zug machen.
    async void match_Moved(object sender, GameEventArgs e)
    {
      DisplayMatch();
      if(!_match.Game.IsGameOver) {
        await Task.Delay(500);
        UpdateStatus();
        _match.NextMove();
      }
    }

    void DisplayMatch(bool animate = true)
    {
      for(int row = 0; row < 8; ++row)
        for(int column = 0; column < 8; ++column) {
          int stone = _match.Game.StoneAt(row, column);
          FlipControl control = _stones[row, column];
          switch(stone) {
            case -1:
              control.Visibility = Visibility.Hidden;
              break;
            case 0:
              if(control.Visibility == Visibility.Visible) {
                if(!control.IsFlipped) {
                  control.Flip(animate);
                }
              }
              else {
                if(!control.IsFlipped) {
                  control.Flip(false);
                }
                control.Visibility = Visibility.Visible;
              }
              break;
            case 1:
              if(control.Visibility == Visibility.Visible) {
                if(control.IsFlipped) {
                  control.Flip(animate);
                }
              }
              else {
                if(control.IsFlipped) {
                  control.Flip(false);
                }
                control.Visibility = Visibility.Visible;
              }
              break;
          }
        }
      tbLog.Text = _match.GetLog();
      tbLog.ScrollToEnd();
      tbScore.Text = string.Format("Schwarz: {0}\r\nWeiß: {1}", _match.Game.CountStonesFor(0), _match.Game.CountStonesFor(1));
      var player = _match.NextPlayer;
      int playerId = _match.GetPlayerId(player);
      var panel = playerId == 0 ? statusPlayer1 : statusPlayer2;
      if(player.IsInteractive) {
        panel.Text = "";
      }
      else {
        string stat = player.GetStatistics();
        if(!string.IsNullOrEmpty(stat)) {
          panel.Text = string.Format("{0}: {1}", playerId == 0 ? "Schwarz" : "Weiß", stat);
        }
        else {
          panel.Text = "";
        }
      }
    }

    void UpdateStatus()
    {
      int playerId = _match.GetPlayerId(_match.NextPlayer);
      string player = playerId == 0 ? "Schwarz" : "Weiß";
      string moving = _match.NextPlayer.IsInteractive ? " ist am Zug." : " denkt nach ...";
      var panel = playerId == 0 ? statusPlayer1 : statusPlayer2;
      panel.Text = player + moving;
    }

    // Das IMoveSelector-Interface
    public event MoveSelectEventHandler<CthelloMove> MoveSelected;
    protected void OnMoveSelected(CthelloMove move)
    {
      if(MoveSelected != null)
        MoveSelected(this, new MoveSelectedEventArgs<CthelloMove>(move));
    }

    private void field_MouseEnter(object sender, MouseEventArgs e)
    {
      if(_match == null || _match.NextPlayer == null || !_match.NextPlayer.IsInteractive)
        return;
      Border field = sender as Border;
      int row = Grid.GetRow(field) - 1;
      int column = Grid.GetColumn(field) - 1;
      if(_match.Game.StoneAt(row, column) == -1) {
        int player = _match.GetPlayerId(_match.NextPlayer);
        CthelloMove move = new CthelloMove(player, row, column);
        UIElement toShow;
        if(_match.Game.IsMoveLegal(move)) {
          if(player == 0)
            toShow = imgYesBlack;
          else
            toShow = imgYesWhite;
        }
        else {
          if(player == 0)
            toShow = imgNoBlack;
          else
            toShow = imgNoWhite;
        }
        Grid.SetRow(toShow, row + 1);
        Grid.SetColumn(toShow, column + 1);
        toShow.Visibility = Visibility.Visible;
      }
    }

    private void field_MouseLeave(object sender, MouseEventArgs e)
    {
      if(_match == null || _match.NextPlayer == null || !_match.NextPlayer.IsInteractive)
        return;
      var destination = Mouse.DirectlyOver as UIElement;
      if(destination != imgYesBlack)
        imgYesBlack.Visibility = Visibility.Hidden;
      if(destination != imgYesWhite)
        imgYesWhite.Visibility = Visibility.Hidden;
      if(destination != imgNoBlack && !imgNoBlack.Children.Contains(destination))
        imgNoBlack.Visibility = Visibility.Hidden;
      if(destination != imgNoWhite && !imgNoWhite.Children.Contains(destination))
        imgNoWhite.Visibility = Visibility.Hidden;
    }

    int _clickRow, _clickColumn;

    private void field_MouseDown(object sender, MouseButtonEventArgs e)
    {
      if(_match == null || _match.NextPlayer == null || !_match.NextPlayer.IsInteractive)
        return;
      var marker = sender as UIElement;
      _clickRow = Grid.GetRow(marker) - 1;
      _clickColumn = Grid.GetColumn(marker) - 1;
    }

    private void field_MouseUp(object sender, MouseButtonEventArgs e)
    {
      if(_match == null || _match.NextPlayer == null || !_match.NextPlayer.IsInteractive)
        return;
      var marker = sender as UIElement;
      marker.Visibility = Visibility.Hidden;
      if(_clickRow == Grid.GetRow(marker) - 1 && _clickColumn == Grid.GetColumn(marker) - 1) {
        CthelloMove move = new CthelloMove(_match.GetPlayerId(_match.NextPlayer), _clickRow, _clickColumn);
        OnMoveSelected(move);
      }
    }

    private void btnPass_Click(object sender, RoutedEventArgs e)
    {
      CthelloMove move = new CthelloMove(_match.GetPlayerId(_match.NextPlayer), 0, 0, true);
      if(_match.Game.IsMoveLegal(move)) {
        OnMoveSelected(move);
      }
    }

    private void btnStart_Click(object sender, RoutedEventArgs e)
    {
      if(_match != null) {
        _match.Moved -= match_Moved;
        _match.Player1.Detach(this);
        _match.Player2.Detach(this);
      }
      Brain<CthelloMove> player1, player2;
      switch(cmbPlayer1.SelectedIndex) {
        case 0:
          player1 = new InteractiveBrain<CthelloMove>();
          break;
        case 1:
          player1 = new RandomBrain<CthelloMove>();
          break;
        case 2:
          player1 = new MinimaxBrain<CthelloMove>();
          (player1 as MinimaxBrain<CthelloMove>).Depth = cmbPlayer1Param.SelectedIndex;
          break;
        default:
          player1 = null;
          MessageBox.Show("Bitte wählen Sie einen gültigen Spieler aus.");
          break;
      }
      switch(cmbPlayer2.SelectedIndex) {
        case 0:
          player2 = new InteractiveBrain<CthelloMove>();
          break;
        case 1:
          player2 = new RandomBrain<CthelloMove>();
          break;
        case 2:
          player2 = new MinimaxBrain<CthelloMove>();
          (player2 as MinimaxBrain<CthelloMove>).Depth = cmbPlayer2Param.SelectedIndex;
          break;
        default:
          player2 = null;
          MessageBox.Show("Bitte wählen Sie einen gültigen Spieler aus.");
          break;
      }
      player1.Attach(this);
      player2.Attach(this);
      _match = new CthelloMatch(player1, player2);
      DisplayMatch(false);
      _match.Moved += match_Moved;
      UpdateStatus();
      _match.NextMove();
    }

    private void cmbPlayer1_SelectionChanged(object sender, SelectionChangedEventArgs e)
    {
      if(lblPlayer1Param != null && cmbPlayer1Param != null) {
        if(cmbPlayer1.SelectedIndex != 2) {
          lblPlayer1Param.Visibility = Visibility.Collapsed;
          cmbPlayer1Param.Visibility = Visibility.Collapsed;
        }
        else {
          lblPlayer1Param.Visibility = Visibility.Visible;
          cmbPlayer1Param.Visibility = Visibility.Visible;
        }
      }
    }

    private void cmbPlayer2_SelectionChanged(object sender, SelectionChangedEventArgs e)
    {
      if(lblPlayer2Param != null && cmbPlayer2Param != null) {
        if(cmbPlayer2.SelectedIndex != 2) {
          lblPlayer2Param.Visibility = Visibility.Collapsed;
          cmbPlayer2Param.Visibility = Visibility.Collapsed;
        }
        else {
          lblPlayer2Param.Visibility = Visibility.Visible;
          cmbPlayer2Param.Visibility = Visibility.Visible;
        }
      }
    }

  }
}
