package com.majorissue.gravity.objects;

import com.majorissue.gravity.util.Assets;

public class Moon extends OSO {

	private static final float SELF_ROTATION_INCR = -1f;
	private static final float ORBITAL_ROTATION_INCR = 0.6f;
	public float selfRotation = 0f;
	public float orbitalRotation = 0f;
	
	private OSO root;
	private float distanceToRoot = 0f;
	
	public Moon(){};
	
	public void init(OSO planet) {
		root = planet;
		collisionRadius = Assets.moon.getWidth() / 2;
		
		// init position
		distanceToRoot = root.collisionRadius + collisionRadius * 2f;
		pixPosX = root.pixPosX;
		pixPosY = root.pixPosY - distanceToRoot;
	}
	
	public void update(float deltaTime) {
		// TODO:
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
		// TODO:
		double rad = Math.toRadians(orbitalRotation);
		float yNorm = (float) Math.sin(rad);
		float y = yNorm * distanceToRoot;
		float x = (float) Math.sqrt( ( (Math.pow(distanceToRoot, 2))-(Math.pow(y, 2)) ) );
		float f = 1.0f;
		if(orbitalRotation <= 360) {
			f = 1.0f;
		}
		if(orbitalRotation <= 270) {
			f = -1.0f;
		}
		if(orbitalRotation <= 180) {
			f = -1.0f;
		}
		if(orbitalRotation <= 90) {
			f = 1.0f;
		}
		pixPosX = root.pixPosX + (x*f);
		pixPosY = root.pixPosY + y;
	}
	
	public void reset() {
		// reset
	}
	
}
