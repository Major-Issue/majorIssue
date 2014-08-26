package com.majorissue.gravity;

import java.util.List;

import com.majorissue.framework.Game;
import com.majorissue.framework.Graphics;
import com.majorissue.framework.Screen;
import com.majorissue.framework.Input.TouchEvent;
import com.majorissue.framework.impl.AndroidGraphics;

public class ExtraScreen extends Screen {

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
				// TODO:
				game.setScreen(new MainMenuScreen(game));
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.clear(android.R.color.black);
		g.drawText(AndroidGraphics.CENTER, 20, "comming soon ...", null);
		// TODO:
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}

}
