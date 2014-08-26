package com.majorissue.gravity;

import java.util.List;

import com.majorissue.framework.Game;
import com.majorissue.framework.Input.TouchEvent;
import com.majorissue.game.R;
import com.majorissue.gravity.util.Settings;

public class SettingsScreen extends MenuScreen {

	private String[][] settingsTouchAreas = null;
	private String touchedSettingsEntry = null;
	
	public SettingsScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		if(settingsTouchAreas == null) return;
		
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) {
				checkTouchDown(event);
			}
			if (event.type == TouchEvent.TOUCH_UP) {
				touchedSettingsEntry = null;
				checkTouchUp(event);
			}
		}
	}
	
	private void checkTouchDown(TouchEvent event) {
		for(int i = 0; i < settingsTouchAreas.length; i++){
			try {
				if(inBounds(event, settingsTouchAreas, i)) {
					touchedSettingsEntry = settingsTouchAreas[i][0];
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private void checkTouchUp(TouchEvent event) {
		for(int i = 0; i < settingsTouchAreas.length; i++){
			try {
				if(inBounds(event, settingsTouchAreas, i)) {
					handleInput(settingsTouchAreas[i][0]);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		settingsTouchAreas = drawMenu(new String[]{	((GravityGame)game).getResources().getString(R.string.music),
													((GravityGame)game).getResources().getString(R.string.sound),
													((GravityGame)game).getResources().getString(R.string.intro),
													((GravityGame)game).getResources().getString(R.string.back)
													}, touchedSettingsEntry);
	}
	
	@Override
	public void pause() {
		Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}
	
	private void handleInput(String entry) {
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.music)))
			Settings.toggleMusic();
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.sound)))
			Settings.toggleSound();
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.intro)))
			Settings.toggleIntro();
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.back)))
			game.setScreen(new MainMenuScreen(game));
	}
}
