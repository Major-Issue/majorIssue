package com.majorissue.gravity.screens;

import java.util.List;

import com.majorissue.framework.Game;
import com.majorissue.framework.Graphics;
import com.majorissue.framework.Input.TouchEvent;
import com.majorissue.framework.impl.AndroidGraphics;
import com.majorissue.gravity.util.Assets;

public class ExtraScreen extends MenuScreen {

	public ExtraScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				game.setScreen(new MainMenuScreen(game));
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.background_menu_01, 0, 0);
		g.drawText(AndroidGraphics.CENTER, 20, "coming soon ...", null);
	}
}