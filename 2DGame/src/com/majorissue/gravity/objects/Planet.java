package com.majorissue.gravity.objects;

import com.majorissue.gravity.util.Assets;

public class Planet extends OSO {

	public static int PLANET_TYPE_A = 0;
	public static int PLANET_TYPE_B = 1;
	public static int PLANET_TYPE_C = 2;
	public static int PLANET_TYPE_D = 3;
	
	private static final float ROTATION_INCR = -0.1f;
	
	public float rotation = 0f;
	public boolean hasMoon = false;
	public boolean hasStation = false;
	
	public Moon moon;
	public Station station;
	
	public Planet(){};
	
	public void init() {
		collisionRadius = Assets.planet_03.getWidth() / 2;
		if(hasMoon) {
			moon = new Moon();
			moon.init(this);
		}
		if(hasStation) {
			station = new Station();
			station.init(this);
		}
	}
	
	public void update(float deltaTime) {
		rotation += ROTATION_INCR;
		if (rotation >= 360 || rotation <= -360) {
			rotation = 0;
		}
		if(moon != null) {
			moon.update(deltaTime);
		}
		if(station != null) {
			station.update(deltaTime);
		}
	}
	
	public void reset() {
		if(moon != null) {
			moon.reset();
		}
		if(station != null) {
			station.reset();
		}
	}
}
