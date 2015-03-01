package majorissue.com.gravity.screens;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Vibrator;

import com.majorissue.game.R;

import java.util.List;

import majorissue.com.framework.Game;
import majorissue.com.framework.Graphics;
import majorissue.com.framework.Input.TouchEvent;
import majorissue.com.framework.impl.AndroidAnimatedSprite;
import majorissue.com.framework.impl.AndroidGraphics;
import majorissue.com.gravity.GravityGame;
import majorissue.com.gravity.World;
import majorissue.com.gravity.World.GameOverReason;
import majorissue.com.gravity.objects.Debris;
import majorissue.com.gravity.objects.Planet;
import majorissue.com.gravity.util.Assets;
import majorissue.com.gravity.util.Level;
import majorissue.com.gravity.util.PortalAnimation;
import majorissue.com.gravity.util.Settings;
import majorissue.com.gravity.util.Util;

public class GameScreen extends MenuScreen {

	public static final int RESOLUTION_NATIVE = 0;
	public static final int RESOLUTION_HALF = 1;
	public static final float DELTA_TIME_MIN = 0.02f;
	
	private final float LOADING_TIME_MIN = 1f; // sec
	
	public enum GameState {
		Loading,
		Ready,
		Running,
		Paused,
		GameOver
	}

	private GameState oldState = GameState.Loading;
	private GameState state = GameState.Loading;
	private World world;
	private int player_x = -1;
	private int player_y = -1;
	private float loadingTime = 0.0f;
	private boolean loadingComplete = false;
	private int fps = 0;
    private Vibrator v;
	
	private String[][] menuTouchAreas = null;
	private String touchedMenuEntry = null;

	public GameScreen(Game game, int extraLvl) {
		super(game);
		loadLevel(extraLvl);
        v = (Vibrator) game.getContext().getSystemService(Context.VIBRATOR_SERVICE);
	}

	private void loadLevel(int extraLvl) {
		if (extraLvl == 0) {
			Level.loadLevel(Settings.currentLevel, Level.LEVEL_STORY, assets);
		} else {
			Level.loadLevel(extraLvl, Level.LEVEL_EXTRA, assets);
		}
		Settings.save(game.getFileIO());
		loadingComplete = true;
	}

	@Override
	public void update(float deltaTime) {

		fps = (int) (1/deltaTime);
		
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		switch (state) {
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
		Level.loadLevel(Settings.currentLevel + 1, Level.LEVEL_STORY, assets);
		loadingComplete = true;
	}
	
	private void retryLevel() {
		player_x = -1;
		player_y = -1;
		state = oldState;
		state = GameState.Loading;
		world.reset();
		Level.loadLevel(Settings.currentLevel, Level.LEVEL_STORY, assets);
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
			if (event.type == TouchEvent.TOUCH_DOWN) {
				checkTouchDown(event);
			}
			if (event.type == TouchEvent.TOUCH_UP) {
				touchedMenuEntry = null;
				checkTouchUp(event);
			}
		}
		updateWorld(deltaTime);
		
		if(Settings.autoretry && gameLost()) {
			retryLevel();
		}
	}
	
