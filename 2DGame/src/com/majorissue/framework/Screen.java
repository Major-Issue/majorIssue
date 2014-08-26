package com.majorissue.framework;

import android.content.res.AssetManager;

public abstract class Screen {

	protected final Game game;
	protected final AssetManager assets;

	public Screen(Game game) {
		this.game = game;
		this.assets = game.getContext().getAssets();
	}

	public abstract void update(float deltaTime);

	public abstract void present(float deltaTime);

	public abstract void pause();

	public abstract void resume();

	public abstract void dispose();

}
