
package com.majorissue.tutorial;

import com.majorissue.framework.Game;
import com.majorissue.framework.Graphics;
import com.majorissue.framework.Graphics.PixmapFormat;
import com.majorissue.framework.Screen;

public class LoadingScreen extends Screen {

    public LoadingScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
    	Graphics g = game.getGraphics();
        Assets.background = g.newPixmap("nom/background.png", PixmapFormat.RGB565);
        Assets.logo = g.newPixmap("nom/logo.png", PixmapFormat.ARGB4444);
        Assets.mainMenu = g.newPixmap("nom/mainmenu.png", PixmapFormat.ARGB4444);
        Assets.buttons = g.newPixmap("nom/buttons.png", PixmapFormat.ARGB4444);
        Assets.help1 = g.newPixmap("nom/help1.png", PixmapFormat.ARGB4444);
        Assets.help2 = g.newPixmap("nom/help2.png", PixmapFormat.ARGB4444);
        Assets.help3 = g.newPixmap("nom/help3.png", PixmapFormat.ARGB4444);
        Assets.numbers = g.newPixmap("nom/numbers.png", PixmapFormat.ARGB4444);
        Assets.ready = g.newPixmap("nom/ready.png", PixmapFormat.ARGB4444);
        Assets.pause = g.newPixmap("nom/pausemenu.png", PixmapFormat.ARGB4444);
        Assets.gameOver = g.newPixmap("nom/gameover.png", PixmapFormat.ARGB4444);
        Assets.headUp = g.newPixmap("nom/headup.png", PixmapFormat.ARGB4444);
        Assets.headLeft = g.newPixmap("nom/headleft.png", PixmapFormat.ARGB4444);
        Assets.headDown = g.newPixmap("nom/headdown.png", PixmapFormat.ARGB4444);
        Assets.headRight = g.newPixmap("nom/headright.png", PixmapFormat.ARGB4444);
        Assets.tail = g.newPixmap("nom/tail.png", PixmapFormat.ARGB4444);
        Assets.stain1 = g.newPixmap("nom/stain1.png", PixmapFormat.ARGB4444);
        Assets.stain2 = g.newPixmap("nom/stain2.png", PixmapFormat.ARGB4444);
        Assets.stain3 = g.newPixmap("nom/stain3.png", PixmapFormat.ARGB4444);
        Assets.click = game.getAudio().newSound("nom/click.ogg");
        Assets.eat = game.getAudio().newSound("nom/eat.ogg");
        Assets.bitten = game.getAudio().newSound("nom/bitten.ogg");
        Settings.load(game.getFileIO());
        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void present(float deltaTime) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {}

}
