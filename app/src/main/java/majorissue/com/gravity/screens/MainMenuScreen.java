package majorissue.com.gravity.screens;

import android.app.Activity;

import com.majorissue.game.R;

import java.util.List;

import majorissue.com.framework.Game;
import majorissue.com.framework.Graphics;
import majorissue.com.framework.Input.TouchEvent;
import majorissue.com.gravity.GravityGame;
import majorissue.com.gravity.util.Assets;
import majorissue.com.gravity.util.Settings;

public class MainMenuScreen extends MenuScreen {
	
	private String[][] menuTouchAreas = null;
	private String touchedMenuEntry = null;
	
	public MainMenuScreen(Game game) {
		super(game);
		playBackgroundMusic();
	}

	@Override
	public void update(float deltaTime) {
		if(menuTouchAreas == null) {
			return;
		}
		
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
		for(int i = 0; i < menuTouchAreas.length; i++) {
			try {
				if(inBounds(event, menuTouchAreas, i)) {
					touchedMenuEntry = menuTouchAreas[i][0];
					playSound(touchedMenuEntry);
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
					handleInput(menuTouchAreas[i][0]);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void handleInput(String entry) {
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.new_game))) {
			if(checkContinueGame())
				game.setScreen(new GameScreen(game, 0));
		}
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.continue_game))) {
			game.setScreen(new GameScreen(game, 0));
		}
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.extra))) {
			game.setScreen(new ExtraScreen(game));
		}
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.settings))) {
			game.setScreen(new SettingsScreen(game));
		}
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.help))) {
			game.setScreen(new HelpScreen(game));
		}
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.credits))) {
			game.setScreen(new CreditsScreen(game));
		}
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.quit))) {
			((Activity)game).finish();
		}
	}
	
	private boolean checkContinueGame() {
		if(Settings.continueGame) {
			game.setScreen(new ContinueGameScreen(game));
		} else {
			return true;
		}
		return false;
	}
	
	private void playSound(String entry) {
		if(entry == null || !Settings.soundEnabled) {
			return;
		}
		
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.new_game))) {
			Assets.menu_click.play(1);
		}
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.continue_game))) {
			Assets.menu_click.play(1);
		}
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.extra))) {
			Assets.menu_click.play(1);
		}
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.settings))) {
			Assets.menu_click.play(1);
		}
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.help))) {
			Assets.menu_click.play(1);
		}
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.credits))) {
			Assets.menu_click.play(1);
		}
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.quit))) {
			Assets.menu_click.play(1);
		}
	}
	
	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.main_menu, 0, 0);
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
		super.pause();
	}
}