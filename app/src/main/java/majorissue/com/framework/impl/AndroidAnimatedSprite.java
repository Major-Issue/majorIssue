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
    private Rect mORectangle;
    private long mAnimationFrameDuration;
    private int mFramesCount;
    private int mLinesCount;
    private int mCurrentFrame;
    private int mCurrentLine;
    private int mSpriteHeight;
    private int mSpriteWidth;
    private boolean mIsLooping;
    private boolean mFirstCircleCompleted;
    private float deltaTimeSum = 0;
    private String mAnimationID = null;

    public AndroidAnimatedSprite(int posX,
                                 int posY,
                                 Bitmap animation,
                                 int spriteWidth,
                                 int spriteHeight,
                                 int fps,
                                 int framePerLine,
                                 int linesCount,
                                 boolean isLooping,
                                 String animationID) {
        // init
        mXPos = posX;
        mYPos = posY;
        mAnimation = animation;
        mSpriteWidth = spriteWidth;
        mSpriteHeight = spriteHeight;
        mAnimationFrameDuration = 1 / fps;
        mFramesCount = framePerLine;
        mLinesCount = linesCount < 1 ? 1 : linesCount;
        mIsLooping = isLooping;
        mSRectangle = new Rect(0, 0, 0, 0);
        mAnimationID = animationID;

        // first animation frame
        mSRectangle.top = 0;
        mSRectangle.bottom = mSpriteHeight;
        mSRectangle.left = 0;
        mSRectangle.right = mSpriteWidth;

        // default values
        mCurrentFrame = 0;
        mCurrentLine = 0;
        mFirstCircleCompleted = false;

        mORectangle = new Rect(mXPos - mSpriteWidth/2, mYPos - mSpriteWidth/2, mXPos + mSpriteWidth/2, mYPos + mSpriteHeight/2);
    }

    @Override
    public void update(float deltaTime) { //deltaTime between frames in seconds
        deltaTimeSum += deltaTime;
        if(deltaTimeSum < mAnimationFrameDuration) {
            return;
        }
        deltaTimeSum = 0;

        mSRectangle.left = mCurrentFrame * mSpriteWidth;
        mSRectangle.right = mSRectangle.left + mSpriteWidth;
        mSRectangle.top = mCurrentLine * mSpriteHeight;
        mSRectangle.bottom = mSRectangle.top + mSpriteHeight;

        // next spriteFrame
        mCurrentFrame += 1;
        if(mCurrentFrame == mFramesCount) {
            mCurrentFrame = 0;
            mCurrentLine += 1;
            if(mCurrentLine == mLinesCount) {
                mCurrentLine = 0;
                mFirstCircleCompleted = true;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
    	canvas.drawBitmap(mAnimation, mSRectangle, mORectangle, null);
    }

	@Override
	public boolean isExpired() {
		if(!mIsLooping && mFirstCircleCompleted) {
            return true;
        } else {
            return false;
        }
	}

    @Override
    public void updateOutRectangle(int posX, int posY) {
        mXPos = posX;
        mYPos = posY;
        mORectangle = new Rect(mXPos - mSpriteWidth/2, mYPos - mSpriteWidth/2, mXPos + mSpriteWidth/2, mYPos + mSpriteHeight/2);
    }

    @Override
    public String getAnimationID() {
        return mAnimationID;
    }
}