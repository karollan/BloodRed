package com.example.bloodred.object;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Sprite is an object which implements method from GameObject for drawing the object as a image
 *
 **/

public abstract class Sprite extends GameObject {
    private Bitmap bmp;

    private float width;
    private float height;

    public Sprite(Context context,int drawing, double positionX, double positionY, float scaleFactor) {
        super(positionX, positionY);

        //Decode and scale the bitmap
        bmp = BitmapFactory.decodeResource(context.getResources(), drawing);
        bmp = bmp.createScaledBitmap(bmp, (int)(bmp.getWidth()*scaleFactor), (int)(bmp.getHeight()*scaleFactor), true);

        width = bmp.getWidth();
        height = bmp.getHeight();
    }

    /**
     * IsColliding checks if two Sprite objects are colliding, based on their positions
     * and circumference of a rectangle
     **/

    public static boolean isColliding(Sprite obj1, Sprite obj2) {
        double distance = getDistanceBetweenObjects(obj1, obj2);
        double distanceToCollisionX = obj1.getWidth()/4 + obj2.getWidth()/4;
        double distanceToCollisionY = obj1.getHeight()/4 + obj2.getHeight()/4;

        if (distance - distanceToCollisionX <= 0 || distance - distanceToCollisionY <= 0) return true;
        else return false;

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bmp, (float)positionX - width/2, (float)positionY - height/2, null);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
