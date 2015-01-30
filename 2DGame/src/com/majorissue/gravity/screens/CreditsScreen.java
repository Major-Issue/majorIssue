package com.majorissue.gravity.screens;

import java.util.List;

import com.majorissue.framework.Game;
import com.majorissue.framework.Graphics;
import com.majorissue.framework.Screen;
import com.majorissue.framework.Input.TouchEvent;
import com.majorissue.framework.impl.AndroidGraphics;
import com.majorissue.gravity.util.Assets;

public class CreditsScreen extends Screen {

	public CreditsScreen(Game game) {
		super(game);
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
		g.drawText(AndroidGraphics.CENTER, 20, "ALL the credits belong to me!", null);
		g.drawText(AndroidGraphics.BOTTOM_RIGHT, 15, "... sweet, sweet credits", null);
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}

}