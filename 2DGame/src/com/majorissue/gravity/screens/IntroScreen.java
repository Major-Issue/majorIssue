package com.majorissue.gravity.screens;

import java.util.List;

import com.majorissue.framework.Game;
import com.majorissue.framework.Graphics;
import com.majorissue.framework.Screen;
import com.majorissue.framework.Input.TouchEvent;
import com.majorissue.framework.impl.AndroidGraphics;
import com.majorissue.gravity.util.Assets;
import com.majorissue.gravity.util.Settings;

public class IntroScreen extends Screen {

	public static final int INTRO_FIRST = -1;
	public static final int INTRO_SHOW = 0;
	public static final int INTRO_DONT_SHOW = 1;
	
	public IntroScreen(Game game) {
		super(game);
		if(Settings.introState != INTRO_SHOW)
			Settings.introState = INTRO_DONT_SHOW;
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				// TODO:
				game.setScreen(new MainMenuScreen(game));
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.background_menu_01, 0, 0);
		g.drawText(AndroidGraphics.CENTER, 20, "INTRO", null);
		// TODO:
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}

}
