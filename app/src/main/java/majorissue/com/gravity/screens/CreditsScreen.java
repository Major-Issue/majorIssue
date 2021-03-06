package majorissue.com.gravity.screens;

import java.util.List;

import majorissue.com.framework.Game;
import majorissue.com.framework.Graphics;
import majorissue.com.framework.Input.TouchEvent;
import majorissue.com.framework.impl.AndroidGraphics;
import majorissue.com.gravity.util.Assets;

public class CreditsScreen extends MenuScreen {

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
				game.setScreen(new MainMenuScreen(game));
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.main_menu, 0, 0);
		g.drawText(AndroidGraphics.CENTER, 20, "ALL the credits belong to me!", null);
		g.drawText(AndroidGraphics.BOTTOM_RIGHT, 15, "... sweet, sweet credits", null);
	}
}