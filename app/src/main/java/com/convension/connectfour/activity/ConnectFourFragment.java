package com.convension.connectfour.activity;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import com.convension.connectfour.Connect4App;
import com.convension.connectfour.R;
import com.convension.connectfour.inter.IGameViewListener;
import com.convension.connectfour.inter.IOnDebugListener;
import com.convension.connectfour.inter.IOnExitListener;
import com.convension.connectfour.inter.IOptionsListener;
import com.convension.connectfour.views.BottomView;
import com.convension.connectfour.views.GameViewMultiplayer;
import com.convension.connectfour.views.TopView;
import com.jgrindall.android.connect4.lib.algorithm.AlgorithmConsts;
import com.jgrindall.android.connect4.lib.board.Players;

/**
 * Created by umerfarooq on 21/08/2016.
 */

public class ConnectFourFragment extends Fragment implements IOnDebugListener,IOptionsListener,IOnExitListener, View.OnClickListener,IGameViewListener {

    private IGameViewListener mMessageSendListener;
    private TopView mtopView;
    private GameViewMultiplayer mGameView;
  //  private BottomView mBottomView;
    private TextView mDecorView;
    private Button mPowerButton;
    private boolean mCompInterrupted = false;
    private View mRootView;


    /** Called when the activity is first created. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState ) {
        this.debug("CREATE");
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.main);
        mRootView= inflater.inflate(R.layout.multiplayer_game_main, container, false);
        mRootView.setOnKeyListener (new View.OnKeyListener ()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    openLeaveDialog();
                    return true;
                }
                return false;
            }
        });
        try {
            Class.forName("android.os.AsyncTask");
        }
        catch (ClassNotFoundException e) {

        }
        init();
        return mRootView;
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
        mtopView = (TopView)mRootView.findViewById(R.id.topview);
        mGameView = (GameViewMultiplayer ) mRootView.findViewById(R.id.gameview);
      //  mBottomView = (BottomView)mRootView.findViewById(R.id.bottomview);
        mDecorView = (TextView)mRootView.findViewById(R.id.debugtext);
        if(!Connect4App.DEBUG){
            mDecorView.setVisibility(View.GONE);
        }
        mPowerButton = (Button)(mRootView.findViewById(R.id.powerbutton));
        addListeners();
        startGame();

    }
    private void startGame(){
        SharedPreferences settings = getActivity ().getSharedPreferences(Connect4App.PREFS_NAME, 0);
        int d = settings.getInt(Connect4App.PREFS_DIFF, Players.DIFF_EASY);
       // int p = settings.getInt(Connect4App.PREFS_PLAY, Players.ONE_PLAYER);
        mGameView.setDepth(AlgorithmConsts.getDefaultDepth());
        mGameView.setDifficulty(d);
        mGameView.setOnExitListener(this);
        mGameView.setmMessageSendListener(this);
        mtopView.setNumPlayers(1);
        mGameView.setNumPlayers (1);

    }
    private void reload(){
        debug("mCompInterrupted "+ mCompInterrupted);
        if( mCompInterrupted ){
           // mGameView.restart();
        }
    }
    private void gameInflated(){
        this.debug("inflated");
        mGameView.inflated();
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
        //mBottomView.setOnNewGameListener(mGameView);
     //   mBottomView.setOnUndoListener(mGameView);
     //   mBottomView.setOnOptionsListener(this);
        mGameView.setOnTurnChangeListener(mtopView);
      //  mGameView.setOnBottomListener(mBottomView);
        mGameView.setOnDebugListener(this);
        ViewTreeObserver vto = mGameView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener () {
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
        getActivity ().finish ();
    }
    private void openLeaveDialog(){
        final boolean wasEnabled = mGameView.getBoardEnabled();
        mGameView.enableBoard(false);
        final Dialog dialog = new Dialog(getActivity () ,R.style.MyDialog);
        dialog.setContentView(R.layout.leave);
        TextView tV = (TextView)dialog.findViewById(R.id.leave_msg);
        tV.setText(R.string.sureleave);
        dialog.setCancelable(true);



        dialog.show();
        ((Button) dialog.findViewById(R.id.leave_cancel)).setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mGameView.enableBoard(wasEnabled);
            }
        });
        ((Button) dialog.findViewById(R.id.leave_quit)).setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                mGameView.enableBoard(wasEnabled);
                back();
            }
        });
    }

    @Override
    public boolean onOptions(View v) {
        Intent intent = new Intent(getActivity (), SettingsActivity.class);
        startActivity(intent);
        return true;
    }
    @Override
    public void exit() {
         getActivity ().finish ();
    }
    @Override
    public void onClick(View v) {
        mGameView.powerPressed();
    }
    public void onMessageReceived(int colNum,Boolean isFinal) {
        mGameView.onMessageReceived(colNum,isFinal);
    }

    @Override
    public void onRealTimeMessageSend (int colNum, Boolean isFinal) {

        mMessageSendListener.onRealTimeMessageSend (colNum,isFinal);
    }
    public void setmMessageSendListener (IGameViewListener mMessageSendListener) {
        this.mMessageSendListener = mMessageSendListener;
    }
}
