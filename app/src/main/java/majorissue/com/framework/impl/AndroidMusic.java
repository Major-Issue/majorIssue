package majorissue.com.framework.impl;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

import majorissue.com.framework.Music;

public class AndroidMusic implements Music, OnCompletionListener {
	MediaPlayer mediaPlayer;
	boolean isPrepared = false;

	public AndroidMusic(AssetFileDescriptor assetDescriptor) {
		mediaPlayer = new MediaPlayer();
		try {
			mediaPlayer.setDataSource(	assetDescriptor.getFileDescriptor(), 
										assetDescriptor.getStartOffset(), 
										assetDescriptor.getLength());
			mediaPlayer.prepare();
			isPrepared = true;
			mediaPlayer.setOnCompletionListener(this);
		} catch (Exception e) {
			Log.e("GRAVITY", "!!! MEDIAPLAYER LOAD !!!");
			e.printStackTrace();
		}
	}

	public void onCompletion(MediaPlayer player) {
		// nothing for now
	}

// ================================================================== //
	
	public void play() {
		if (mediaPlayer.isPlaying())
			return;
		try {
			synchronized (this) {
				if (!isPrepared) {
					mediaPlayer.prepare();
					isPrepared = true;
				}
				mediaPlayer.start();
			}
		} catch (IllegalStateException e) {
			Log.e("GRAVITY", "!!! MEDIAPLAYER PLAY !!!");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("GRAVITY", "!!! MEDIAPLAYER PLAY !!!");
			e.printStackTrace();
		}
	}

	public void pause() {
		try {
			synchronized (this) {
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.pause();
				}
			}
		} catch (IllegalStateException e) {
			Log.e("GRAVITY", "!!! MEDIAPLAYER PAUSE !!!");
			e.printStackTrace();
		}
	}

	public void stop() {
		try {
			synchronized (this) {
				if(!isPrepared) {
					return;
				}
				mediaPlayer.stop();
				isPrepared = false;
			}
		} catch (Exception e) {
			Log.e("GRAVITY", "!!! MEDIAPLAYER STOP !!!");
			e.printStackTrace();
		}
	}

	public void dispose() {
		try {
			synchronized (this) {
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.stop();
				}
				mediaPlayer.release();
			}
		} catch (Exception e) {
			Log.e("GRAVITY", "!!! MEDIAPLAYER DISPOSE !!!");
			e.printStackTrace();
		}
	}

// ================================================================== //
	
	public void setLooping(boolean isLooping) {
		mediaPlayer.setLooping(isLooping);
	}

	public void setVolume(float volume) {
		mediaPlayer.setVolume(volume, volume);
	}
}