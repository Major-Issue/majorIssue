package majorissue.com.gravity.screens;

import java.util.List;

import com.majorissue.game.R;

import majorissue.com.framework.Game;
import majorissue.com.framework.Graphics;
import majorissue.com.framework.Input.TouchEvent;
import majorissue.com.gravity.GravityGame;
import majorissue.com.gravity.util.Assets;
import majorissue.com.gravity.util.Settings;

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
					playSound(touchedSettingsEntry);
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
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.background_menu_01, 0, 0);
		settingsTouchAreas = drawMenu(new String[]{	((GravityGame)game).getResources().getString(R.string.music),
													((GravityGame)game).getResources().getString(R.string.sound),
													((GravityGame)game).getResources().getString(R.string.intro),
													((GravityGame)game).getResources().getString(R.string.autoretry),
													((GravityGame)game).getResources().getString(R.string.aidline),
                                                    ((GravityGame)game).getResources().getString(R.string.back)
													}, touchedSettingsEntry);
	}
	
	@Override
	public void pause() {
		Settings.save(game.getFileIO());
		super.pause();
	}
	
	private void handleInput(String entry) {
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.music))) {
			Settings.toggleMusic();
		}
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.sound))) {
			Settings.toggleSound();
		}
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.intro))) {
			Settings.toggleIntro();
		}
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.autoretry))) {
			Settings.toggleAutoRetry();
		}
        if(entry.equals(((GravityGame)game).getResources().getString(R.string.aidline))) {
            Settings.toggleAidline();
        }
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.back))) {
			Settings.save(game.getFileIO());
			game.setScreen(new MainMenuScreen(game));
		}
	}
	
	private void playSound(String entry) {
		if(entry == null || !Settings.soundEnabled) {
			return;
		}
		
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.music))) {
			Assets.menu_click.play(1);
		}
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.sound))) {
			Assets.menu_click.play(1);
		}
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.intro))) {
			Assets.menu_click.play(1);
		}
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.autoretry))) {
			Assets.menu_click.play(1);
		}
        if(entry.equals(((GravityGame)game).getResources().getString(R.string.aidline))) {
            Assets.menu_click.play(1);
        }
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.back))) {
			Assets.menu_click.play(1);
		}
	}
}