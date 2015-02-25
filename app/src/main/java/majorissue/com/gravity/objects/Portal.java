package majorissue.com.gravity.objects;

import majorissue.com.framework.Pixmap;
import majorissue.com.gravity.util.Assets;


public class Portal extends OSO {

	public static final int PORTAL_TYPE_A = 0;
	public static final int PORTAL_TYPE_B = 1;
	public static final int PORTAL_TYPE_C = 2;
	public static final int PORTAL_TYPE_D = 3;

	private static final int ROTATION_INCR = 1;

	public float rotation = 0f;
	
	public Portal() {}
	
	public void init() {
		collisionRadius = Assets.portal_01.getWidth() / 2;
	}

	public void update(float deltaTime) {
		rotation += (ROTATION_INCR * rotationDirection);
		if (rotation >= 360 || rotation <= -360) {
			rotation = 0;
		}
	}
	
	public Pixmap getAsset() {
		switch (asset) {
		case 1:
			return Assets.portal_01;
		case 2:
			return Assets.portal_02;
		default:
			return Assets.portal_01;
		}
	}
}