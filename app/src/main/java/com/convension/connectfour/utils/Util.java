package com.convension.connectfour.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;

import com.convension.connectfour.Connect4App;
import com.convension.connectfour.R;
import com.jgrindall.android.connect4.lib.board.Players;

/**
 * Created by umerfarooq on 11/08/2016.
 */

public class Util {
    private static Util  sInstance;
    private static ImageView mFirstPiece;
    private Util(){};
    public static Util getInstance() {
        if(sInstance==null) {
           return sInstance=new Util();
        }else
            return sInstance;
    }
    public static void setIcons(ImageView first, ImageView second, Context mContext) {

        SharedPreferences settings = mContext.getSharedPreferences(Connect4App.PREFS_NAME, 0);
        int theme = settings.getInt(Connect4App.PREFS_THEME, Players.THEME_DEFAULT);
        if(theme==Players.THEME_DEFAULT) {
            first.setImageDrawable (mContext.getResources ().getDrawable (R.drawable.red));
            second.setImageDrawable (mContext.getResources ().getDrawable (R.drawable.green));
        }
    }
    public static void setImageViewDrawable(ImageView imageView,int id,Context mContext) {
        SharedPreferences settings = mContext.getSharedPreferences(Connect4App.PREFS_NAME, 0);
        int theme = settings.getInt(Connect4App.PREFS_THEME, Players.THEME_DEFAULT);
        if(theme==Players.THEME_DEFAULT) {
            if(id==R.layout.firstpiece)
                imageView.setImageDrawable (mContext.getResources ().getDrawable (R.drawable.red));
            else if(id==R.layout.secondpiece)
                imageView.setImageDrawable (mContext.getResources ().getDrawable (R.drawable.green));
            else if(id==R.layout.powerpiece) {
                imageView.setImageDrawable (mContext.getResources ().getDrawable (R.drawable.power));
            }
        }
    }

}
