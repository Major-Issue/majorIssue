package com.majorissue.gravity;

import java.util.ArrayList;

import com.majorissue.framework.Game;
import com.majorissue.gravity.objects.Planet;
import com.majorissue.gravity.objects.Portal;
import com.majorissue.gravity.objects.Ship;
import com.majorissue.gravity.objects.Station;
import com.majorissue.gravity.screens.GameScreen.GameState;
import com.majorissue.gravity.util.Level;

public class World {
	
	public Ship ship;
	public Portal portal;
	public ArrayList<Planet> planets;
	public ArrayList<Station> stations;
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
		stations = Level.stations;
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
		if(stations != null) {
			// init stations
			for(Station station : stations) {
				station.pixPosX = (station.posX * game.getGraphics().getWidth()) / 100;
				station.pixPosY = (station.posY * game.getGraphics().getHeight()) / 100;
				station.init();
			}
		}
	}

	public void update(float deltaTime, GameState state) {
		// this is where we make things move
		if(state == GameState.Ready) {
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
		if(stations != null) {
			for(Station station : stations) {
				station.update(deltaTime);
			}
		}
	}
	
	private void updateRunning(float deltaTime) {
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
		ship.updateHeading();
		
		// collision
		if(portal != null) {
			if(ship.checkCollision(portal.getPosX(), portal.getPosY(), portal.collisionRadius)) {
				gameWon = true;
			}
		}
		if(planets != null) {
			for(Planet planet : planets) {
				if(ship.checkCollision(planet.getPosX(), planet.getPosY(), planet.collisionRadius)) {
					gameOver = true;
				}
			}
		}
		if(stations != null) {
			for(Station station : stations) {
				if(ship.checkCollision(station.getPosX(), station.getPosY(), station.collisionRadius)) {
					gameOver = true;
				}
			}
		}
		
		// lost in space
		if(ship.checkOutOfBounds()) {
			gameOver = true;
		}
	}
	
	public void reset() {
		ship.reset();
		inputX = -1;
		inputY = -1;
		if(stations != null) {
			for(Station station : stations) {
				station.reset();
			}
		}
	}
}
