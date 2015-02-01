package com.majorissue.gravity.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class Util {

	public static Matrix matrix = new Matrix();
	
	public static Bitmap RotateBitmap(Bitmap source, float degrees) {
	      matrix.reset();
	      matrix.postRotate(degrees);
	      return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
	}
}