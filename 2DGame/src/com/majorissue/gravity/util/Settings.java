package com.majorissue.gravity.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.majorissue.framework.FileIO;
import com.majorissue.gravity.screens.GameScreen;
import com.majorissue.gravity.screens.IntroScreen;

public class Settings {

	public static final int DEFAULT_LVL = 1;
	
	// settings w/ defaults
	public static boolean soundEnabled = true;
	public static boolean musicEnabled = true;
	public static int introState = IntroScreen.INTRO_FIRST;
	public static int resolution = GameScreen.RESOLUTION_NATIVE;
	public static boolean continueGame = false;
	public static int currentLevel = DEFAULT_LVL;
		
	public static void load(FileIO files) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(files.readFile("gravity.settings")));
			soundEnabled = Boolean.parseBoolean(in.readLine());
			musicEnabled = Boolean.parseBoolean(in.readLine());
			introState = Integer.parseInt(in.readLine());
			resolution = Integer.parseInt(in.readLine());
			continueGame = Boolean.parseBoolean(in.readLine());
			currentLevel = Integer.parseInt(in.readLine());
		} catch (Exception e) {
			// defaults
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}
	
	public static void save(FileIO files) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(files.writeFile("gravity.settings")));
			out.write(Boolean.toString(soundEnabled));
			out.write("\n");
			out.write(Boolean.toString(musicEnabled));
			out.write("\n");
			out.write(Integer.toString(introState));
			out.write("\n");
			out.write(Integer.toString(resolution));
			out.write("\n");
			out.write(Boolean.toString(continueGame));
			out.write("\n");
			out.write(Integer.toString(currentLevel));
			out.write("\n");
		} catch (IOException e) {
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}
	}
	
	public static void toggleSound(){
		soundEnabled = !soundEnabled;
	}
	
	public static void toggleMusic(){
		musicEnabled = !musicEnabled;
	}
	
	public static void toggleIntro(){
		if(introState == IntroScreen.INTRO_DONT_SHOW)
			introState = IntroScreen.INTRO_SHOW;
		else
			introState = IntroScreen.INTRO_DONT_SHOW;
	}
}