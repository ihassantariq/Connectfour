package com.jgrindall.android.connect4.lib.board;

public abstract interface IBoard
{
  public abstract int getPlayersGo();
  
  public abstract void setPlayersGo(int paramInt);
  
  public abstract APoint getBestPlay();
  
  public abstract void pushCol(int paramInt, boolean paramBoolean);
  
  public abstract void popCol(int paramInt);
  
  public abstract int getCanWinNow();
  
  public abstract void alternateTurn();
  
  public abstract APoint[] checkWin();
  
  public abstract int getStepsDown(int paramInt);
  
  public abstract boolean getOwns(APoint[] paramArrayOfAPoint);
  
  public abstract boolean colFull(int paramInt);
  
  public abstract int getNumSpaces();
  
  public abstract int get(APoint paramAPoint);
  
  public abstract int get(int paramInt1, int paramInt2);
  
  public abstract void fill(APoint paramAPoint, int paramInt);
  
  public abstract void reset();
  
  public abstract String encode();
  
  public abstract void output(String paramString);
  
  public abstract int evaluateBoard();
  
  public abstract void setDifficulty(int paramInt);
  
  public abstract void setDepth(int paramInt);
  
  public abstract int getDepth();
  
  public abstract int getHighestStrengthCol();
  
  public abstract int getCanStopWin();
}
