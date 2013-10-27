package com.majorissue.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public interface AnimatedSprite {

    public void initialize(Bitmap bitmap, int width, int height, int fps, int frameCount);
    
    public void update(long gameTime);
    
    public void draw(Canvas canvas);
    
    public void setmXPos(int mXPos);
    
    public void setmYPos(int mYPos);
    
    public int getmXPos();
    
    public int getmYPos();
}
