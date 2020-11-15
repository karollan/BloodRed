package com.example.bloodred.object;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.Log;

import com.example.bloodred.CircleCollider;
import com.example.bloodred.CircleColliderPosition;

import java.util.Vector;

/**
 * Sprite is an object which implements method from GameObject for drawing the object as a image
 *
 **/

public abstract class Sprite extends GameObject {
    private Bitmap bmp;

    private float width;
    private float height;
    public CircleCollider collider;
    private CircleColliderPosition colliderPositionRelativeToSprite;

    //Contructor for Sprites without collider
    public Sprite(Context context, int drawing, double positionX, double positionY, float scaleFactor) {
        super(positionX, positionY);

        //Decode and scale the bitmap
        bmp = BitmapFactory.decodeResource(context.getResources(), drawing);
        bmp = bmp.createScaledBitmap(bmp, (int) (bmp.getWidth() * scaleFactor), (int) (bmp.getHeight() * scaleFactor), true);

        width = bmp.getWidth();
        height = bmp.getHeight();
    }

    //Contructor for Sprites with CircleCollider
    public Sprite(Context context, int drawing, double positionX, double positionY, float scaleFactor, float radius, CircleColliderPosition pos) {
        //Call the original contructor
        this(context, drawing, positionX, positionY, scaleFactor);

        //Set the collider
        /**
         * Circle collider position is relative to Sprite position, all possible positions are listed in CircleColliderPosition enum
         * It is easier to set collider on fixed positions relative to sprite size than to choose each position manually
         **/
        this.colliderPositionRelativeToSprite = pos;
        PointF position = colliderPos(this.colliderPositionRelativeToSprite);

        collider = new CircleCollider(positionX, positionY, radius, position.x, position.y);
    }

    /**
     * IsColliding checks if two Sprite objects are colliding, based on their positions
     * and circumference of a rectangle *IF NEEDED CREATE RECTANGLECOLLIDER CLASS FOR THIS FUNCTION*
     **/

    public static boolean isColliding(Sprite obj1, Sprite obj2) {
        double distance = getDistanceBetweenObjects(obj1, obj2);
        double distanceToCollisionX = obj1.getWidth() / 4 + obj2.getWidth() / 4;
        double distanceToCollisionY = obj1.getHeight() / 4 + obj2.getHeight() / 4;

        if (distance - distanceToCollisionX <= 0 || distance - distanceToCollisionY <= 0)
            return true;
        else return false;

    }

    public void draw(Canvas canvas) {
        if (collider != null) {
            collider.setPosition(positionX, positionY);
            collider.draw(canvas);
        }
        canvas.drawBitmap(bmp, (float) positionX - width / 2, (float) positionY - height / 2, null);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    //ColliderPos function checks what position is needed through checking enum and returns vector with x and y
    private PointF colliderPos(CircleColliderPosition pos) {
        PointF position = new PointF();
        switch (pos) {
            case TOP:
                position.set(0f, -height/2);
                return position;
            case BOTTOM:
                position.set(0f, height/2);
                return position;
            case LEFT:
                position.set(-width/2, 0f);
                return position;
            case RIGHT:
                position.set(width/2, 0f);
                return position;
            case CENTER:
                return position;
            case TOP_RIGHT:
                position.set(width/2, -height/2);
                return position;
            case TOP_LEFT:
                position.set(-width/2, -height/2);
                return position;
            case BOTTOM_LEFT:
                position.set(-width/2, height/2);
                return position;
            case BOTTOM_RIGHT:
                position.set(width/2, height/2);
                return position;
            case CENTER_RIGHT:
                position.set(width/4, 0f);
                return position;
            case CENTER_LEFT:_RIGHT:
                position.set(-width/4, 0f);
                return position;
            default:
                position.set(0f, 0f);
                return position;

        }
    }
}
