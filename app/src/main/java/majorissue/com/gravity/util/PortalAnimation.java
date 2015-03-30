package majorissue.com.gravity.util;

import android.graphics.Bitmap;

import majorissue.com.gravity.objects.OSO;

public class PortalAnimation {

    private static final float ANIMATION_FRAME_DURATION = 1/30;

    private boolean expired = false;
    private OSO object;
    private OSO target;
    private float deltaTimeSum = 0;
    private float decrPercentage = 0.05f;
    private float width = 0;
    private float height = 0;
    private float fX = 0f;
    private float fY = 0f;

    public Bitmap bmpOut;
    public float posX = 0f;
    public float posY = 0f;

    public PortalAnimation(OSO object, OSO target) {
        this.object = object;
        this.target = target;
        posX = object.getPosX();
        posY = object.getPosY();
        width = Assets.ship.getWidth();
        height = Assets.ship.getHeight();
        bmpOut = Assets.ship.getBitmap();

        init();
    }

    private void init() {
        float distX = target.getPosX() - object.getPosX();
        float distY = target.getPosY() - object.getPosY();
        float xSqr = (float)Math.pow(distX, 2);
        float ySqr = (float)Math.pow(distY, 2);
        float hypo = (float)Math.sqrt(xSqr + ySqr);
        fX = distX/hypo;
        fY = distY/hypo;
    }


    public void update(float deltaTime) {
        deltaTimeSum += deltaTime;
        if(deltaTimeSum < ANIMATION_FRAME_DURATION) {
            return;
        }
        deltaTimeSum = 0;

        float newWidth = width - width * decrPercentage;
        float newHeight = height - height * decrPercentage;

        // TODO: scale bitmap and move to center of portal
        bmpOut = Bitmap.createScaledBitmap(Assets.ship.getBitmap(), (int)newWidth, (int)newHeight, true);
        posX += (fX * 5f);
        posY += (fY * 5f);

        decrPercentage += 0.05f;
        if(decrPercentage >= 1f) {
            expired = true;
        }
    }

    public boolean isExpired() {
        return expired;
    }

}
