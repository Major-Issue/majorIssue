package majorissue.com.gravity.intro;

import java.util.ArrayList;

import majorissue.com.framework.Game;
import majorissue.com.framework.impl.AndroidAnimatedSprite;
import majorissue.com.gravity.objects.Ship;
import majorissue.com.gravity.util.Assets;

public class IntroWorld {

    public Game game;
    public Ship ship;
    public ArrayList<AndroidAnimatedSprite> animations;
    public ArrayList<IntroText> texts;

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
                    animation.updateOutRectangle(ship.getPosX() - 25, ship.getPosY() + (int)(Assets.ship.getHeight() * 0.5f)); // -25 = flame offset x
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

    public void addIntroText(IntroText text) {
        texts.add(text);
    }

    public void updateIntroTexts(float deltaTime) {
        if(texts != null && !texts.isEmpty()) {
            for(IntroText text : texts) {
                text.update(deltaTime);
                if(text.isExpired) {
                    switch (text.id) {
                        case 1:
                            firstTextExpired = true;
                            break;
                        case 2:
                            secondTextExpired = true;
                            break;
                        case 3:
                            thirdTextExpired = true;
                            break;
                        case 4:
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
