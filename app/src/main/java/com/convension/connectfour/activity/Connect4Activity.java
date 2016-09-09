package com.convension.connectfour.activity;

import  com.convension.connectfour.Connect4App;
import  com.convension.connectfour.R;
import com.jgrindall.android.connect4.lib.algorithm.*;
import com.jgrindall.android.connect4.lib.board.*;
import  com.convension.connectfour.views.*;
import  com.convension.connectfour.inter.*;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class Connect4Activity extends ABaseActivity implements IOnDebugListener,IOptionsListener,IOnExitListener, OnClickListener {
	
	private TopView mtopView;
	private GameView mGameView;
	private BottomView mBottomView;
	private TextView mDecorView;
	private Button mPowerButton;
	private boolean mCompInterrupted = false;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.debug("CREATE");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        try {
			Class.forName("android.os.AsyncTask");
		}
        catch (ClassNotFoundException e) {
			
		}
        init();
    }
    public void debug(String s){
    	if(!Connect4App.DEBUG){
    		return;
    	}
    	if( mDecorView !=null){
    		if(s==""){
    			mDecorView.setText("");
    		}
    		else{
    			mDecorView.setText(mDecorView.getText()+"\n"+s);
    		}
    	}
    }
    private void init(){
    	mtopView = (TopView)this.findViewById(R.id.topview);
    	mGameView = (GameView)this.findViewById(R.id.gameview);
    	mBottomView = (BottomView)this.findViewById(R.id.bottomview);
    	mDecorView = (TextView)this.findViewById(R.id.debugtext);
    	if(!Connect4App.DEBUG){
    		mDecorView.setVisibility(View.GONE);
    	}
    	mPowerButton = (Button)(this.findViewById(R.id.powerbutton));
    	addListeners();
    	startGame();
    	
    }
    private void startGame(){
    	SharedPreferences settings = getSharedPreferences(Connect4App.PREFS_NAME, 0);
        int d = settings.getInt(Connect4App.PREFS_DIFF, Players.DIFF_EASY);
        int p = settings.getInt(Connect4App.PREFS_PLAY, Players.ONE_PLAYER);
    	mGameView.setDepth(AlgorithmConsts.getDefaultDepth());
    	mGameView.setDifficulty(d);
    	mGameView.setOnExitListener(this);
    	mtopView.setNumPlayers(p);
    	
    }
    private void reload(){
    	debug("mCompInterrupted "+ mCompInterrupted);
    	if( mCompInterrupted ){
    		mGameView.restart();
    	}
    }
    private void gameInflated(){
    	this.debug("inflated");
    	mGameView.inflated();
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
    	super.onRestoreInstanceState(savedInstanceState);
    }
    public void onRestart(){
    	super.onRestart();
    	this.debug("RESTART");
    }
    public void onStart(){
    	super.onStart();
    	this.debug("STARTING");
    }
    public void onResume(){
    	super.onResume();
    	this.debug("RESUMING");
    	// reload the UI and play the move.
    	reload();
    }
    public void onSaveInstanceState(Bundle outState){
    	super.onSaveInstanceState(outState);
    	this.debug("onSaveInstanceState");
    }
    public void onPause(){
    	super.onPause();
    	this.debug("PAUSE");
    }
    private void cleanUp(){
    	this.debug("cleanUp");
    	if( mGameView !=null){
    		mCompInterrupted = mGameView.cleanUp();
    	}
    }
    public void onStop(){
    	super.onStop();
    	this.debug("STOP");
    	cleanUp();
    }
    public void onDestroy(){
    	super.onDestroy();
    	this.debug("DESTROY");
    	cleanUp();
    }
    private void addListeners(){
    	mBottomView.setOnNewGameListener(mGameView);
    	mBottomView.setOnUndoListener(mGameView);
    	mBottomView.setOnOptionsListener(this);
    	mGameView.setOnTurnChangeListener(mtopView);
    	mGameView.setOnBottomListener(mBottomView);
    	mGameView.setOnDebugListener(this);
    	ViewTreeObserver vto = mGameView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                gameInflated();
                ViewTreeObserver obs = mGameView.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
            }
        });
        if(Connect4App.DEBUG){
    		mPowerButton.setOnClickListener(this);
    	}
    	else{
    		mPowerButton.setVisibility(View.INVISIBLE);
    	}
    }
    
   	
    private void back(){
    	finish();
    }
    private void openLeaveDialog(){
    	final boolean wasEnabled = mGameView.getBoardEnabled();
    	mGameView.enableBoard(false);
    	final Dialog dialog = new Dialog(this,R.style.MyDialog);
        dialog.setContentView(R.layout.leave);
        TextView tV = (TextView)dialog.findViewById(R.id.leave_msg);
        tV.setText(R.string.sureleave);
        dialog.setCancelable(true);
      
        
        
        dialog.show();
        ((Button) dialog.findViewById(R.id.leave_cancel)).setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		dialog.dismiss();
        		mGameView.enableBoard(wasEnabled);
            }
        });
        ((Button) dialog.findViewById(R.id.leave_quit)).setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		mGameView.enableBoard(wasEnabled);
        		back();
            }
        });
    }
  
    @Override
   
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        	openLeaveDialog();
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

   
    
	@Override
	public boolean onOptions(View v) {
		Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        return true;
	}
	@Override
	public void exit() {
		finish();
	}
	@Override
	public void onClick(View v) {
		mGameView.powerPressed();
	}
	
    
    
}