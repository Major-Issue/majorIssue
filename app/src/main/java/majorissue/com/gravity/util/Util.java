package majorissue.com.gravity.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;

public class Util {

	public static Matrix matrix = new Matrix();
	
	public static Bitmap RotateBitmap(Bitmap source, float degrees) {
	      matrix.reset();
	      matrix.postRotate(degrees);
	      return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
	}

	public static Bitmap ScaleBitamp(Bitmap source, float rx, float ry) {
		Matrix m = new Matrix();
		m.setRectToRect(new RectF(0, 0, source.getWidth(), source.getHeight()), new RectF(0, 0, source.getWidth()*rx, source.getHeight()*ry), Matrix.ScaleToFit.CENTER);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), m, true);

	}
}