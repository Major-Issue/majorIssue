package majorissue.com.gravity.intro;

import android.graphics.Typeface;

import majorissue.com.framework.Game;
import majorissue.com.framework.Graphics;

public class IntroText {

    private static final float STEP_DURATION = 1/30f;

    public boolean isExpired = false;
    public int id = 0;

    private String textSource = "";
    private String textOut = "";
    private Typeface typeface = null;
    private int fontsize = 20;
    private int pixPosX = 0;
    private int pixPosY = 0;
    private float aliveTime = 0f;
    private float textDuration = 0f;
    private float deltaTimeSum = 0f;
    private int idx;

    public IntroText(Game game, String text, float relPosX, float relPosY, float textDuration, Typeface tf, int fs, int id) {
        this.textSource = text;
        this.pixPosX = (int)(game.getGraphics().getWidth() * relPosX);
        this.pixPosY = (int)(game.getGraphics().getHeight() * relPosY);
        this.textDuration = textDuration;
        this.typeface = tf;
        this.fontsize = fs;
        this.id = id;
        idx = 0;
    }

    public void update(float deltaTime) {
        aliveTime += deltaTime;
        if(aliveTime >= textDuration) {
            isExpired = true;
        }
        deltaTimeSum += deltaTime;
        if(deltaTimeSum < STEP_DURATION) {
            return;
        }
        deltaTimeSum = 0f;

        textOut = textSource.substring(0, idx);
        idx++;
        if(idx > textSource.length()) {
            idx = textSource.length();
        }
    }

    public void draw(Graphics g) {
        g.drawText(pixPosX, pixPosY, fontsize, textOut, typeface);
    }

}
