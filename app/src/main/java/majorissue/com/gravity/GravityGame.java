package majorissue.com.gravity;

import android.content.Context;

import majorissue.com.framework.Screen;
import majorissue.com.framework.impl.AndroidGame;
import majorissue.com.gravity.screens.LoadingScreen;
import majorissue.com.gravity.screens.MainMenuScreen;
import majorissue.com.gravity.util.Assets;

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
		Assets.music_bkg_01.pause();
		if(isFinishing()) {
			Assets.music_bkg_01.dispose();
		}
		super.onPause();
	}
}