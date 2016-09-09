package com.convension.connectfour.models;

/**
 * Created by umerfarooq on 22/08/2016.
 */

public class PlayerData {

    private boolean mIsFirstMove;
    private boolean mIsWon;
    private String mId;
    private String mNAme;
    private int mColNum;

    public boolean ismIsFirstMove () {
        return mIsFirstMove;
    }

    public void setmIsFirstMove (boolean mIsFirstMove) {
        this.mIsFirstMove = mIsFirstMove;
    }
    public boolean ismIsWon () {
        return mIsWon;
    }

    public void setmIsWon (boolean mIsWon) {
        this.mIsWon = mIsWon;
    }
    public String getmId () {
        return mId;
    }

    public void setmId (String mId) {
        this.mId = mId;
    }
    public String getmNAme () {
        return mNAme;
    }

    public void setmNAme (String mNAme) {
        this.mNAme = mNAme;
    }

    public int getmColNum () {
        return mColNum;
    }

    public void setmColNum (int mColNum) {
        this.mColNum = mColNum;
    }


}
