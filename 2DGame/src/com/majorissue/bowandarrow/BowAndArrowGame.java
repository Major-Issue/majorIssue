
package com.majorissue.bowandarrow;

import com.majorissue.framework.Screen;
import com.majorissue.framework.impl.AndroidGame;

public class BowAndArrowGame extends AndroidGame {

    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }

}
