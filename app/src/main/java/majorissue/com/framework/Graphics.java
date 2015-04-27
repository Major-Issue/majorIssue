package majorissue.com.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

public interface Graphics {

	public static enum PixmapFormat {
		ARGB8888, ARGB4444, RGB565
	}

	public Pixmap newPixmap(String fileName, PixmapFormat format, float rx, float ry);

	public void clear(int color);

	public void drawPixel(int x, int y, int color);

	public void drawLine(int x, int y, int x2, int y2, int color);

	public void drawRect(int x, int y, int width, int height, int color);

	public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight);

	public void drawPixmap(Pixmap pixmap, int x, int y);
	
	public void drawBitmap(Bitmap bitmap, int x, int y);
	
	public void drawText(int position, int size, String text, Typeface tf);
	
	public void drawText(int x, int y, int size, String text, Typeface tf);
	
	public void drawText(String text, int x, int y, Paint paint);

	public int getWidth();

	public int getHeight();
	
	public int getPixels(int size);
	
	public Canvas getCanvas();
}