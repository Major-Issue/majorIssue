
package com.majorissue.tutorial;

import android.content.Context;

import com.majorissue.framework.Screen;
import com.majorissue.framework.impl.AndroidGame;

public class My2DGame extends AndroidGame {

    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }

	@Override
	public Context getContext() {
		return this;
	}

}
