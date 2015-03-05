package majorissue.com.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public interface AnimatedSprite {
    
    public void update(float deltaTime);
    
    public void draw(Canvas canvas);
    
    public boolean isExpired();

    public void updateOutRectangle(int posX, int posY);

    public String getAnimationID();
}