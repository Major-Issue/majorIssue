package majorissue.com.framework.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import majorissue.com.framework.AnimatedSprite;

public class AndroidAnimatedSprite implements AnimatedSprite {

    private Bitmap mAnimation;
    private int mXPos;
    private int mYPos;
    private Rect mSRectangle;
    private int mFPS;
    private int mFramesCount;
    private int mCurrentFrame;
    private long mFrameTimer;
    private int mSpriteHeight;
    private int mSpriteWidth;
    private boolean mIsLooping;
    private boolean mFirstCircleCompleted;

    public AndroidAnimatedSprite() {
        mSRectangle = new Rect(0, 0, 0, 0);
        mFrameTimer = 0;
        mCurrentFrame = 0;
        mXPos = 0;
        mYPos = 0;
        mFirstCircleCompleted = false;
    }

    @Override
    public void initialize(Bitmap bitmap, int width, int height, int fps, int frameCount, boolean isLooping) {
        mAnimation = bitmap;
        mSpriteWidth = width;
        mSpriteHeight = height;
        mSRectangle.top = 0;
        mSRectangle.bottom = mSpriteHeight;
        mSRectangle.left = 0;
        mSRectangle.right = mSpriteWidth;
        mFPS = 1000 / fps;
        mFramesCount = frameCount;
        mIsLooping = isLooping;
    }

    @Override
    public void update(long gameTime) {
        if (gameTime > mFrameTimer + mFPS) {
            mFrameTimer = gameTime;
            mCurrentFrame += 1;
            if(mCurrentFrame == mFramesCount) {
            	mCurrentFrame = 0;
            	mFirstCircleCompleted = true;
            }
        }
        mSRectangle.left = mCurrentFrame * mSpriteWidth;
        mSRectangle.right = mSRectangle.left + mSpriteWidth;
    }

    @Override
    public void draw(Canvas canvas) {
    	if(!isExpired()){
    		Rect rect = new Rect(getXPos(), getYPos(), getXPos() + mSpriteWidth, getYPos() + mSpriteHeight);
    		canvas.drawBitmap(mAnimation, mSRectangle, rect, null);
    	}
    }

    @Override
    public void setXPos(int xPos) {
        this.mXPos = xPos;
    }

    @Override
    public void setYPos(int yPos) {
        this.mYPos = yPos;
    }

    @Override
    public int getXPos() {
        return mXPos;
    }

    @Override
    public int getYPos() {
        return mYPos;
    }

	@Override
	public boolean isExpired() {
		if(!mIsLooping && mFirstCircleCompleted)
			return true;
		else
			return false;
	}
}