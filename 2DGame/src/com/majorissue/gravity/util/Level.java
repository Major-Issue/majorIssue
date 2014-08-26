package com.majorissue.gravity.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.majorissue.gravity.objects.Planet;
import com.majorissue.gravity.objects.Portal;
import com.majorissue.gravity.objects.Station;
import com.majorissue.gravity.objects.Ship;

import android.content.res.AssetManager;

public class Level {

	private static final String LEVEL_PATH = "gravity/levels/";
	private static final String META_DATA = "gravity/levels/meta.level";
	private static final String LEVEL_POSTFIX = ".level";

	public static final int LEVEL_STORY = 0;
	public static final int LEVEL_EXTRA = 1;

	public static String[] storyLevels = null;
	public static String[] extraLevels = null;

	public static Ship ship = null;
	public static Portal portal = null;
	public static ArrayList<Planet> planets = null;
	public static ArrayList<Station> stations = null;

	// level data
	public static String levelBackground;
	public static String levelBGM;

	public static void loadLevel(int level, int type, AssetManager am) {
		if (storyLevels == null || extraLevels == null)
			return;
		
		String levelName = type == LEVEL_STORY ? storyLevels[level] : extraLevels[level];
		levelName += LEVEL_POSTFIX;
		loadLevel(levelName, type, am);
	}
	
	public static void loadLevel(String levelName, int type, AssetManager am) {
		
		if(planets == null)
			planets = new ArrayList<Planet>();
		else
			planets.clear();
		
		if(stations == null)
			stations = new ArrayList<Station>();
		else
			stations.clear();
		
		if(type == LEVEL_STORY)
			Settings.currentLevel = levelName;
		InputStream in = null;
		BufferedReader reader = null;
		try {
			in = am.open(LEVEL_PATH + levelName);
			reader = new BufferedReader(new InputStreamReader(in));
			while (true) {
				final String line = reader.readLine();
				if (line == null)
					break;
				makeObject(line);
			}
		} catch (Exception e) {
			throw new RuntimeException("Couldn't load level");
		} finally {
			try {
				if (in != null)
					in.close();
				if (reader != null)
					reader.close();
			} catch (Exception e) {
			}
		}
	}

	public static void loadMetaData(AssetManager am) {
		InputStream in = null;
		BufferedReader reader = null;
		String line = "";
		try {
			in = am.open(META_DATA);
			reader = new BufferedReader(new InputStreamReader(in));
			try {
				line = reader.readLine(); // available story levels
				StringTokenizer st = new StringTokenizer(line);
				storyLevels = new String[st.countTokens()];
				for (int i = 0; i < storyLevels.length; i++) {
					storyLevels[i] = st.nextToken();
				}

				line = reader.readLine(); // available extra levels
				st = new StringTokenizer(line);
				extraLevels = new String[st.countTokens()];
				for (int i = 0; i < extraLevels.length; i++) {
					extraLevels[i] = st.nextToken();
				}

			} catch (Exception e) {
				throw new RuntimeException("Error loading metadata");
			}
		} catch (Exception e) {
			throw new RuntimeException("Error loading metadata");
		} finally {
			try {
				if (in != null)
					in.close();
				if (reader != null)
					reader.close();
			} catch (Exception e) {
			}
		}
	}

	private static void makeObject(String line) {
		if (line.startsWith("##"))
			return;
		if (line.startsWith("#ship"))
			createShip(line);
		if (line.startsWith("#portal"))
			createPortal(line);
		if (line.startsWith("#planet"))
			createPlanet(line);
		if (line.startsWith("#station"))
			createStation(line);
		
	}

	private static void createShip(String line) {
		if(ship == null)
			ship = new Ship();
		ship.posX = 0;
		ship.posY = 0;
	}

	private static void createPortal(String line) {
		if(portal == null)
			portal = new Portal();
		StringTokenizer st = new StringTokenizer(line);
		st.nextToken();
		portal.posX = Integer.parseInt(st.nextToken());
		portal.posY = Integer.parseInt(st.nextToken());
		portal.gravity = Integer.parseInt(st.nextToken());
		portal.asset = Integer.parseInt(st.nextToken());
	}

	private static void createPlanet(String line) {
		Planet planet = new Planet();
		StringTokenizer st = new StringTokenizer(line);
		st.nextToken();
		planet.posX = Integer.parseInt(st.nextToken());
		planet.posY = Integer.parseInt(st.nextToken());
		planet.gravity = Integer.parseInt(st.nextToken());
		planet.asset = Integer.parseInt(st.nextToken());
		planets.add(planet);
	}
	
	private static void createStation(String line) {
		Station station = new Station();
		StringTokenizer st = new StringTokenizer(line);
		st.nextToken();
		station.posX = Integer.parseInt(st.nextToken());
		station.posY = Integer.parseInt(st.nextToken());
		station.gravity = Integer.parseInt(st.nextToken());
		station.asset = Integer.parseInt(st.nextToken());
		stations.add(station);
	}

}
