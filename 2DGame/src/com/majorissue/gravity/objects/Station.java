package com.majorissue.gravity.objects;

import com.majorissue.gravity.util.Assets;

public class Station extends OSO {

	public static int STATION_TYPE_A = 0;
	public static int STATION_TYPE_B = 1;
	public static int STATION_TYPE_C = 2;
	public static int STATION_TYPE_D = 3;

	private static final float SELF_ROTATION_INCR = 0.1f;
	private static float ORBITAL_ROTATION_INCR = 0.2f;
	public float selfRotation = 0f;
	public float orbitalRotation = 0f;

	private OSO root;
	private float distanceToRoot = 0f;

	public Station() {};

	public void init(OSO planet) {
		root = planet;
		collisionRadius = Assets.station.getWidth() / 2;

		// init position
		distanceToRoot = root.collisionRadius + collisionRadius * 2f;
		pixPosX = root.pixPosX;
		pixPosY = root.pixPosY - distanceToRoot;
	}

	public void update(float deltaTime) {
		// self-rotation
		selfRotation += SELF_ROTATION_INCR;
		if (selfRotation >= 360 || selfRotation <= -360) {
			selfRotation = 0;
		}
		// orbital-rotation
		orbitalRotation += ORBITAL_ROTATION_INCR;
		if (orbitalRotation >= 360 || orbitalRotation <= -360) {
			orbitalRotation = 0;
		}
		updateOrbitalPosition();
	}

	private void updateOrbitalPosition() {
		double rad = Math.toRadians(orbitalRotation);
		float yNorm = (float) Math.sin(rad);
		float y = yNorm * distanceToRoot;
		float x = (float) Math.sqrt( ( (Math.pow(distanceToRoot, 2))-(Math.pow(y, 2)) ) );
		float f = getRotationCorrection();
		pixPosX = root.pixPosX + (x*f) * (-1.0f);
		pixPosY = root.pixPosY + y;
	}
	
	private float getRotationCorrection() {
		float f = 1.0f;
		if(orbitalRotation <= 270) {
			f = -1.0f;
		}
		if(orbitalRotation <= 90) {
			f = 1.0f;
		}
		return f;
	}
	
	public void reset() {
		// reset
		orbitalRotation = 0f;
	}
}
