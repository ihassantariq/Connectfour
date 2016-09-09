package com.jgrindall.android.connect4.lib.algorithm;

import com.jgrindall.android.connect4.lib.board.APoint;
import com.jgrindall.android.connect4.lib.board.IBoard;

public class Minimax
  extends AAlgorithm
{
  public Minimax(IBoard paramIBoard)
  {
    super(paramIBoard);
  }
  
  public APoint minimax(int paramInt)
  {
    int i = this.b.getNumSpaces();
    int k = -1;
    int m = Integer.MIN_VALUE;
    APoint[] arrayOfAPoint1 = this.b.checkWin();
    this.b.alternateTurn();
    APoint[] arrayOfAPoint2 = this.b.checkWin();
    this.b.alternateTurn();
    if (arrayOfAPoint1 != null) {
      return new APoint(Integer.MAX_VALUE, k);
    }
    if (arrayOfAPoint2 != null) {
      return new APoint(Integer.MIN_VALUE, k);
    }
    int n;
    if (paramInt == 0)
    {
      n = this.b.evaluateBoard();
      return new APoint(n, this.b.getHighestStrengthCol());
    }
    int j = this.b.getCanWinNow();
    if (j >= 0) {
      return new APoint(Integer.MAX_VALUE, j);
    }
    j = this.b.getCanStopWin();
    if (j >= 0) {
      return new APoint(Integer.MAX_VALUE, j);
    }
    if (i != this.b.getNumSpaces()) {
      return new APoint(Integer.MAX_VALUE, 0);
    }
    for (j = 0; j <= 6; j++) {
      if (!this.b.colFull(j))
      {
        this.b.pushCol(j, false);
        APoint localAPoint = minimax(paramInt - 1);
        n = -localAPoint.x;
        this.b.popCol(j);
        if (n > m)
        {
          m = n;
          k = j;
        }
      }
    }
    return new APoint(m, k);
  }
  
  public APoint getBestPlay(int paramInt)
  {
    return minimax(paramInt);
  }
}

