package majorissue.com.gravity.objects;

import majorissue.com.framework.Game;
import majorissue.com.framework.impl.AndroidGraphics;
import majorissue.com.gravity.util.Assets;

public class Gauge extends OSO {

    public int needleAnchorX = 0;
    public int needleAnchorY = 0;
    public int needleTargetX = 0;
    public int needleTargetY = 0;
    public float needleHeadingRange = 60f;
    private int needleSize = 0;

    public float angle = 0f;


    public Gauge() {};

    public void init(Game game) {
        this.game = game;
        pixPosX = game.getGraphics().getWidth() * 0.9f;
        pixPosY = game.getGraphics().getHeight() * 0.0f;
        needleAnchorX = (int) (pixPosX + Assets.hub_boost.getWidth()/2);
        needleAnchorY = (int) (pixPosY + Assets.hub_boost.getHeight());
        needleTargetX = (int) (pixPosX + Assets.hub_boost.getWidth()/2);
        needleTargetY = (int) (pixPosY + (Assets.hub_boost.getHeight() * 0.2f));
        needleSize = needleAnchorY - needleTargetY;
        setNeedleHeading(0);
    }

    public void setNeedleHeading(int p) {
        angle = p/100f * needleHeadingRange;
        angle += 240;

        double rad = Math.toRadians(angle);
        float yNorm = (float) Math.sin(rad);
        float y = yNorm * needleSize;
        float x = (float) Math.sqrt( ( (Math.pow(needleSize, 2))-(Math.pow(y, 2)) ) );
        float f = getRotationCorrection(angle);
        needleTargetX = (int) (needleAnchorX + (x*f));
        needleTargetY = (int) (needleAnchorY + y);
    }

    private float getRotationCorrection(float angle) {
        float f = 1.0f;
        if(angle <= 270) {
            f = -1.0f;
        }
        if(angle <= 90) {
            f = 1.0f;
        }
        return f;
    }
}
