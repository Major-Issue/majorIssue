package com.majorissue.gravity.screens;

import java.util.List;

import com.majorissue.framework.Game;
import com.majorissue.framework.Graphics;
import com.majorissue.framework.Input.TouchEvent;
import com.majorissue.framework.Screen;
import com.majorissue.framework.impl.AndroidGraphics;
import com.majorissue.gravity.World;
import com.majorissue.gravity.util.Level;
import com.majorissue.gravity.util.Settings;

public class GameScreen extends Screen {

	public static final int RESOLUTION_NATIVE = 0;
	public static final int RESOLUTION_HALF = 1;

	private final float LOADING_TIME_MIN = 2; // sec

	enum GameState {
		Loading, Ready, Running, Paused, GameOver
	}

	GameState state = GameState.Loading;
	World world;

	private float loadingTime = 0;
	private boolean loadingComplete = false;

	public GameScreen(Game game, int extraLvl) {
		super(game);
		world = new World();
		loadLevel(extraLvl);
	}

	private void loadLevel(int extraLvl) {
		if (extraLvl == 0)
			Level.loadLevel(Settings.currentLevel, Level.LEVEL_STORY, assets);
		else
			Level.loadLevel(extraLvl, Level.LEVEL_EXTRA, assets);
		loadingComplete = true;
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		switch (state) {
		case Loading:
			updateLoading(touchEvents, deltaTime);
			break;
		case Ready:
			updateReady(touchEvents);
			break;
		case Running:
			updateRunning(touchEvents, deltaTime);
			break;
		case Paused:
			updatePaused(touchEvents);
			break;
		case GameOver:
			updateGameOver(touchEvents);
			break;
		default:
			break;
		}	
	}

	private void updateLoading(List<TouchEvent> touchEvents, float deltaTime) {
		loadingTime += deltaTime;
		if (loadingComplete && loadingTime > LOADING_TIME_MIN)
			state = GameState.Ready;
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
		// TODO
	}

	private void updatePaused(List<TouchEvent> touchEvents) {
		// TODO
	}

	private void updateReady(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				// TODO:
				game.setScreen(new MainMenuScreen(game));
			}
		}
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		// TODO
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.clear(android.R.color.black);
		switch (state) {
		case Loading:
			drawLoadingUI(g);
			break;
		case Ready:
			drawReadyUI(g);
		case Running:
			drawRunningUI(g);
			break;
		case Paused:
			drawPausedUI(g);
			break;
		case GameOver:
			drawGameOverUI(g);
			break;
		default:
			break;
		}
	}

	private void drawLoadingUI(Graphics g) {
		g.drawText(AndroidGraphics.CENTER, 20, "loading da game", null);
		// TODO:
	}

	private void drawReadyUI(Graphics g) {
		g.drawText(AndroidGraphics.CENTER, 20, "da game is rdy", null);
		// TODO:
	}

	private void drawRunningUI(Graphics g) {
		// TODO:
	}

	private void drawPausedUI(Graphics g) {
		// TODO:
	}

	private void drawGameOverUI(Graphics g) {
		// TODO:
	}

	@Override
	public void pause() {
		if (state == GameState.Running)
			state = GameState.Paused;
		
		Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
