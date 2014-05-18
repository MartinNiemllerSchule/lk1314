using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Collections.Immutable;

namespace Cthello.Engine
{
    class Board
    {
        private ImmutableDictionary<Tuple<int, int>, int> _board;

        public Board()
        {
            Dictionary<Tuple<int, int>, int> boardBuilder = new Dictionary<Tuple<int, int>, int>(64);
            for (int row = 0; row < 8; row++)
            {
                for (int column = 0; column < 8; column++)
                {
                    var rc = Tuple.Create(row, column);
                    boardBuilder[rc] = -1;
                }
            }
            _board = boardBuilder.ToImmutableDictionary();
        }

        public Board(Board other)
        {
            _board = other._board;
        }

        public void SetAt(int row, int column, int newValue)
        {
            _board = _board.SetItem(Tuple.Create(row, column), newValue);
        }

        public int GetAt(int row, int column)
        {
            return _board[Tuple.Create(row, column)];
        }

        public int this[int row, int column]
        {
            get { return GetAt(row, column); }
            set { SetAt(row, column, value); }
        }

        public ImmutableDictionary<Tuple<int, int>, int>.Builder GetBuilder()
        {
            return _board.ToBuilder();
        }

        public void ReleaseBuilder(ImmutableDictionary<Tuple<int, int>, int>.Builder builder)
        {
            _board = builder.ToImmutable();
        }
    }
}
