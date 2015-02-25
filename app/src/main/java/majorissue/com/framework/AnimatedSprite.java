package majorissue.com.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public interface AnimatedSprite {

    public void initialize(Bitmap bitmap, int width, int height, int fps, int frameCount, boolean isLooping);
    
    public void update(long gameTime);
    
    public void draw(Canvas canvas);
    
    public void setXPos(int xPos);
    
    public void setYPos(int yPos);
    
    public int getXPos();
    
    public int getYPos();
    
    public boolean isExpired();
}