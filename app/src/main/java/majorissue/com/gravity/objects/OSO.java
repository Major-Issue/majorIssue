package majorissue.com.gravity.objects;

import majorissue.com.framework.Game;
import majorissue.com.framework.impl.AndroidAnimatedSprite;

public abstract class OSO {

	// after load level
	public int posX;
	public int posY;
	public int gravity;
	public int rotationDirection;
	public int asset;
	public int scale;
	
	// after init world
	public float pixPosX;
	public float pixPosY;
	public int collisionRadius;
	public Game game;
	
	protected void reset() {
		posX = -1;
		posY = -1;
		pixPosX = -1;
		pixPosY = -1;
	}
	
	public int getPosX() {
		return (int) pixPosX;
	}
	
	public int getPosY() {
		return (int) pixPosY;
	}
}