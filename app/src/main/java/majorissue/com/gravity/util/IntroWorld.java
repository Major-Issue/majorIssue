package majorissue.com.gravity.util;

import java.util.ArrayList;

import majorissue.com.framework.Game;
import majorissue.com.framework.impl.AndroidAnimatedSprite;
import majorissue.com.gravity.objects.Ship;

public class IntroWorld {

    public Game game;
    public Ship ship;
    public ArrayList<AndroidAnimatedSprite> animations;

    public boolean firstExplosionExpired = false;
    public boolean secondExplosionExpired = false;

    public IntroWorld(Game game) {
        this.game = game;
        ship = new Ship();
        animations = new ArrayList<>();
        init();
    }

    private void init() {
        ship.pixPosX = -100;
        ship.pixPosY = game.getGraphics().getHeight() * 0.6f;
        animations.add(new AndroidAnimatedSprite(   ship.getPosX(),
                                                    ship.getPosY() + (int)(Assets.ship.getHeight() * 0.5f),
                                                    Assets.flame.getBitmap(),
                                                    50, 50, 5, 4, 4, true, "flame"));
    }

    public void moveShip(float deltaX) {
        ship.pixPosX += deltaX;
    }

    public void addAnimation(AndroidAnimatedSprite animation) {
        animations.add(animation);
    }

    public void updateAnimations(float deltaTime) {
        if(animations != null && !animations.isEmpty()) {
            for(AndroidAnimatedSprite animation : animations) {
                animation.update(deltaTime);
                if(animation.isExpired()) {
                    if(animation.getAnimationID().equalsIgnoreCase("firstExplosion")) {
                        firstExplosionExpired = true;
                    }
                    if(animation.getAnimationID().equalsIgnoreCase("secondExplosion")) {
                        secondExplosionExpired = true;
                    }
                    animations.remove(animation);
                }
                if(animation.getAnimationID().equalsIgnoreCase("flame")) {
                    animation.updateOutRectangle(ship.getPosX() - 25, ship.getPosY() + (int)(Assets.ship.getHeight() * 0.5f)); // -30 = flame offset x
                }
            }
        }
    }

    public void removeAnimation(String animationID) {
        if(animations != null && !animations.isEmpty()) {
            for(AndroidAnimatedSprite animation : animations) {
                if(animation.getAnimationID().equalsIgnoreCase("flame")) {
                    animations.remove(animation);
                }
            }
        }
    }
}
