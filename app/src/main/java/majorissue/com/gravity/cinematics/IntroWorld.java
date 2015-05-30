package majorissue.com.gravity.cinematics;

import java.util.ArrayList;

import majorissue.com.framework.Game;
import majorissue.com.framework.impl.AndroidAnimatedSprite;
import majorissue.com.gravity.objects.Ship;
import majorissue.com.gravity.util.Assets;
import majorissue.com.gravity.util.Util;

public class IntroWorld {

    public Game game;
    public Ship ship;
    public ArrayList<AndroidAnimatedSprite> animations;
    public ArrayList<CinematicsText> texts;

    public boolean firstExplosionExpired = false;
    public boolean secondExplosionExpired = false;

    public boolean firstTextExpired = false;
    public boolean secondTextExpired = false;
    public boolean thirdTextExpired = false;
    public boolean forthTextExpired = false;

    public IntroWorld(Game game) {
        this.game = game;
        ship = new Ship();
        animations = new ArrayList<>();
        texts = new ArrayList<>();
        init();
    }

    private void init() {
        ship.pixPosX = -100;
        ship.pixPosY = game.getGraphics().getHeight() * 0.6f;
        animations.add(new AndroidAnimatedSprite(   ship.getPosX(),
                                                    ship.getPosY(),
                                                    Assets.flame.getBitmap(),
                                                    50, 50, 5, 4, 4, true, "flame",
                                                    game));
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
                    animation.updateOutRectangle(ship.getPosX() - Util.getScaledOffset(game.getScaleX(), 25f), ship.getPosY() + (int)(Assets.ship.getHeight() * 0.5f)); // -25 = flame offset x
                }
            }
        }
    }

    public void removeAnimation(String animationID) {
        if(animations != null && !animations.isEmpty()) {
            for(AndroidAnimatedSprite animation : animations) {
                if(animation.getAnimationID().equalsIgnoreCase(animationID)) {
                    animations.remove(animation);
                }
            }
        }
    }

    public void addIntroText(CinematicsText text) {
        texts.add(text);
    }

    public void updateIntroTexts(float deltaTime) {
        if(texts != null && !texts.isEmpty()) {
            for(CinematicsText text : texts) {
                text.update(deltaTime);
                if(text.isExpired) {
                    switch (text.id) {
                        case 11:
                            firstTextExpired = true;
                            break;
                        case 21:
                            secondTextExpired = true;
                            break;
                        case 12:
                            thirdTextExpired = true;
                            break;
                        case 22:
                            forthTextExpired = true;
                            break;
                        default:
                            break;
                    }
                    texts.remove(text);
                }
            }
        }
    }

}
