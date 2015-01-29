package com.majorissue.gravity.screens;

import java.util.List;

import android.graphics.Bitmap;

import com.majorissue.framework.Game;
import com.majorissue.framework.Graphics;
import com.majorissue.framework.Input.TouchEvent;
import com.majorissue.framework.Screen;
import com.majorissue.framework.impl.AndroidGraphics;
import com.majorissue.gravity.GravityGame;
import com.majorissue.gravity.World;
import com.majorissue.gravity.objects.Planet;
import com.majorissue.gravity.util.Assets;
import com.majorissue.gravity.util.Level;
import com.majorissue.gravity.util.Settings;
import com.majorissue.gravity.util.Util;

public class GameScreen extends Screen {

	public static final int RESOLUTION_NATIVE = 0;
	public static final int RESOLUTION_HALF = 1;
	public static final float DELTA_TIME_MIN = 0.02f;
	
	private final float LOADING_TIME_MIN = 2f; // sec
	
	public enum GameState {
		Loading, Ready, Running, Paused, GameOver, GameWon
	}

	private GameState oldState = GameState.Loading;
	private GameState state = GameState.Loading;
	private World world;
	private int player_x = -1;
	private int player_y = -1;
	private float loadingTime = 0.0f;
	private boolean loadingComplete = false;
	private int fps = 0;

	public GameScreen(Game game, int extraLvl) {
		super(game);
		loadLevel(extraLvl);
	}

	private void loadLevel(int extraLvl) {
		if (extraLvl == 0) {
			Level.loadLevel(Settings.currentLevel, Level.LEVEL_STORY, assets);
		} else {
			Level.loadLevel(extraLvl, Level.LEVEL_EXTRA, assets);
		}
		loadingComplete = true;
	}

	@Override
	public void update(float deltaTime) {

		fps = (int) (1/deltaTime);
		
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		switch (state) {
		case GameWon:
			loadNextLevel();
			break;
		case Loading:
			updateLoading(touchEvents, deltaTime);
			break;
		case Ready:
			updateReady(touchEvents, deltaTime);
			break;
		case Running:
			updateRunning(touchEvents,deltaTime);
			break;
		case Paused:
			updatePaused(touchEvents, deltaTime);
			break;
		case GameOver:
			updateGameOver(touchEvents, deltaTime);
			break;
		default:
			break;
		}
	}
	
	private void loadNextLevel() {
		player_x = -1;
		player_y = -1;
		state = oldState;
		state = GameState.Loading;
		world.reset();
		String nextLevel = "02.level"; // TODO: next level
		Level.loadLevel(nextLevel, Level.LEVEL_STORY, assets);
		loadingComplete = true;
	}

	private void updateLoading(List<TouchEvent> touchEvents, float deltaTime) {
		loadingTime += deltaTime;
		if (loadingComplete && loadingTime > LOADING_TIME_MIN) {
			oldState = state;
			world = new World(game);
			world.init();
			state = GameState.Ready;
		}
	}

	private void updateGameOver(List<TouchEvent> touchEvents, float deltaTime) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				game.setScreen(new MainMenuScreen(game));
			}
		}
		updateWorld(deltaTime);
	}

	private void updatePaused(List<TouchEvent> touchEvents, float deltaTime) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				state = oldState;
				oldState = GameState.Paused;
			}
		}
	}

	private void updateReady(List<TouchEvent> touchEvents, float deltaTime) {
		// this is where we wait for the user action / input
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) {
				player_x = event.x;
				player_y = event.y;
			}
			if (event.type == TouchEvent.TOUCH_DRAGGED) {
				player_x = event.x;
				player_y = event.y;
			}
			if (event.type == TouchEvent.TOUCH_UP) {
				oldState = state;
				state = GameState.Running;
				player_x = event.x;
				player_y = event.y;
			}
		}
		
		world.inputX = player_x;
		world.inputY = player_y;
		updateWorld(deltaTime);
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				oldState = state;
				state = GameState.Paused;
			}
		}
		updateWorld(deltaTime);
	}
	
	private void updateWorld(float deltaTime) {
		world.update(deltaTime, state);

		if (world.gameOver) {
			oldState = state;
			state = GameState.GameOver;
		}
		else if(world.gameWon) {
			oldState = state;
			state = GameState.GameWon;
		}
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
			break;
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
		
		if(GravityGame.DEBUG) {
			drawDebug(g);
		}
	}
	
	private void drawDebug(Graphics g) {
		g.drawText(AndroidGraphics.TOP_RIGHT, 20, fps + " fps", null);
		if(state == GameState.Ready || state == GameState.Running || state == GameState.Paused) {
			g.drawText(AndroidGraphics.BOTTOM_LEFT, 20, "x=" + player_x + ", y=" + player_y + ", h=" + world.ship.heading + ", p=" + world.ship.pulse + ", s=" + world.ship.percetageSpeed, null);
		}
	}


	private void drawLoadingUI(Graphics g) {
		g.drawText(AndroidGraphics.TOP_LEFT, 20, "loading ...", null);	
	}

	private void drawReadyUI(Graphics g) {
		g.drawText(AndroidGraphics.TOP_LEFT, 20, "ready", null);
		drawWorld(g);
	}

	private void drawRunningUI(Graphics g) {
		g.drawText(AndroidGraphics.TOP_LEFT, 20, "running", null);
		drawWorld(g);
	}

	private void drawPausedUI(Graphics g) {
		g.drawText(AndroidGraphics.TOP_LEFT, 20, "paused", null);
		drawWorld(g);
	}

	private void drawGameOverUI(Graphics g) {
		g.drawText(AndroidGraphics.TOP_LEFT, 20, "game over", null);
		drawWorld(g);
	}
	
	private void drawWorld(Graphics g) {
		int x = 0;
		int y = 0;
		
		// draw Planets
		if(world.planets != null) {
			for(Planet planet : world.planets) {
				Bitmap b = Util.RotateBitmap(Assets.planet.getBitmap(), planet.rotation);
				x = planet.getPosX() - (b.getWidth() / 2);
				y = planet.getPosY() - (b.getHeight() / 2);
				g.drawBitmap(b, x, y);
				if(planet.hasMoon) {
					b = Util.RotateBitmap(Assets.moon.getBitmap(), planet.moon.selfRotation);
					x = planet.moon.getPosX() - (b.getWidth() / 2);
					y = planet.moon.getPosY() - (b.getHeight() / 2);
					g.drawBitmap(b, x, y);
				}
			}
		}
		
		// draw Portal
		if(world.portal != null) {
			Bitmap portal = Util.RotateBitmap(Assets.portal.getBitmap(), world.portal.rotation);
			x = world.portal.getPosX() - (portal.getWidth() / 2);
			y = world.portal.getPosY() - (portal.getHeight() / 2);
			g.drawBitmap(portal, x, y);
		}
		
		// draw Ship
		Bitmap ship = Util.RotateBitmap(Assets.ship.getBitmap(), world.ship.heading);
		x = world.ship.getPosX() - (ship.getWidth() / 2);
		y = world.ship.getPosY() - (ship.getHeight() / 2);
		g.drawBitmap(ship, x, y);
		
		// draw Line
		if(state == GameState.Ready && player_x != -1) {
			g.drawLine(world.ship.getPosX(), world.ship.getPosY(), player_x, player_y, 0xffffffff);
		}
	}

	@Override
	public void pause() {
		if (state == GameState.Running) {
			oldState = state;
			state = GameState.Paused;
		}
		world.reset();
		Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
