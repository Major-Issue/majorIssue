package com.majorissue.gravity.screens;

import android.graphics.Typeface;

import com.majorissue.framework.Game;
import com.majorissue.framework.Graphics;
import com.majorissue.framework.Screen;
import com.majorissue.framework.impl.AndroidGraphics;
import com.majorissue.gravity.util.Assets;
import com.majorissue.gravity.util.Level;
import com.majorissue.gravity.util.Settings;

public class LoadingScreen extends Screen {

	private final float LOADING_TIME_MIN = 3; // sec
	
	private float loadingTime = 0;
	private boolean loadingComplete = false;

	public LoadingScreen(Game game) {
		super(game);
		loadAssets();
		Settings.load(game.getFileIO());
		Level.loadMetaData(assets);
		loadingComplete = true;
	}

	private void loadAssets(){
		// TODO: load assets
		
		// graphics
		
		// sounds
		Assets.menu_select = game.getAudio().newSound("gravity/sound/select_01.wav");
		Assets.menu_back = game.getAudio().newSound("gravity/sound/back_01.wav");
		
		// music
	}
	
	@Override
	public void update(float deltaTime) {
		loadingTime += deltaTime;
		if(loadingComplete && loadingTime > LOADING_TIME_MIN) {
			if(Settings.introState == IntroScreen.INTRO_FIRST || Settings.introState == IntroScreen.INTRO_SHOW)
				game.setScreen(new IntroScreen(game));
			else
				game.setScreen(new MainMenuScreen(game));
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		Typeface tf = Typeface.create("Roboto",Typeface.BOLD_ITALIC);
		g.drawText(AndroidGraphics.CENTER, 25, "LOADING ...", tf);
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}

}
