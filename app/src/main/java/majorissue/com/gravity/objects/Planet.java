package majorissue.com.gravity.objects;

import majorissue.com.framework.Pixmap;
import majorissue.com.gravity.util.Assets;

public class Planet extends OSO {
	
	private static final float ROTATION_INCR = 0.1f;
	
	public float rotation = 0f;
	public boolean hasMoon = false;
	public boolean hasStation = false;
	
	public Moon moon;
	public Station station;
	
	public Planet(){}
	
	public void init() {
		collisionRadius = Assets.planet_earth.getWidth() / 2;
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
		rotation += (ROTATION_INCR * rotationDirection);
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
	
	public Pixmap getAsset() {
		switch (asset) {
		case 1:
			return Assets.planet_earth;
		case 2:
			return Assets.planet_blue;
		case 3:
			return Assets.planet_orange;
		case 4:
			return Assets.planet_purple;
		case 5:
			return Assets.planet_green;
		case 6:
			return Assets.planet_red;
		case 7:
			return Assets.planet_yellow;
		default:
			return Assets.planet_earth;
		}
	}
}