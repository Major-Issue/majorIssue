package com.majorissue.gravity.objects;

import com.majorissue.gravity.util.Assets;

public class Station extends OSO{

	public static int STATION_TYPE_A = 0;
	public static int STATION_TYPE_B = 1;
	public static int STATION_TYPE_C = 2;
	public static int STATION_TYPE_D = 3;
	
	public int heading = 0;
	
	public Station(){};
	
	public void init() {
		collisionRadius = Assets.station.getWidth() / 2;
	}
	
	public void update(float deltaTime) {
		// TODO:
	}
	
	public void reset() {
		// TODO:
	}
}
