package com.example.bloodred.gameobject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

import com.example.bloodred.Data;
/**
 * SpriteSheet is an object which implements method from GameObject for drawing the object as a image
 *
 **/
public class SpriteSheet extends GameObject {

    //SpriteSheet can be created with a collider
    public Collider collider;
    private Data.ColliderPosition colliderPositionRelativeToSprite;

    private Bitmap bitmap;		// the animation sequence
    private Rect sourceRect;	// the rectangle to be drawn from the animation bitmap
    private int frameNr;		// number of frames in animation
    private int currentFrame;	// the current frame
    private long frameTicker;	// the time of the last frame update
    private int framePeriod;	// milliseconds between each frame (1000/fps)
    private int delay;          // if animation starts with delay
    private final int animationTime; // time to complete animation from start to finish

    private int spriteWidth;	// the width of the sprite to calculate the cut out rectangle
    private int spriteHeight;	// the height of the sprite

    private final int numberOfPauses; //number of animation stages
    private int stopFrameNumber; // frame at which animation pauses
    private final int framesPerAnimation; // number of frames played for each stage
    private int stopCounter; //Number of remaining pauses

    private boolean animation;  // state that decides if animation can start

    //Sound in animation
    private Runnable r;  // contains method which plays sound
    private int soundAtFrameNr; // number of frame at which sound is played
    private boolean soundInAnimation; //state which decides if animation is played with sound


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


    public int getFrameNr() {
        return frameNr;
    }
    public int getCurrentFrame() {
        return currentFrame;
    }
    public int getSpriteWidth() {
        return spriteWidth;
    }
    public int getSpriteHeight() {
        return spriteHeight;
    }

    public void update(){};

    public void startAnimation() {
        animation = true;
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
            collider.draw(canvas);
        }

        Rect destRect = new Rect((int)getPositionX()-spriteWidth/2, (int)getPositionY()-spriteHeight/2, (int)getPositionX() + spriteWidth/2, (int)getPositionY() + spriteHeight/2);
        canvas.drawBitmap(bitmap, sourceRect, destRect, null);
    }

}
