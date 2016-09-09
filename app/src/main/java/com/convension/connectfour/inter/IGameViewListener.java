package com.convension.connectfour.inter;

import android.view.View;

public interface IGameViewListener {
	public abstract void onRealTimeMessageSend (int colNum ,Boolean isFinal);
}
