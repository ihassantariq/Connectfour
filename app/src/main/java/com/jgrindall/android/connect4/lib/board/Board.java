package com.jgrindall.android.connect4.lib.board;

import com.jgrindall.android.connect4.lib.algorithm.Minimax;

public class Board
  extends AConnect4Board
{
  public Board()
  {
    this.alg = new Minimax(this);
  }
  
  public int evaluateBoard()
  {
    int i = 1;
    int j = 4;
    int k = 3;
    int m = countTwoPower();
    int n = countThreePower();
    int i1 = countControlledPower();
    alternateTurn();
    int i2 = countTwoPower();
    int i3 = countThreePower();
    int i4 = countControlledPower();
    alternateTurn();
    int i5 = j * (n - i3) + k * (i1 - i4) + i * (m - i2);
    return i5;
  }
}