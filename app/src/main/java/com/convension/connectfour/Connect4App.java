package com.convension.connectfour;

import android.app.Application;
import android.content.SharedPreferences;

import com.jgrindall.android.connect4.lib.board.Players;

public class Connect4App extends Application{
	
	public static final String PREFS_NAME 		= 	"MyPrefsFile";
	public static final String PREFS_SOUND 		= 	"sound";
	public static final String PREFS_PLAY 		= 	"player";
	public static final String PREFS_DIFF 		= 	"difficulty";
	public static final String PREFS_TURN 		= 	"turn";
	public static final String PREFS_POWER 		= 	"power";
	public static final String PREFS_THEME		=	"theme";
	
	public static boolean DEBUG = false;
	
	public Connect4App(){
		super();
	}
	
}