package com.convension.connectfour.views;

import  com.convension.connectfour.R;
import com.convension.connectfour.utils.Util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class DecorView extends RelativeLayout {
	private View mRootView;
	public DecorView(Context c, AttributeSet a) {
		super(c,a);
		init();
	}
	public DecorView(Context c){
		super(c);
		init();
	}
	private void init(){
		inflate();
	}
	private boolean getIsLarge(){
		// 640 by 480 is 307200
		// 700 by 900 is 630000
		WindowManager man = (WindowManager)this.getContext().getSystemService(Context.WINDOW_SERVICE);
		Display display = man.getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		return (width*height > 450000);
	}
	private void inflate(){
		LayoutInflater linf = (LayoutInflater)(getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
		boolean isLarge = getIsLarge();
		if(isLarge){
			mRootView=linf.inflate(R.layout.decorlarge, this, true);
		}
		else{
			mRootView=linf.inflate(R.layout.decor, this, true);
		}
		RelativeLayout relativeLayout=(RelativeLayout)mRootView.findViewById (R.id.root_layout);
		int i=0;
		for(i=0;i<9;i++) {
			Util.getInstance ().setImageViewDrawable ((ImageView) relativeLayout.getChildAt (i),R.layout.firstpiece,getContext ());
		}
		for(;i<15;i++) {
			Util.getInstance ().setImageViewDrawable ((ImageView) relativeLayout.getChildAt (i),R.layout.secondpiece,getContext ());
		}

	}
	
}