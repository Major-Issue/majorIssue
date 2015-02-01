package com.majorissue.gravity.screens;

import java.util.List;

import com.majorissue.framework.Game;
import com.majorissue.framework.Graphics;
import com.majorissue.framework.Input.TouchEvent;
import com.majorissue.framework.Pixmap;
import com.majorissue.gravity.util.Assets;
import com.majorissue.gravity.util.Settings;

public class IntroScreen extends MenuScreen {

	public static final int INTRO_FIRST = -1;
	public static final int INTRO_SHOW = 0;
	public static final int INTRO_DONT_SHOW = 1;
	
	private int currentScreen = 1; 
	
	public IntroScreen(Game game) {
		super(game);
		if(Settings.introState != INTRO_SHOW) {
			Settings.introState = INTRO_DONT_SHOW;
		}
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (event.x > game.getGraphics().getWidth() * 0.8f && 
					event.y > game.getGraphics().getHeight() * 0.9f) {
					next();
				}
				if (event.x < game.getGraphics().getWidth() * 0.2f && 
					event.y < game.getGraphics().getHeight() * 0.1f) {
					back();
				}
				if (event.x > game.getGraphics().getWidth() * 0.8f && 
					event.y < game.getGraphics().getHeight() * 0.1f) {
					skip();
				}
			}
		}
	}
	
	private void next() {
		currentScreen += 1;
		if(currentScreen > 3) {
			game.setScreen(new MainMenuScreen(game));
		}
	}
	
	private void back() {
		currentScreen -= 1;
		if(currentScreen < 1) {
			currentScreen = 1;
		}
	}
	
	private void skip() {
		game.setScreen(new MainMenuScreen(game));
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawPixmap(getCurrentIntroScreen(currentScreen), 0, 0);
	}
	
	
	private Pixmap getCurrentIntroScreen(int screen) {
		switch (screen) {
		case 1:
			return Assets.intro_page_01;
		case 2:
			return Assets.intro_page_02;
		case 3:
			return Assets.intro_page_03;
		default:
			return null;
		}
	}
}