	private boolean gameLost() {
		if(	world.gameOverResason == GameOverReason.LostInSpace ||
			world.gameOverResason == GameOverReason.Moon ||
			world.gameOverResason == GameOverReason.Planet) {
			return true;
		}
		return false;
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
	
	private void playSound(String entry) {
		if(entry == null || !Settings.soundEnabled) {
			return;
		}
		Assets.menu_click.play(1);
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
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.next_level))) {
			loadNextLevel();
		}
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.retry))) {
			retryLevel();
		}
		if(entry.equals(((GravityGame)game).getResources().getString(R.string.mainmenu))) {
			game.setScreen(new MainMenuScreen(game));
		}
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
                if(Settings.soundEnabled) {
                    Assets.swoosh_01.play(1);
                }
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

		if(state == GameState.Running) {
			if (world.gameOver) {
				oldState = state;
				state = GameState.GameOver;
                addGameOverAnimation();
			}
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
		
		g.drawText(AndroidGraphics.BOTTOM_RIGHT, 20, fps + " fps", null);
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
		switch (world.gameOverResason) {
		case Portal:
		case Station:
			// next level
			menuTouchAreas = drawMenu(new String[]{	((GravityGame)game).getResources().getString(R.string.next_level),
													((GravityGame)game).getResources().getString(R.string.mainmenu)
													}, touchedMenuEntry);
			break;
		default:
			// retry
			menuTouchAreas = drawMenu(new String[]{	((GravityGame)game).getResources().getString(R.string.retry),
													((GravityGame)game).getResources().getString(R.string.mainmenu)
													}, touchedMenuEntry);
			break;
		}
	}

    private void addGameOverAnimation() {
        switch (world.gameOverResason) {
            case Planet:
                world.addAnimation(new AndroidAnimatedSprite(   world.ship.getPosX(),
                                                                world.ship.getPosY(),
                                                                Assets.explosion_01.getBitmap(),
                                                                64, 64, 10, 5, 5, false));
                if(Settings.soundEnabled) {
                    Assets.explosion_sfx_02.play(1);
                }
                vibrate(300);
                break;
            case Moon:
                world.addAnimation(new AndroidAnimatedSprite(   world.ship.getPosX(),
                                                                world.ship.getPosY(),
                                                                Assets.explosion_02.getBitmap(),
                                                                93, 100, 10, 10, 4, false));
                addDebris(world.ship.getPosX(), world.ship.getPosY());
                if(Settings.soundEnabled) {
                    Assets.explosion_sfx_01.play(1);
                }
                vibrate(200);
                break;
            case Portal:
                world.addPortalAnimations(new PortalAnimation(world.ship, world.portal));
                // TODO: sound, vibration
                break;
            default:
                break;
        }
    }

    private void vibrate(long duration) {
        if(Settings.vibrate && v != null) {
            v.vibrate(duration);
        }
    }

    private void addDebris(int x, int y) {
        for(int i = 0; i < 7; i++) {
            world.addDebris(new Debris(game, i+1, x, y));
        }
    }
	
	private void drawWorld(Graphics g) {
        if(world == null) {
            return;
        }

		int x = 0;
		int y = 0;
		
		// draw Planets
		if(world.planets != null) {
			for(Planet planet : world.planets) {
				Bitmap b = Util.RotateBitmap(planet.getAsset().getBitmap(), planet.rotation);
				x = planet.getPosX() - (b.getWidth() / 2);
				y = planet.getPosY() - (b.getHeight() / 2);
				g.drawBitmap(b, x, y);
				if(planet.hasMoon) {
					b = Util.RotateBitmap(Assets.moon_01.getBitmap(), planet.moon.selfRotation);
					x = planet.moon.getPosX() - (b.getWidth() / 2);
					y = planet.moon.getPosY() - (b.getHeight() / 2);
					g.drawBitmap(b, x, y);
				}
				if(planet.hasStation) {
					b = Util.RotateBitmap(Assets.station.getBitmap(), planet.station.selfRotation);
					x = planet.station.getPosX() - (b.getWidth() / 2);
					y = planet.station.getPosY() - (b.getHeight() / 2);
					g.drawBitmap(b, x, y);
				}
			}
		}
		
		// draw Portal
		if(world.portal != null) {
			Bitmap portal = Util.RotateBitmap(world.portal.getAsset().getBitmap(), world.portal.rotation);
			x = world.portal.getPosX() - (portal.getWidth() / 2);
			y = world.portal.getPosY() - (portal.getHeight() / 2);
			g.drawBitmap(portal, x, y);
		}
		
		// draw Ship
		if(world.ship != null && !world.gameOver) {

            // draw Line
            if(Settings.aidline && state == GameState.Ready && player_x != -1) {
                g.drawLine(world.ship.getPosX(), world.ship.getPosY(), player_x, player_y, 0xffffffff);
            }

			Bitmap ship = Util.RotateBitmap(Assets.ship.getBitmap(), world.ship.heading);
			x = world.ship.getPosX() - (ship.getWidth() / 2);
			y = world.ship.getPosY() - (ship.getHeight() / 2);
			g.drawBitmap(ship, x, y);
		}

        //draw Debris
        if(world.debris != null && !world.debris.isEmpty()) {
            for(Debris debris : world.debris) {
                Bitmap d = Util.RotateBitmap(debris.getAsset().getBitmap(), debris.rotation);
                x = debris.getPosX() - (d.getWidth() / 2);
                y = debris.getPosY() - (d.getHeight() / 2);
                g.drawBitmap(d, x, y);
            }
        }

        //draw Animations
        if(world.animations != null && !world.animations.isEmpty()) {
            for(AndroidAnimatedSprite animation : world.animations) {
                animation.draw(g.getCanvas());
            }
        }

        //draw PostalAnimation
        if(world.portalAnimations != null && !world.portalAnimations.isEmpty()) {
            for(PortalAnimation animation : world.portalAnimations) {
                g.drawBitmap(animation.bmpOut, (int)animation.posX, (int)animation.posY);
            }
        }

        // draw Gauge
        if(world.gauge != null && world.ship != null) {
            g.drawPixmap(Assets.hub_boost, world.gauge.getPosX(), world.gauge.getPosY());
            g.drawLine(world.gauge.needleAnchorX, world.gauge.needleAnchorY, world.gauge.needleTargetX, world.gauge.needleTargetY, 0xffff0000);
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
	public void resume() {}

	@Override
	public void dispose() {}
}