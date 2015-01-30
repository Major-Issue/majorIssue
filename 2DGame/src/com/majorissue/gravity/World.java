package com.majorissue.gravity;

import java.util.ArrayList;

import com.majorissue.framework.Game;
import com.majorissue.gravity.objects.Planet;
import com.majorissue.gravity.objects.Portal;
import com.majorissue.gravity.objects.Ship;
import com.majorissue.gravity.screens.GameScreen.GameState;
import com.majorissue.gravity.util.Level;

public class World {
	
	public Ship ship;
	public Portal portal;
	public ArrayList<Planet> planets;
	public boolean gameOver = false;
	public boolean gameWon = false;
	public Game game;

	public int inputX = -1;
	public int inputY = -1;

	public World(Game game) {
		this.game = game;
		ship = Level.ship;
		portal = Level.portal;
		planets = Level.planets;
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
	}

	public void update(float deltaTime, GameState state) {
		// this is where we make things move
		if(state == GameState.Ready && ship != null) {
			ship.userInput(inputX, inputY);
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
	}
	
	private void updateRunning(float deltaTime) {
		// ship movement
		if(ship != null) {
			ship.updatePosition(deltaTime);
		}
		
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
		if(ship != null && portal != null) {
			if(ship.checkCollision(portal)) {
				gameWon = true;
			}
		}
		if(ship != null && planets != null) {
			for(Planet planet : planets) {
				if(ship.checkCollision(planet)) {
					gameOver = true;
				}
				if(planet.hasMoon) {
					if(ship.checkCollision(planet.moon)) {
						gameOver = true;
					}
				}
				if(planet.hasStation) {
					if(ship.checkCollision(planet.station)) {
						gameWon = true;
					}
				}
			}
		}
		
		// lost in space
		if(ship != null && ship.checkOutOfBounds()) {
			gameOver = true;
		}
	}
	
	public void reset() {
		ship = null;
		planets = null;
		portal = null;
		
//		ship.reset();
//		inputX = -1;
//		inputY = -1;
//		
//		if(planets != null) {
//			for(Planet planet : planets) {
//				planet.reset();
//			}
//		}
	}
}
