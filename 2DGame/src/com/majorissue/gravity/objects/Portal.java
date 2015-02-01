package com.majorissue.gravity.objects;

import com.majorissue.gravity.util.Assets;


public class Portal extends OSO {

	public static final int PORTAL_TYPE_A = 0;
	public static final int PORTAL_TYPE_B = 1;
	public static final int PORTAL_TYPE_C = 2;
	public static final int PORTAL_TYPE_D = 3;

	private static final int ROTATION_INCR = 1;

	public float rotation = 0f;
	
	public Portal() {}
	
	public void init() {
		collisionRadius = Assets.portal.getWidth() / 2;
	}

	public void update(float deltaTime) {
		rotation += (ROTATION_INCR * rotationDirection);
		if (rotation >= 360 || rotation <= -360) {
			rotation = 0;
		}
	}
}