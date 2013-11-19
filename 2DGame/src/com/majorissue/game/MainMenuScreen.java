package com.majorissue.game;

import com.majorissue.framework.Game;
import com.majorissue.framework.Screen;

public class MainMenuScreen extends Screen{

    public MainMenuScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        // TODO
    }

    @Override
    public void present(float deltaTime) {
        
        
        // TODO
    }

    @Override
    public void pause() {
        Settings.save(game.getFileIO());
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

}
