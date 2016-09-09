package com.jgrindall.android.connect4.lib.board;

public class WinLines
{
  public static final int NUM_WIN = 69;
  public static final int NUM_3 = 98;
  public static final int NUM_2 = 131;
  public static APoint[][] twoLines = new APoint[''][2];
  public static APoint[][] threeLines = new APoint[98][3];
  public static APoint[][] winLines = new APoint[69][4];
  public static int[][] strength = new int[7][6];
  public static boolean built = false;
  
  public static void build()
  {
    if (!built)
    {
      buildLines(4, winLines);
      buildLines(3, threeLines);
      buildLines(2, twoLines);
      buildStrength();
    }
    built = true;
  }
  
  public static int getStrengthAt(APoint paramAPoint)
  {
    return strength[paramAPoint.x][paramAPoint.y];
  }
  
  public static int getStrengthAt(int paramInt1, int paramInt2)
  {
    return strength[paramInt1][paramInt2];
  }
  
  public static void buildStrength()
  {
    int[] arrayOfInt1 = { 3, 4, 5, 5, 4, 3 };
    int[] arrayOfInt2 = { 4, 6, 8, 8, 6, 4 };
    int[] arrayOfInt3 = { 5, 8, 11, 11, 8, 5 };
    int[] arrayOfInt4 = { 7, 10, 13, 13, 10, 7 };
    strength[0] = arrayOfInt1;
    strength[1] = arrayOfInt2;
    strength[2] = arrayOfInt3;
    strength[3] = arrayOfInt4;
    strength[4] = arrayOfInt3;
    strength[5] = arrayOfInt2;
    strength[6] = arrayOfInt1;
  }
  
  private static void buildLines(int paramInt, APoint[][] paramArrayOfAPoint)
  {
    int m = 0;
    int j;
    APoint[] arrayOfAPoint;
    int k;
    for (int i = 0; i <= 7 - paramInt; i++) {
      for (j = 0; j <= 5; j++)
      {
        arrayOfAPoint = new APoint[paramInt];
        for (k = 0; k <= paramInt - 1; k++) {
          arrayOfAPoint[k] = new APoint(i + k, j);
        }
        paramArrayOfAPoint[m] = arrayOfAPoint;
        m++;
      }
    }
    for (int i = 0; i <= 6; i++) {
      for (j = 0; j <= 6 - paramInt; j++)
      {
        arrayOfAPoint = new APoint[paramInt];
        for (k = 0; k <= paramInt - 1; k++) {
          arrayOfAPoint[k] = new APoint(i, j + k);
        }
        paramArrayOfAPoint[m] = arrayOfAPoint;
        m++;
      }
    }
    for (int i = 0; i <= 7 - paramInt; i++) {
      for (j = 0; j <= 6 - paramInt; j++)
      {
        arrayOfAPoint = new APoint[paramInt];
        for (k = 0; k <= paramInt - 1; k++) {
          arrayOfAPoint[k] = new APoint(i + k, j + k);
        }
        paramArrayOfAPoint[m] = arrayOfAPoint;
        m++;
      }
    }
    for ( int i = 0; i <= 7 - paramInt; i++) {
      for (j = paramInt - 1; j <= 5; j++)
      {
        arrayOfAPoint = new APoint[paramInt];
        for (k = 0; k <= paramInt - 1; k++) {
          arrayOfAPoint[k] = new APoint(i + k, j - k);
        }
        paramArrayOfAPoint[m] = arrayOfAPoint;
        m++;
      }
    }
  }
}