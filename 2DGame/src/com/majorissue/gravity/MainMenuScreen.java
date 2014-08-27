package com.majorissue.gravity;

import java.util.List;

import android.app.Activity;

import com.majorissue.framework.Game;
import com.majorissue.framework.Input.TouchEvent;
import com.majorissue.game.R;
import com.majorissue.gravity.util.Settings;

public class MainMenuScreen extends MenuScreen {
	
	private String[][] menuTouchAreas = null;
	private String touchedMenuEntry = null;
	
	public MainMenuScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		if(menuTouchAreas == null) return;
		
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) {
				checkTouchDown(event);
			}
			if (event.type == TouchEvent.TOUCH_UP) {
				touchedMenuEntry = null;
				checkTouchUp(event);
			}
		}
	}

	private void checkTouchDown(TouchEvent event) {
		for(int i = 0; i < menuTouchAreas.length; i++){
			try {
				if(inBounds(event, menuTouchAreas, i)) {
					touchedMenuEntry = menuTouchAreas[i][0];
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private void checkTouchUp(TouchEvent event) {
		for(int i = 0; i < menuTouchAreas.length; i++){
			try {
				if(inBounds(event, menuTouchAreas, i)) {
					loadScreen(menuTouchAreas[i][0]);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void loadScreen(String screen) {
		if(screen.equals(((GravityGame)game).getResources().getString(R.string.new_game))) {
			if(checkContinueGame())
				game.setScreen(new GameScreen(game, 0));
		}
		if(screen.equals(((GravityGame)game).getResources().getString(R.string.continue_game)))
			game.setScreen(new GameScreen(game, 0));
		if(screen.equals(((GravityGame)game).getResources().getString(R.string.extra)))
			game.setScreen(new ExtraScreen(game));
		if(screen.equals(((GravityGame)game).getResources().getString(R.string.settings)))
			game.setScreen(new SettingsScreen(game));
		if(screen.equals(((GravityGame)game).getResources().getString(R.string.help)))
			game.setScreen(new HelpScreen(game));
		if(screen.equals(((GravityGame)game).getResources().getString(R.string.credits)))
			game.setScreen(new CreditsScreen(game));
		if(screen.equals(((GravityGame)game).getResources().getString(R.string.quit)))
			((Activity)game).finish();
	}
	
	private boolean checkContinueGame() {
		if(Settings.continueGame) {
			return true;
		} else {
			// TODO:
		}
		return true;
	}
	
	@Override
	public void present(float deltaTime) {
		menuTouchAreas = drawMenu(new String[]{	((GravityGame)game).getResources().getString(R.string.new_game),
												((GravityGame)game).getResources().getString(R.string.continue_game),
												((GravityGame)game).getResources().getString(R.string.extra),
												((GravityGame)game).getResources().getString(R.string.settings),
												((GravityGame)game).getResources().getString(R.string.help),
												((GravityGame)game).getResources().getString(R.string.credits),
												((GravityGame)game).getResources().getString(R.string.quit)
												}, touchedMenuEntry);
	}
	
	@Override
	public void pause() {
		Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}

}
