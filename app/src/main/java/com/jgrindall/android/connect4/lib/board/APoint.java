package com.jgrindall.android.connect4.lib.board;

import java.util.Comparator;

public class APoint implements Comparable<APoint>
{
  public int x;
  public int y;
  public static Comparator<APoint> XComparator = new Comparator()
  {
    @Override
    public int compare(Object o, Object t1) {
      APoint paramAnonymousAPoint1=(APoint)o;
      APoint paramAnonymousAPoint2=(APoint)t1;
      APoint localAPoint1 = paramAnonymousAPoint1;
      APoint localAPoint2 = paramAnonymousAPoint2;
      return localAPoint1.x - localAPoint2.x;
    }
  };
  public static Comparator<APoint> YComparator = new Comparator()
  {
    @Override
    public int compare(Object o, Object t1) {
      APoint paramAnonymousAPoint1=(APoint)o;
      APoint paramAnonymousAPoint2=(APoint)t1;
      APoint localAPoint1 = paramAnonymousAPoint1;
      APoint localAPoint2 = paramAnonymousAPoint2;
      return localAPoint1.y - localAPoint2.y;
    }
  };
  
  public APoint(int paramInt1, int paramInt2)
  {
    this.x = paramInt1;
    this.y = paramInt2;
  }
  
  public String toString()
  {
    return "" + this.x + "," + this.y;
  }
  
  public int compareTo(APoint paramAPoint)
  {
    APoint localAPoint = paramAPoint;
    return this.x - localAPoint.x;
  }
  
  public static APoint[] concat(APoint[] paramArrayOfAPoint1, APoint[] paramArrayOfAPoint2)
  {
    int i = paramArrayOfAPoint1.length;
    int j = paramArrayOfAPoint2.length;
    APoint[] arrayOfAPoint = new APoint[i + j];
    for (int k = 0; k <= i - 1; k++) {
      arrayOfAPoint[k] = paramArrayOfAPoint1[k];
    }
    for ( int k = 0; k <= j - 1; k++) {
      arrayOfAPoint[(k + i)] = paramArrayOfAPoint2[k];
    }
    return arrayOfAPoint;
  }
}