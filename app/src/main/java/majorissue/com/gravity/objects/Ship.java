package majorissue.com.gravity.objects;

import majorissue.com.framework.Game;
import majorissue.com.gravity.util.Assets;

public class Ship extends OSO {

	public float heading = 0f;
	public float veloX = 0f;
	public float veloY = 0f;
	public int pulse = 0;
	public int percetageSpeed = 0;
	
	private int screenWidth;
	private int screenHeight;
	private float pixPosOldX = 0f;
	private float pixPosOldY = 0f;
	
	public Ship(){};
	
	public void init(Game game) {
		this.game = game;
		screenWidth = game.getGraphics().getWidth();
		screenHeight = game.getGraphics().getHeight();
		collisionRadius = Assets.ship.getWidth() / 2;
		pixPosOldX = pixPosX;
		pixPosOldY = pixPosY;
	}
	
	public void updatePosition(float deltaTime) {
		pixPosOldX = pixPosX;
		pixPosOldY = pixPosY;
		float incrX = veloX * (percetageSpeed * 0.1f);
		float incrY = veloY * (percetageSpeed * 0.1f);
		pixPosX += incrX;
		pixPosY += incrY;
	}
	
	public void factorInGravitationalPull(float deltaTime, OSO object) {
		float deltaGX = object.pixPosX - pixPosX;
		float deltaGY = object.pixPosY - pixPosY;
		float xSqr = (float)Math.pow(deltaGX, 2);
		float ySqr = (float)Math.pow(deltaGY, 2);
		float hypo = (float)Math.sqrt(xSqr + ySqr);
		float xNorm = deltaGX/hypo;
		float yNorm = deltaGY/hypo;
		
		// TODO: make gravity exponential
		float pull = (float)object.gravity / hypo;
				
		float deltaX = pull * xNorm;
		float deltaY = pull * yNorm;
		pixPosX += deltaX;
		pixPosY += deltaY;
	}
	
	public void updateHeading() {
		float distX = pixPosX - pixPosOldX;
		float distY = pixPosY - pixPosOldY;
		float xSqr = (float)Math.pow(distX, 2);
		float ySqr = (float)Math.pow(distY, 2);
		float hypo = (float)Math.sqrt(xSqr + ySqr);
		float yNorm = distY/hypo;
		veloX = distX / (percetageSpeed * 0.1f);
		veloY = distY / (percetageSpeed * 0.1f);
		double rad = Math.asin(yNorm);
		heading = (float) (rad * 180 / Math.PI);
	}
	
	public boolean checkCollision(OSO object) {
		return checkCollision(object.getPosX(), object.getPosY(), object.collisionRadius);
	}
	
	public boolean checkCollision(int objectX, int objectY, int objectRadius) {
		float distX = objectX - pixPosX;
		float distY = objectY - pixPosY;
		float xSqr = (float)Math.pow(distX, 2);
		float ySqr = (float)Math.pow(distY, 2);
		float hypo = (float)Math.sqrt(xSqr + ySqr);
		
		if((int)hypo < (objectRadius + collisionRadius)) {
			return true;
		}
		return false;
	}
	
	public boolean checkOutOfBounds() {
		if(	pixPosX <= 0 ||
			pixPosY <= 0 ||
			pixPosX >= screenWidth ||
			pixPosY >= screenHeight) {
			return true;
		}
		return false;
	}
	
	public void userInput(int targetX, int targetY) {
		pulse = updateHeading(targetX, targetY);
		int max  = (screenWidth / 3) * 2;
		percetageSpeed = (int) (pulse > max ? 100 : (100.0f/max)*pulse);
	}
	
	private int updateHeading(int targetX, int targetY) {
		if(targetX == -1) {
			return 0;
		}
		float distX = targetX - pixPosOldX;
		float distY = targetY - pixPosOldY;
		float xSqr = (float)Math.pow(distX, 2);
		float ySqr = (float)Math.pow(distY, 2);
		float hypo = (float)Math.sqrt(xSqr + ySqr);
		float xNorm = distX/hypo;
		float yNorm = distY/hypo;
		veloX = xNorm;
		veloY = yNorm;
		double rad = Math.asin(yNorm);
		heading = (float) (rad * 180 / Math.PI);
		
		return (int)hypo;
	}
	
	public void reset() {
		heading = 0;
		veloX = 0;
		veloY = 0;
		pulse = 0;
		percetageSpeed = 0;
		super.reset();
	}
}