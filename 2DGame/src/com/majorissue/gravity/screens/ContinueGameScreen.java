package com.majorissue.gravity.screens;

import java.util.List;

import com.majorissue.framework.Game;
import com.majorissue.framework.Graphics;
import com.majorissue.framework.Input.TouchEvent;
import com.majorissue.game.R;
import com.majorissue.gravity.GravityGame;
import com.majorissue.gravity.util.Assets;
import com.majorissue.gravity.util.Settings;

public class ContinueGameScreen extends MenuScreen {

	// reset game progress (continue game) and start new game
	
	private String[][] touchAreas = null;
	private String touchedEntry = null;
	
	public ContinueGameScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		if(touchAreas == null) return;
		
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) {
				checkTouchDown(event);
			}
			if (event.type == TouchEvent.TOUCH_UP) {
				touchedEntry = null;
				checkTouchUp(event);
			}
		}
	}
	
	private void checkTouchDown(TouchEvent event) {
		for(int i = 0; i < touchAreas.length; i++){
			try {
				if(inBounds(event, touchAreas, i)) {
					touchedEntry = touchAreas[i][0];
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void checkTouchUp(TouchEvent event) {
		for(int i = 0; i < touchAreas.length; i++){
			try {
				if(inBounds(event, touchAreas, i)) {
					handleInput(touchAreas[i][0]);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void handleInput(String entry) {
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.reset))) {
			Settings.continueGame = false;
			Settings.currentLevel = Settings.DEFAULT_LVL;
			Settings.save(game.getFileIO());
			game.setScreen(new GameScreen(game, 0));
		}
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.cancel))) {
			game.setScreen(new MainMenuScreen(game));
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.background_menu_01, 0, 0);
		touchAreas = drawMenu(new String[]{	((GravityGame)game).getResources().getString(R.string.reset),
											((GravityGame)game).getResources().getString(R.string.cancel)
											}, touchedEntry);
		
		
		drawInfo(	((GravityGame)game).getResources().getString(R.string.continue_info),
					Integer.parseInt(touchAreas[0][1]), 
					Integer.parseInt(touchAreas[0][3])
				);
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}

}
