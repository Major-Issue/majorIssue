package majorissue.com.framework.impl;

import android.graphics.Bitmap;

import majorissue.com.framework.Graphics.PixmapFormat;
import majorissue.com.framework.Pixmap;

public class AndroidPixmap implements Pixmap {
    Bitmap bitmap;
    PixmapFormat format;
    
    public AndroidPixmap(Bitmap bitmap, PixmapFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    public int getWidth() {
        return bitmap.getWidth();
    }

    public int getHeight() {
        return bitmap.getHeight();
    }

    public PixmapFormat getFormat() {
        return format;
    }

    public void dispose() {
        bitmap.recycle();
    }
    
    public Bitmap getBitmap() {
    	return bitmap;
    }
    
    public void setBitmap(Bitmap bmp) {
    	this.bitmap = bmp;
    }
}