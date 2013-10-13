package com.majorissue.framework;

import com.majorissue.framework.Graphics.PixmapFormat;

public interface Pixmap {

	public int getWidth();

	public int getHeight();

	public PixmapFormat getFormat();

	public void dispose();

}
