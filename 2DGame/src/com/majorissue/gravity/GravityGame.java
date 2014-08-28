package com.majorissue.gravity;

import android.content.Context;

import com.majorissue.framework.Screen;
import com.majorissue.framework.impl.AndroidGame;
import com.majorissue.gravity.screens.LoadingScreen;
import com.majorissue.gravity.screens.MainMenuScreen;

public class GravityGame extends AndroidGame {

	@Override
	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public void onBackPressed() {
		if(getCurrentScreen() instanceof MainMenuScreen)
			super.onBackPressed();//TODO: advertising
		else
			setScreen(new MainMenuScreen(this));
	}

}
