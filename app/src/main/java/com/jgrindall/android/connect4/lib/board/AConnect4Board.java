package com.jgrindall.android.connect4.lib.board;

import com.jgrindall.android.connect4.lib.algorithm.AAlgorithm;
import com.jgrindall.android.connect4.lib.algorithm.AlgorithmConsts;
import com.jgrindall.android.connect4.lib.algorithm.LookUp;
import java.util.Arrays;

public abstract class AConnect4Board
  implements IBoard
{
  public static final int NUMX = 7;
  public static final int NUMY = 6;
  protected int[][] full = new int[7][6];
  protected int numSpaces;
  protected int playersGo = 1;
  protected int[] heights = new int[7];
  protected int depth = 1;
  protected APoint[][] winLines;
  protected APoint[][] threeLines;
  protected APoint[][] twoLines;
  protected double rand;
  private int difficulty;
  protected AAlgorithm alg;
  
  public AConnect4Board()
  {
    WinLines.build();
    this.winLines = WinLines.winLines;
    this.threeLines = WinLines.threeLines;
    this.twoLines = WinLines.twoLines;
    reset();
  }
  
  public int getPlayersGo()
  {
    return this.playersGo;
  }
  
  public void setPlayersGo(int paramInt)
  {
    this.playersGo = paramInt;
  }
  
  protected APoint playRandom()
  {
    return new APoint(0, 0);
  }
  
  public void setDifficulty(int paramInt)
  {
    this.difficulty = paramInt;
  }
  
  public double getDifficulty()
  {
    return this.difficulty;
  }
  
  public int getDepth()
  {
    return this.depth;
  }
  
  public void setDepth(int paramInt)
  {
    this.depth = paramInt;
  }
  
  protected int countControlledPower()
  {
    int i = 0;
    for (int j = 0; j <= 6; j++) {
      for (int k = 0; k <= 5; k++) {
        if ((get(j, k) == 0) && (controls(j, k)))
        {
          int m = WinLines.getStrengthAt(j, k);
          i += m;
        }
      }
    }
    return i;
  }
  
  private boolean controls(int paramInt1, int paramInt2)
  {
    int i = 0;
    int j = 0;
    int k = Math.max(paramInt1 - 1, 0);
    int m = Math.min(paramInt1 + 1, 6);
    int n = Math.max(paramInt2 - 1, 0);
    int i1 = Math.min(paramInt2 + 1, 5);
    for (int i2 = k; i2 <= m; i2++) {
      for (int i3 = n; i3 <= i1; i3++)
      {
        int i4 = get(i2, i3);
        if (i4 == 2)
        {
          i++;
          j++;
        }
        else if (i4 == this.playersGo)
        {
          i++;
        }
        else if (i4 != 0)
        {
          j++;
        }
      }
    }
    Boolean vari2 = (i >= 2) && (i > j) && (i + j >= 4);
    return vari2;
  }
  
  private APoint makeFirstPlay()
  {
    double d1 = 0.25D;
    double d2 = Math.random();
    if (d2 < d1) {
      return new APoint(2, 11);
    }
    if (d2 > 1.0D - d1) {
      return new APoint(3, 11);
    }
    return new APoint(4, 11);
  }
  
  public APoint getBestPlay()
  {
    if (this.numSpaces == 42) {
      return makeFirstPlay();
    }
    int i = lookUp();
    if (i >= 0) {
      return new APoint(i, 1);
    }
    int j = this.depth;
    int k = Math.random() < AlgorithmConsts.getRandCol(this.difficulty) ? 1 : 0;
    if (k != 0) {
      return new APoint(getHighestStrengthCol(), 3);
    }
    int m = 0;
    if (Math.random() <= AlgorithmConsts.getRandDepth(this.difficulty))
    {
      this.depth = AlgorithmConsts.getDepth(this.difficulty);
      m = 1;
    }
    APoint localAPoint = this.alg.getBestPlay(this.depth);
    if (m != 0)
    {
      this.depth = j;
      return new APoint(localAPoint.y, 0);
    }
    return new APoint(localAPoint.y, 2);
  }
  
  protected APoint[] orderColsByStrength()
  {
    APoint[] arrayOfAPoint = new APoint[7];
    for (int i = 0; i <= 6; i++) {
      arrayOfAPoint[i] = new APoint(i, getStrengthOnCol(i));
    }
    Arrays.sort(arrayOfAPoint, APoint.YComparator);
    return arrayOfAPoint;
  }
  
  private int lookUp()
  {
    String str = encode();
    return LookUp.lookUp(str);
  }
  
  protected int countThreePower()
  {
    int i = 0;
    int j = 98;
    for (int k = 0; k <= j - 1; k++)
    {
      APoint[] arrayOfAPoint = this.threeLines[k];
      if (getOwns(arrayOfAPoint)) {
        i += getPower(arrayOfAPoint);
      }
    }
    return i;
  }
  
  public int evaluateBoard()
  {
    return 0;
  }
  
  private int getPower(APoint[] paramArrayOfAPoint)
  {
    int i = 0;
    for (int j = 0; j <= paramArrayOfAPoint.length - 1; j++) {
      i += WinLines.getStrengthAt(paramArrayOfAPoint[j]);
    }
    return i;
  }
  
  protected int countTwoPower()
  {
    int i = 0;
    int j = 131;
    for (int k = 0; k <= j - 1; k++)
    {
      APoint[] arrayOfAPoint = this.twoLines[k];
      if (getOwns(arrayOfAPoint)) {
        i += getPower(arrayOfAPoint);
      }
    }
    return i;
  }
  
  public int getHighestStrengthCol()
  {
    APoint[] arrayOfAPoint = orderColsByStrength();
    return arrayOfAPoint[(arrayOfAPoint.length - 1)].x;
  }
  
  public void outputE(int paramInt1, int paramInt2, int paramInt3) {}
  
  public void pushCol(int paramInt, boolean paramBoolean)
  {
    int i = getStepsDown(paramInt);
    if (!paramBoolean) {
      fill(paramInt, i, this.playersGo);
    } else {
      fill(paramInt, i, 2);
    }
    alternateTurn();
    this.heights[paramInt] += 1;
  }
  
  public void popCol(int paramInt)
  {
    int i = getStepsDown(paramInt);
    fill(paramInt, i + 1, 0);
    alternateTurn();
    this.heights[paramInt] -= 1;
  }
  
  public int getCanWinNow()
  {
    for (int i = 0; i <= 6; i++)
    {
      boolean bool = playWinsNow(i);
      if (bool) {
        return i;
      }
    }
    return -1;
  }
  
  public boolean getOwns(APoint[] paramArrayOfAPoint)
  {
    int i = paramArrayOfAPoint.length;
    for (int j = 0; j <= i - 1; j++)
    {
      int k = get(paramArrayOfAPoint[j]);
      if ((k != 2) && (k != this.playersGo)) {
        return false;
      }
    }
    return true;
  }
  
  protected boolean playWinsNow(int paramInt)
  {
    if (!colFull(paramInt))
    {
      pushCol(paramInt, false);
      alternateTurn();
      APoint[] arrayOfAPoint = checkWin();
      popCol(paramInt);
      alternateTurn();
      if (arrayOfAPoint != null) {
        return true;
      }
    }
    return false;
  }
  
  public int getCanStopWin()
  {
    alternateTurn();
    for (int i = 0; i <= 6; i++) {
      if (playWinsNow(i))
      {
        alternateTurn();
        return i;
      }
    }
    alternateTurn();
    return -1;
  }
  
  public static int getAlternateTurn(int paramInt)
  {
    if (paramInt == 1) {
      return -1;
    }
    if (paramInt == -1) {
      return 1;
    }
    return 0;
  }
  
  public void alternateTurn()
  {
    this.playersGo = getAlternateTurn(this.playersGo);
  }
  
  public APoint[] checkWin()
  {
    int i = 69;
    for (int j = 0; j <= i - 1; j++)
    {
      APoint[] arrayOfAPoint = this.winLines[j];
      boolean bool = getOwns(arrayOfAPoint);
      if (bool) {
        return arrayOfAPoint;
      }
    }
    return null;
  }
  
  public int getStepsDown(int paramInt)
  {
    return 5 - this.heights[paramInt];
  }
  
  public boolean colFull(int paramInt)
  {
    return this.heights[paramInt] == 6;
  }
  
  public int getNumSpaces()
  {
    return this.numSpaces;
  }
  
  public int get(APoint paramAPoint)
  {
    return get(paramAPoint.x, paramAPoint.y);
  }
  
  private int getStrengthOnCol(int paramInt)
  {
    int i = getStepsDown(paramInt);
    int j;
    if (i == -1) {
      j = -1;
    } else {
      j = WinLines.getStrengthAt(paramInt, i);
    }
    return j;
  }
  
  public int get(int paramInt1, int paramInt2)
  {
    return this.full[paramInt1][paramInt2];
  }
  
  public void fill(int paramInt1, int paramInt2, int paramInt3)
  {
    this.full[paramInt1][paramInt2] = paramInt3;
    if (paramInt3 == 0) {
      this.numSpaces += 1;
    } else {
      this.numSpaces -= 1;
    }
  }
  
  public void fill(APoint paramAPoint, int paramInt)
  {
    fill(paramAPoint.x, paramAPoint.y, paramInt);
  }
  
  public void reset()
  {
    this.heights = new int[7];
    for (int i = 0; i <= 6; i++)
    {
      for (int j = 0; j <= 5; j++) {
        this.full[i][j] = 0;
      }
      this.heights[i] = 0;
    }
    setPlayersGo(1);
    this.numSpaces = 42;
  }
  
  public String encode()
  {
    String str = "";
    int i = 0;
    for (int k = 0; k <= 5; k++) {
      for (int m = 0; m <= 6; m++)
      {
        int j = get(m, k);
        if (j == 0)
        {
          i++;
        }
        else
        {
          if (i >= 1)
          {
            str = str + "" + i;
            i = 0;
          }
          if (j == 1) {
            str = str + "R";
          } else if (j == -1) {
            str = str + "Y";
          }
        }
      }
    }
    return str;
  }
  
  public void output(String paramString)
  {
    for (int i = 0; i <= 5; i++)
    {
      String str = paramString;
      for (int j = 0; j <= 6; j++)
      {
        int k = get(j, i);
        if (k == 0) {
          str = str + "O ";
        } else if (k == -1) {
          str = str + "Y ";
        } else if (k == 1) {
          str = str + "R ";
        }
      }
      System.out.println(str);
    }
  }
}
