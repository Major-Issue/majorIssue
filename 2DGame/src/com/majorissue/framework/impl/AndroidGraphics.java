package com.majorissue.framework.impl;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.majorissue.framework.Graphics;
import com.majorissue.framework.Pixmap;

public class AndroidGraphics implements Graphics {
    
    public static final int TOP_LEFT = 0;
    public static final int TOP_RIGHT = 1;
    public static final int BOTTOM_LEFT = 2;
    public static final int BOTTOM_RIGHT = 3;
    public static final int CENTER = 4;
	
	AssetManager assets;
    Bitmap frameBuffer;
    Canvas canvas;
    Paint paint;
    Rect srcRect = new Rect();
    Rect dstRect = new Rect();

    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
    }

    public Pixmap newPixmap(String fileName, PixmapFormat format) {
        Config config = null;
        if (format == PixmapFormat.RGB565)
            config = Config.RGB_565;
        else if (format == PixmapFormat.ARGB4444)
            config = Config.ARGB_4444;
        else
            config = Config.ARGB_8888;

        Options options = new Options();
        options.inPreferredConfig = config;

        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        if (bitmap.getConfig() == Config.RGB_565)
            format = PixmapFormat.RGB565;
        else if (bitmap.getConfig() == Config.ARGB_4444)
            format = PixmapFormat.ARGB4444;
        else
            format = PixmapFormat.ARGB8888;

        return new AndroidPixmap(bitmap, format);
    }

    public void clear(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8, (color & 0xff));
    }

    public void drawPixel(int x, int y, int color) {
        paint.setColor(color);
        canvas.drawPoint(x, y, paint);
    }

    public void drawLine(int x, int y, int x2, int y2, int color) {
        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    public void drawRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + width - 1, paint);
    }

    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight) {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth - 1;
        srcRect.bottom = srcY + srcHeight - 1;

        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth - 1;
        dstRect.bottom = y + srcHeight - 1;

        canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, srcRect, dstRect, null);
    }

    public void drawPixmap(Pixmap pixmap, int x, int y) {
        canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, x, y, null);
    }
    
    public void drawBitmap(Bitmap bitmap, int x, int y) {
        canvas.drawBitmap(bitmap, x, y, null);
    }

    public int getWidth() {
        return frameBuffer.getWidth();
    }

    public int getHeight() {
        return frameBuffer.getHeight();
    }

	public void drawText(int position, int size, String text, Typeface tf) {
		Paint paint = new Paint(); 
		paint.setColor(Color.WHITE); 
		paint.setTextSize(getPixels(size));
		paint.setAntiAlias(true);
		if(tf != null)
			paint.setTypeface(tf);
		Point p = getDrawPointForText(paint, text, position);
		canvas.drawText(text, p.x, p.y, paint);
	}
    
	public void drawText(int x, int y, int size, String text, Typeface tf) {
		Paint paint = new Paint(); 
		paint.setColor(Color.WHITE); 
		paint.setTextSize(getPixels(size));
		paint.setAntiAlias(true);
		if(tf != null)
			paint.setTypeface(tf);
		canvas.drawText(text, x, y, paint); 
	}
	
	public int getPixels(int size) {
	    DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
	    return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, metrics);
	}
	
	public Point getDrawPointForText(Paint paint, String text, int position) {
	    Rect bounds = new Rect();
	    paint.getTextBounds(text, 0, text.length(), bounds);
	    int x = 0;
	    int y = 0;
	    switch (position) {
		case CENTER:
			x = (getWidth() / 2) - (bounds.width() / 2);
		    y = (getHeight() / 2) - (bounds.height() / 2);
			break;
		case TOP_LEFT:
			x = 0;
		    y = 0 + bounds.height();
			break;
		case TOP_RIGHT:
			x = getWidth() - bounds.width();
		    y = 0 + bounds.height();
			break;
		case BOTTOM_LEFT:
			x = 0;
		    y = getHeight() - 10;
			break;
		case BOTTOM_RIGHT:
			x = getWidth() - bounds.width();
		    y = getHeight() - 10;
			break;
		}
	    
	    return new Point(x, y);
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public void drawText(String text, int x, int y, Paint paint) {
		canvas.drawText(text, x, y, paint);
	}
}

