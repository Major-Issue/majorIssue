package com.majorissue.framework;

import android.graphics.Bitmap;

import com.majorissue.framework.Graphics.PixmapFormat;

public interface Pixmap {

	public int getWidth();

	public int getHeight();

	public PixmapFormat getFormat();

	public void dispose();

	public void setBitmap(Bitmap bmp);
	
	public Bitmap getBitmap();
}
