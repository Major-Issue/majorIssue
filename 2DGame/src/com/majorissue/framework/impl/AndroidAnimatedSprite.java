
package com.majorissue.framework.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.majorissue.framework.AnimatedSprite;

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

    public AndroidAnimatedSprite() {
        mSRectangle = new Rect(0, 0, 0, 0);
        mFrameTimer = 0;
        mCurrentFrame = 0;
        mXPos = 0;
        mYPos = 0;
    }

    @Override
    public void initialize(Bitmap bitmap, int width, int height, int fps, int frameCount) {
        mAnimation = bitmap;
        mSpriteWidth = width;
        mSpriteHeight = height;
        mSRectangle.top = 0;
        mSRectangle.bottom = mSpriteHeight;
        mSRectangle.left = 0;
        mSRectangle.right = mSpriteWidth;
        mFPS = 1000 / fps;
        mFramesCount = frameCount;
    }

    @Override
    public void update(long gameTime) {
        if (gameTime > mFrameTimer + mFPS) {
            mFrameTimer = gameTime;
            mCurrentFrame += 1;
            if (mCurrentFrame == mFramesCount)
                mCurrentFrame = 0;
        }
        mSRectangle.left = mCurrentFrame * mSpriteWidth;
        mSRectangle.right = mSRectangle.left + mSpriteWidth;
    }

    @Override
    public void draw(Canvas canvas) {
        Rect rect = new Rect(getmXPos(), getmYPos(), getmXPos() + mSpriteWidth, getmYPos()
                + mSpriteHeight);
        canvas.drawBitmap(mAnimation, mSRectangle, rect, null);
    }

    @Override
    public void setmXPos(int mXPos) {
        this.mXPos = mXPos;
    }

    @Override
    public void setmYPos(int mYPos) {
        this.mYPos = mYPos;
    }

    @Override
    public int getmXPos() {
        return mXPos;
    }

    @Override
    public int getmYPos() {
        return mYPos;
    }

}
