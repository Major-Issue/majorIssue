
package com.majorissue.game;

import com.majorissue.framework.Game;
import com.majorissue.framework.Graphics;
import com.majorissue.framework.Screen;
import com.majorissue.framework.Graphics.PixmapFormat;

public class LoadingScreen extends Screen {

    public LoadingScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.arrow = g.newPixmap("arrow.png", PixmapFormat.ARGB4444);
        Assets.background = g.newPixmap("background.png", PixmapFormat.RGB565);
        Assets.menu_help = g.newPixmap("menu_help.png", PixmapFormat.ARGB4444);
        Assets.menu_play = g.newPixmap("menu_play.png", PixmapFormat.ARGB4444);
        Assets.menu_quit = g.newPixmap("menu_quit.png", PixmapFormat.ARGB4444);
        Assets.menu_ready = g.newPixmap("menu_ready.png", PixmapFormat.ARGB4444);
        Assets.menu_resume = g.newPixmap("meun_resume.png", PixmapFormat.ARGB4444);
        Assets.player_body = g.newPixmap("player_body.png", PixmapFormat.ARGB4444);
        Assets.player_bow = g.newPixmap("player_bow.png", PixmapFormat.ARGB4444);
        Assets.target = g.newPixmap("target.png", PixmapFormat.ARGB4444);
        Assets.title = g.newPixmap("title.png", PixmapFormat.ARGB4444);
        Assets.wall = g.newPixmap("wall.png", PixmapFormat.ARGB4444);
        // ...
        Settings.load(game.getFileIO());
        game.setScreen(new MainMenuScreen(game));

    }

    @Override
    public void present(float deltaTime) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

}
