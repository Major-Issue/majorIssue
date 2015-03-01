package majorissue.com.gravity;

import java.util.ArrayList;

import majorissue.com.framework.Game;
import majorissue.com.framework.impl.AndroidAnimatedSprite;
import majorissue.com.gravity.objects.Debris;
import majorissue.com.gravity.objects.Gauge;
import majorissue.com.gravity.objects.Planet;
import majorissue.com.gravity.objects.Portal;
import majorissue.com.gravity.objects.Ship;
import majorissue.com.gravity.screens.GameScreen.GameState;
import majorissue.com.gravity.util.Level;
import majorissue.com.gravity.util.PortalAnimation;

public class World {
	
	public Ship ship;
	public Portal portal;
	public ArrayList<Planet> planets;
	public ArrayList<AndroidAnimatedSprite> animations;
	public boolean gameOver = false;
	public Game game;
	public GameOverReason gameOverResason;
    public Gauge gauge;
    public ArrayList<Debris> debris;
    public ArrayList<PortalAnimation> portalAnimations;
	
	public enum GameOverReason {
		LostInSpace,
		Moon,
		Planet,
		Portal,
		Station
	}

	public int inputX = -1;
	public int inputY = -1;

	public World(Game game) {
		this.game = game;
		ship = Level.ship;
		portal = Level.portal;
		planets = Level.planets;
        gauge = new Gauge();
	}
	
	public void init() {
		if(ship != null) {
			// init ship
			ship.pixPosX = (ship.posX * game.getGraphics().getWidth()) / 100;
			ship.pixPosY = (ship.posY * game.getGraphics().getHeight()) / 100;
			ship.init(game);
		}
		if(portal != null) {
			// init portal
			portal.pixPosX = (portal.posX * game.getGraphics().getWidth()) / 100;
			portal.pixPosY = (portal.posY * game.getGraphics().getHeight()) / 100;
			portal.init();
		}
		if(planets != null) {
			// inti planets
			for(Planet planet : planets) {
				planet.pixPosX = (planet.posX * game.getGraphics().getWidth()) / 100;
				planet.pixPosY = (planet.posY * game.getGraphics().getHeight()) / 100;
				planet.init();
			}
		}
        if(gauge != null) {
            gauge.init(game);
        }
	}

	public void update(float deltaTime, GameState state) {
		// this is where we make things move
		if(state == GameState.Ready && ship != null) {
			ship.userInput(inputX, inputY);
            if(gauge != null) {
                gauge.setNeedleHeading(ship.percetageSpeed);
            }
		}
		if(state == GameState.Running) {
			updateRunning(deltaTime);
		}
		if(portal != null) {
			portal.update(deltaTime);
		}
		if(planets != null) {
			for(Planet planet : planets) {
				planet.update(deltaTime);
			}
		}
        if(animations != null && !animations.isEmpty()) {
            updateAnimations(deltaTime);
        }
        if(debris != null && !debris.isEmpty()) {
            updateDebris(deltaTime);
        }
        if(portalAnimations != null && !portalAnimations.isEmpty()) {
            updatePortalAnimations(deltaTime);
        }
	}
	
	private void updateRunning(float deltaTime) {
        if(ship == null) {
            return;
        }

		// ship movement
        ship.updatePosition(deltaTime);

		// gravitational movement
		if(planets != null) {
			for(Planet planet : planets) {
				ship.factorInGravitationalPull(deltaTime, planet);
			}
		}
		if(portal != null) {
			ship.factorInGravitationalPull(deltaTime, portal);
		}
		
		// update heading & velocitiy
		if(ship != null) {
			ship.updateHeading();
		}
		
		// collision
		if(portal != null) {
			if(ship.checkCollision(portal)) {
				gameOver = true;
				gameOverResason = GameOverReason.Portal;
			}
		}
		if(planets != null) {
			for(Planet planet : planets) {
				if(ship.checkCollision(planet)) {
					gameOver = true;
					gameOverResason = GameOverReason.Planet;
				}
				if(planet.hasMoon) {
					if(ship.checkCollision(planet.moon)) {
						gameOver = true;
						gameOverResason = GameOverReason.Moon;
					}
				}
				if(planet.hasStation) {
					if(ship.checkCollision(planet.station)) {
						gameOver = true;
						gameOverResason = GameOverReason.Station;
					}
				}
			}
		}
		
		// lost in space
		if(ship.checkOutOfBounds()) {
			gameOver = true;
			gameOverResason = GameOverReason.LostInSpace;
		}
	}
	
	public void reset() {
		ship = null;
		planets = null;
		portal = null;
	}

    public void addAnimation(AndroidAnimatedSprite animation) {
        if(animations == null) {
            animations = new ArrayList<>();
        }
        animations.add(animation);
    }

    public void updateAnimations(float deltaTime) {
        for(AndroidAnimatedSprite animation : animations) {
            animation.update(deltaTime);
            if(animation.isExpired()) {
                animations.remove(animation);
            }
        }
    }

    public void addDebris(Debris debris) {
        if(this.debris == null) {
            this.debris = new ArrayList<>();
        }
        this.debris.add(debris);
    }

    public void updateDebris(float deltaTime) {
        for(Debris d : debris) {
            d.update(deltaTime);
//            if(d.checkOutOfBounds()) {
////                debris.remove(d);
//                continue;
//            }
//            for(Planet p : planets) {
//                if(d.checkCollision(p)) {
//                    animations.add(new AndroidAnimatedSprite(   d.getPosX(),
//                                                                d.getPosY(),
//                                                                Assets.explosion_01.getBitmap(),
//                                                                64, 64, 10, 5, 5, false));
////                    debris.remove(d);
//                }
//            }
        }
    }

    public void addPortalAnimations(PortalAnimation animation) {
        if(this.portalAnimations == null) {
            this.portalAnimations = new ArrayList<>();
        }
        this.portalAnimations.add(animation);
    }

    public void updatePortalAnimations(float deltaTime) {
        for(PortalAnimation animation : portalAnimations) {
            animation.update(deltaTime);
            if(animation.isExpired()) {
                portalAnimations.remove(animation);
            }
        }
    }
}