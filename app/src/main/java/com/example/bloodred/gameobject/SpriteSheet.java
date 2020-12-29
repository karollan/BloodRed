package com.example.bloodred.gameobject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

import com.example.bloodred.Data;

public class SpriteSheet extends GameObject {

    public Collider collider;
    private Data.ColliderPosition colliderPositionRelativeToSprite;
    private Bitmap bitmap;		// the animation sequence
    private Rect sourceRect;	// the rectangle to be drawn from the animation bitmap
    private int frameNr;		// number of frames in animation
    private int currentFrame;	// the current frame
    private long frameTicker;	// the time of the last frame update
    private int framePeriod;	// milliseconds between each frame (1000/fps)
    private int delay;
    private final int animationTime;

    private int spriteWidth;	// the width of the sprite to calculate the cut out rectangle
    private int spriteHeight;	// the height of the sprite

    private final int numberOfPauses;
    private int stopFrameNumber;
    private final int framesPerAnimation;
    private int stopCounter;

    private boolean animation;

    //Sound in animation
    private Runnable r;
    private int soundAtFrameNr;
    private boolean soundInAnimation;


    //SpriteSheet Contructor
    public SpriteSheet(Bitmap bitmap, int x, int y, int fps, int frameCount, float scaleFactor, int numberOfPauses, boolean alwaysOn) {
        super(x, y);
        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * scaleFactor), (int) (bitmap.getHeight() * scaleFactor), true);
        this.numberOfPauses = numberOfPauses;
        this.stopCounter = numberOfPauses;
        this.animation = alwaysOn;
        currentFrame = 0;
        frameNr = frameCount;
        framesPerAnimation = frameNr/this.numberOfPauses;
        stopFrameNumber = frameNr/this.stopCounter-1;
        spriteWidth = this.bitmap.getWidth() / frameCount;
        spriteHeight = this.bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        framePeriod = 1000 / fps;
        frameTicker = 0l;
        delay = 0;
        animationTime = framePeriod * framesPerAnimation;
    }

    //Spritesheet Constructor with CircleCollider
    public SpriteSheet(Bitmap bitmap, int x, int y, int fps, int frameCount, float scaleFactor, int numberOfPauses, boolean alwaysOn, float radius, Data.ColliderPosition pos, boolean drawable) {
        this(bitmap, x, y, fps, frameCount, scaleFactor, numberOfPauses, alwaysOn);

        this.colliderPositionRelativeToSprite = pos;
        PointF position = Collider.colliderPos(this.colliderPositionRelativeToSprite, spriteWidth, spriteHeight);
        collider = new CircleCollider(positionX, positionY, radius, position.x, position.y, drawable);
    }

    public static boolean isClicked(SpriteSheet spriteSheet, double x, double y) {
        return x >= (spriteSheet.getPositionX() - (float)spriteSheet.getSpriteWidth()/2) && x < (spriteSheet.getPositionX() + (float)spriteSheet.getSpriteWidth()/2) && y >= (spriteSheet.getPositionY() - (float)spriteSheet.getSpriteHeight()/2) && y < (spriteSheet.getPositionY() + (float)spriteSheet.getSpriteHeight()/2);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public Rect getSourceRect() {
        return sourceRect;
    }
    public void setSourceRect(Rect sourceRect) {
        this.sourceRect = sourceRect;
    }
    public int getFrameNr() {
        return frameNr;
    }
    public void setFrameNr(int frameNr) {
        this.frameNr = frameNr;
    }
    public int getCurrentFrame() {
        return currentFrame;
    }
    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }
    public int getFramePeriod() {
        return framePeriod;
    }
    public void setFramePeriod(int framePeriod) {
        this.framePeriod = framePeriod;
    }
    public int getSpriteWidth() {
        return spriteWidth;
    }
    public void setSpriteWidth(int spriteWidth) {
        this.spriteWidth = spriteWidth;
    }
    public int getSpriteHeight() {
        return spriteHeight;
    }
    public void setSpriteHeight(int spriteHeight) {
        this.spriteHeight = spriteHeight;
    }

    public void update(){};

    public void startAnimation() {
        animation = true;
        Log.d("animuje", "animacje");
    }

    public boolean inAnimation() {
        return animation;
    }

    public boolean inLastFrame() {
        if (stopCounter < 0) return true;
        else return false;
    }

    public int getAnimationTime() {
        return animationTime;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void playSoundInAnimation(Runnable r, int soundAtFrameNr) {
        this.r = r;
        this.soundAtFrameNr = soundAtFrameNr;
        this.soundInAnimation = true;
    }

    // the update method for SpriteSheet
    public void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod + delay) {
            frameTicker = gameTime;
            // increment the frame
            if (animation) {
                delay = 0;
                currentFrame++;

                //Sound in animation at certain frame
                if (soundInAnimation) {
                    if (currentFrame == soundAtFrameNr-1) {
                        r.run();
                    }
                }

                if (currentFrame >= stopFrameNumber) {
                    stopFrameNumber = currentFrame + framesPerAnimation - 1;
                    animation = false;
                    stopCounter--;
                }
            }
            //When last pause is reached just finish all frames
            if (stopCounter == 0 && currentFrame <= frameNr && numberOfPauses > 1) {
                currentFrame++;
                if (currentFrame == frameNr) {
                    stopCounter--;
                }
            }
        }
        // define the rectangle to cut out sprite
        this.sourceRect.left = currentFrame * spriteWidth;
        this.sourceRect.right = this.sourceRect.left + spriteWidth;

        if (collider != null) {
            collider.setPosition(positionX, positionY);
        }
    }

    // the draw method which draws the corresponding frame
    public void draw(Canvas canvas) {
        // where to draw the sprite
        if (collider != null && collider.setToDraw()) {
            //collider.draw(canvas);
        }

        Rect destRect = new Rect((int)getPositionX()-spriteWidth/2, (int)getPositionY()-spriteHeight/2, (int)getPositionX() + spriteWidth/2, (int)getPositionY() + spriteHeight/2);
        canvas.drawBitmap(bitmap, sourceRect, destRect, null);
    }

}
