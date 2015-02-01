package com.majorissue.framework;

public interface Music {

	public void play();
	
	public void pause();

	public void stop();

	public void dispose();
	
	public void setLooping(boolean looping);

	public void setVolume(float volume);
}