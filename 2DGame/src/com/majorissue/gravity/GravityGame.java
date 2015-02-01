package com.majorissue.gravity;

import android.content.Context;

import com.majorissue.framework.Screen;
import com.majorissue.framework.impl.AndroidGame;
import com.majorissue.gravity.screens.LoadingScreen;
import com.majorissue.gravity.screens.MainMenuScreen;
import com.majorissue.gravity.util.Assets;

public class GravityGame extends AndroidGame {

	public static final boolean DEBUG = true;
	
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
		if(getCurrentScreen() instanceof MainMenuScreen) {	// exit game
			super.onBackPressed();
		} else {											// return to main screen
			setScreen(new MainMenuScreen(this));
		}
	}

	@Override
	public void onPause() {
		Assets.music_bonobo.pause();
		if(isFinishing()) {
			Assets.music_bonobo.dispose();
		}
		super.onPause();
	}
}