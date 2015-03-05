package majorissue.com.gravity.screens;

import android.graphics.Typeface;

import majorissue.com.framework.Game;
import majorissue.com.framework.Graphics;
import majorissue.com.framework.Graphics.PixmapFormat;
import majorissue.com.framework.impl.AndroidGraphics;
import majorissue.com.gravity.util.Assets;
import majorissue.com.gravity.util.Level;
import majorissue.com.gravity.util.Settings;

public class LoadingScreen extends MenuScreen {

	private final float LOADING_TIME_MIN = 2; // sec
	
	private float loadingTime = 0;
	private boolean loadingComplete = false;

	public LoadingScreen(Game game) {
		super(game);
		loadAssets();
		Settings.load(game.getFileIO());
		Level.loadMetaData(assets);
		loadingComplete = true;
	}

	private void loadAssets(){
		// graphics
		Graphics g = game.getGraphics();
		Assets.background_menu_01 = g.newPixmap("gravity/graphics/background_menu_01.jpg", PixmapFormat.RGB565);
        Assets.intro_background = g.newPixmap("gravity/graphics/intro_background.jpg", PixmapFormat.RGB565);
		Assets.portal_01 = g.newPixmap("gravity/graphics/portal_01.png", PixmapFormat.ARGB4444);
		Assets.portal_02 = g.newPixmap("gravity/graphics/portal_02.png", PixmapFormat.ARGB4444);
		Assets.ship = g.newPixmap("gravity/graphics/ship.png", PixmapFormat.ARGB4444);
		Assets.planet_01 = g.newPixmap("gravity/graphics/planet_01.png", PixmapFormat.ARGB4444);
		Assets.planet_02 = g.newPixmap("gravity/graphics/planet_02.png", PixmapFormat.ARGB4444);
		Assets.planet_03 = g.newPixmap("gravity/graphics/planet_03.png", PixmapFormat.ARGB4444);
		Assets.station = g.newPixmap("gravity/graphics/station.png", PixmapFormat.ARGB4444);
		Assets.moon_01 = g.newPixmap("gravity/graphics/moon_01.png", PixmapFormat.ARGB4444);
        Assets.hub_boost = g.newPixmap("gravity/graphics/hud_boost.png", PixmapFormat.ARGB4444);
        Assets.explosion_01 = g.newPixmap("gravity/graphics/explosion_01.png", PixmapFormat.ARGB4444);
        Assets.explosion_02 = g.newPixmap("gravity/graphics/explosion_02.png", PixmapFormat.ARGB4444);
        Assets.flame = g.newPixmap("gravity/graphics/flame.png", PixmapFormat.ARGB4444);

        Assets.debris_01 = g.newPixmap("gravity/graphics/debris_01.png", PixmapFormat.ARGB4444);
        Assets.debris_02 = g.newPixmap("gravity/graphics/debris_02.png", PixmapFormat.ARGB4444);
        Assets.debris_03 = g.newPixmap("gravity/graphics/debris_03.png", PixmapFormat.ARGB4444);
        Assets.debris_04 = g.newPixmap("gravity/graphics/debris_04.png", PixmapFormat.ARGB4444);
        Assets.debris_05 = g.newPixmap("gravity/graphics/debris_05.png", PixmapFormat.ARGB4444);
        Assets.debris_06 = g.newPixmap("gravity/graphics/debris_06.png", PixmapFormat.ARGB4444);
        Assets.debris_07 = g.newPixmap("gravity/graphics/debris_07.png", PixmapFormat.ARGB4444);
        Assets.debris_08 = g.newPixmap("gravity/graphics/astronaut_01.png", PixmapFormat.ARGB4444);
        Assets.debris_09 = g.newPixmap("gravity/graphics/astronaut_02.png", PixmapFormat.ARGB4444);

		// sounds
		Assets.menu_click = game.getAudio().newSound("gravity/sound/click_01.ogg");
        Assets.explosion_sfx_01 = game.getAudio().newSound("gravity/sound/explosion_01.ogg");
        Assets.explosion_sfx_02 = game.getAudio().newSound("gravity/sound/explosion_02.ogg");
        Assets.swoosh_01 = game.getAudio().newSound("gravity/sound/swoosh_01.ogg");
		
		// music
		Assets.music_bkg_01 = game.getAudio().newMusic("gravity/music/menu_background_01.ogg");
        Assets.rocketengine = game.getAudio().newMusic("gravity/music/rocketengine.ogg");
	}
	
	@Override
	public void update(float deltaTime) {
		loadingTime += deltaTime;
		if(loadingComplete && loadingTime > LOADING_TIME_MIN) {
			if(Settings.introState == IntroScreen.INTRO_FIRST || Settings.introState == IntroScreen.INTRO_SHOW) {
				game.setScreen(new IntroScreen(game));
			} else {
				game.setScreen(new MainMenuScreen(game));
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.background_menu_01, 0, 0);
		Typeface tf = Typeface.create("Roboto",Typeface.BOLD_ITALIC);
		g.drawText(AndroidGraphics.CENTER, 25, "LOADING ...", tf);
	}
}