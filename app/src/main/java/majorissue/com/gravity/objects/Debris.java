package majorissue.com.gravity.objects;

import java.util.Random;

import majorissue.com.framework.Game;
import majorissue.com.framework.Pixmap;
import majorissue.com.gravity.util.Assets;

public class Debris extends OSO {

    public int debrisIndex = 0;
    public float rotation = 0f;

    private float incrX = 0;
    private float incrY = 0;
    private int screenWidth;
    private int screenHeight;
    private float rotationIncr = 0f;

    private static final float INCR_MAX = 1f;
    private static final float INCR_MIN = 0.1f;

    public Debris(Game game, int i, int posX, int posY) {
        this.game = game;
        screenWidth = game.getGraphics().getWidth();
        screenHeight = game.getGraphics().getHeight();
        debrisIndex = i;
        pixPosX = posX;
        pixPosY = posY;

        init();
    };

    private void init() {

        Random rand = new Random();
        incrX = (rand.nextFloat() * (INCR_MAX - INCR_MIN) + INCR_MIN) * (rand.nextInt() % 2 == 0 ? 1 : -1);
        incrY = (rand.nextFloat() * (INCR_MAX - INCR_MIN) + INCR_MIN) * (rand.nextInt() % 2 == 0 ? 1 : -1);
        rotationDirection = (rand.nextInt() % 2 == 0 ? 1 : -1);
        rotationIncr = (rand.nextFloat() * (INCR_MAX - INCR_MIN)) + INCR_MIN;

        switch (debrisIndex) {
            case 1:
                collisionRadius = Math.max(Assets.debris_01.getHeight(), Assets.debris_01.getWidth());
                break;
            case 2:
                collisionRadius = Math.max(Assets.debris_02.getHeight(), Assets.debris_02.getWidth());
                break;
            case 3:
                collisionRadius = Math.max(Assets.debris_03.getHeight(), Assets.debris_03.getWidth());
                break;
            case 4:
                collisionRadius = Math.max(Assets.debris_04.getHeight(), Assets.debris_04.getWidth());
                break;
            case 5:
                collisionRadius = Math.max(Assets.debris_05.getHeight(), Assets.debris_05.getWidth());
                break;
            case 6:
                collisionRadius = Math.max(Assets.debris_06.getHeight(), Assets.debris_06.getWidth());
                break;
            case 7:
                collisionRadius = Math.max(Assets.debris_07.getHeight(), Assets.debris_07.getWidth());
                break;
            default:
                break;

        }

    }

    public void update(float deltaTime) {
        rotation += (rotationIncr * rotationDirection);
        if (rotation >= 360 || rotation <= -360) {
            rotation = 0;
        }

        pixPosX += incrX;
        pixPosY += incrY;
    }

    public boolean checkCollision(OSO object) {
        return checkCollision(object.getPosX(), object.getPosY(), object.collisionRadius);
    }

    public boolean checkCollision(int objectX, int objectY, int objectRadius) {
        float distX = objectX - pixPosX;
        float distY = objectY - pixPosY;
        float xSqr = (float)Math.pow(distX, 2);
        float ySqr = (float)Math.pow(distY, 2);
        float hypo = (float)Math.sqrt(xSqr + ySqr);

        if((int)hypo < (objectRadius + collisionRadius)) {
            return true;
        }
        return false;
    }

    public boolean checkOutOfBounds() {
        if(	pixPosX <= 0 ||
                pixPosY <= 0 ||
                pixPosX >= screenWidth ||
                pixPosY >= screenHeight) {
            return true;
        }
        return false;
    }

    public Pixmap getAsset() {
        switch (debrisIndex) {
            case 1:
                return Assets.debris_01;
            case 2:
                return Assets.debris_02;
            case 3:
                return Assets.debris_03;
            case 4:
                return Assets.debris_04;
            case 5:
                return Assets.debris_05;
            case 6:
                return Assets.debris_06;
            case 7:
                return Assets.debris_07;
            default:
                return Assets.debris_01;
        }
    }

}
