package majorissue.com.gravity.screens;

import android.graphics.Rect;
import android.graphics.Typeface;

import com.majorissue.game.R;

import java.util.List;

import majorissue.com.framework.Game;
import majorissue.com.framework.Graphics;
import majorissue.com.framework.Input.TouchEvent;
import majorissue.com.framework.impl.AndroidAnimatedSprite;
import majorissue.com.framework.impl.AndroidGraphics;
import majorissue.com.gravity.GravityGame;
import majorissue.com.gravity.intro.IntroText;
import majorissue.com.gravity.intro.IntroWorld;
import majorissue.com.gravity.util.Assets;
import majorissue.com.gravity.util.Settings;

public class IntroScreen extends MenuScreen {

	public static final int INTRO_FIRST = -1;
	public static final int INTRO_SHOW = 0;
	public static final int INTRO_DO_NOT_SHOW = 1;

    private static final float ANIMATION_FRAME_DURATION = 1/30; //fps

    private float backgroundAnimationTime = 0f;
    private Rect rectOut1;
    private Rect rectOut2;
    private Rect rectSource1;
    private Rect rectSource2;
    private int horizontalOffset = 0;
    private int sourceOutDelta;
    private IntroWorld introWorld;
    private boolean firstExplosion = false;
    private boolean secondExplosion = false;
    private boolean firstText = false;
    private boolean secondText = false;
    private boolean thirdText = false;
    private boolean forthText = false;

	
	public IntroScreen(Game game) {
		super(game);
		if(Settings.introState != INTRO_SHOW) {
			Settings.introState = INTRO_DO_NOT_SHOW;
		}
        rectOut1 = new Rect(0, 0, game.getGraphics().getWidth(), game.getGraphics().getHeight());
        rectOut2 = new Rect(0, 0, 0, 0);
        rectSource1 = new Rect(0, 0, game.getGraphics().getWidth(), game.getGraphics().getHeight());
        rectSource2 = new Rect(0, 0, 0, 0);
        sourceOutDelta = Assets.intro_screen.getWidth() - game.getGraphics().getWidth();
        introWorld = new IntroWorld(game);
        Assets.rocketengine.setLooping(true);
        Assets.rocketengine.play();
	}

	@Override
	public void update(float deltaTime) {
        if(introWorld.forthTextExpired) {
            skip();
        }

		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (event.x > game.getGraphics().getWidth() * 0.8f && 
					event.y < game.getGraphics().getHeight() * 0.1f) {
					skip();
				}
			}
		}

        /* update introWorld */
        if(introWorld.ship.getPosX() < game.getGraphics().getWidth()*0.4f) {
            introWorld.moveShip(2f);
        } else {
            addFirstExplosion();
        }
        addSecondExplosion();
        introWorld.updateAnimations(deltaTime);

        if(introWorld.secondExplosionExpired) {
            addIntroText();
        }
        introWorld.updateIntroTexts(deltaTime);
	}
	
	private void skip() {
        Assets.rocketengine.stop();
		game.setScreen(new MainMenuScreen(game));
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
        playIntro(g, deltaTime);
        g.drawText(AndroidGraphics.TOP_RIGHT, 20, ((GravityGame)game).getResources().getString(R.string.skip), null);
	}

    private void playIntro(Graphics g, float deltaTime) {

        /* background */

        backgroundAnimationTime += deltaTime;
        if(backgroundAnimationTime > ANIMATION_FRAME_DURATION) {
            horizontalOffset += 1;
            if(horizontalOffset > Assets.intro_screen.getWidth()) {
                horizontalOffset = 0;
            }
        }
        rectSource1 = new Rect(horizontalOffset, 0, g.getWidth() + horizontalOffset, g.getHeight());
        g.getCanvas().drawBitmap(Assets.intro_screen.getBitmap(), rectSource1, rectOut1, null);
        if(horizontalOffset - sourceOutDelta > 0) {
            rectSource2 = new Rect(0, 0, horizontalOffset - sourceOutDelta, g.getHeight());
            rectOut2 = new Rect(game.getGraphics().getWidth() - (horizontalOffset - sourceOutDelta), 0, game.getGraphics().getWidth(), game.getGraphics().getHeight());
            g.getCanvas().drawBitmap(Assets.intro_screen.getBitmap(), rectSource2, rectOut2, null);
        }

        /* foreground */

        /* draw ship */
        g.drawPixmap(Assets.ship, introWorld.ship.getPosX(), introWorld.ship.getPosY());
        /* draw Animations */
        if(introWorld.animations != null && !introWorld.animations.isEmpty()) {
            for(AndroidAnimatedSprite animation : introWorld.animations) {
                animation.draw(g.getCanvas());
            }
        }
        /* draw texts */
        if(introWorld.texts != null && !introWorld.texts.isEmpty()) {
            for(IntroText text : introWorld.texts) {
                text.draw(g);
            }
        }

    }

    private void addFirstExplosion(){
        if(!firstExplosion) {
            introWorld.removeAnimation("flame");
            firstExplosion = true;
            introWorld.addAnimation(new AndroidAnimatedSprite(  introWorld.ship.getPosX(),
                                                                introWorld.ship.getPosY() + (int)(Assets.ship.getHeight() * 0.3f),
                                                                Assets.explosion_02.getBitmap(),
                                                                93, 100, 10, 10, 4, false, "firstExplosion",
                                                                game));
            Assets.rocketengine.stop();
            Assets.explosion_sfx_02.play(1);
        }
    }

    private void addSecondExplosion(){
        if(!secondExplosion && introWorld.firstExplosionExpired) {
            secondExplosion = true;
            introWorld.addAnimation(new AndroidAnimatedSprite(  introWorld.ship.getPosX(),
                                                                introWorld.ship.getPosY() + (int)(Assets.ship.getHeight() * 0.5f),
                                                                Assets.explosion_02.getBitmap(),
                                                                93, 100, 10, 10, 4, false, "secondExplosion",
                                                                game));
            Assets.explosion_sfx_02.play(1);
        }
    }

    private void addIntroText() {
        // TODO:
        if(!firstText) {
            /* add first text */
            introWorld.addIntroText(new IntroText(game, "what was that!", 0.2f, 0.2f, 2, Typeface.create("Roboto", Typeface.NORMAL), 25, 11));
            Assets.whatwasthat.play(1);
            firstText = true;
        }
        if(!secondText && introWorld.firstTextExpired) {
            /* add second text */
            introWorld.addIntroText(new IntroText(game, "our engine exploded", 0.6f, 0.3f, 4, Typeface.create("Roboto",Typeface.NORMAL), 20, 21));
            Assets.enginegone.play(1);
            secondText = true;
        }
        if(!thirdText && introWorld.secondTextExpired) {
            /* add third text */
            introWorld.addIntroText(new IntroText(game, "now what?", 0.2f, 0.2f, 2, Typeface.create("Roboto",Typeface.NORMAL), 25, 12));
            Assets.howdowegethome.play(1);
            thirdText = true;
        }
        if(!forthText && introWorld.thirdTextExpired) {
            /* add forth text */
            introWorld.addIntroText(new IntroText(game, "lets try that", 0.6f, 0.3f, 4, Typeface.create("Roboto",Typeface.NORMAL), 20, 22));
            Assets.letstrythat.play(1);
            forthText = true;
        }
    }
}