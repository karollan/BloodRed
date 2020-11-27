package com.example.bloodred.gameobject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;

import com.example.bloodred.Data;


/**
 * Sprite is an object which implements method from GameObject for drawing the object as a image
 *
 **/

public abstract class Sprite extends GameObject {
    private Bitmap bmp;

    private final float width;
    private final float height;
    public Collider collider;
    private Data.ColliderPosition colliderPositionRelativeToSprite;

    //Contructor for Sprites without collider
    public Sprite(Context context, int drawing, double positionX, double positionY, float scaleFactor) {
        super(positionX, positionY);

        //Decode and scale the bitmap
        bmp = BitmapFactory.decodeResource(context.getResources(), drawing);
        bmp = bmp.createScaledBitmap(bmp, (int) (bmp.getWidth() * scaleFactor), (int) (bmp.getHeight() * scaleFactor), true);

        width = bmp.getWidth();
        height = bmp.getHeight();
    }

    /**
     * Colliders position is relative to Sprite position, all possible positions are listed in Data class -> ColliderPosition enum
     * It is easier to set collider on fixed positions relative to sprite size than to choose each position manually
     **/

    //Contructor for Sprites with CircleCollider
    public Sprite(Context context, int drawing, double positionX, double positionY, float scaleFactor, float radius, Data.ColliderPosition pos) {
        //Call the basic constructor
        this(context, drawing, positionX, positionY, scaleFactor);

        //Set the collider
        this.colliderPositionRelativeToSprite = pos;
        PointF position = colliderPos(this.colliderPositionRelativeToSprite);

        collider = new CircleCollider(positionX, positionY, radius, position.x, position.y);
    }

    //Constructor for Sprites with RectangleCollider
    public Sprite (Context context, int drawing, double positionX, double positionY, float scaleFactor, double width, double height,  Data.ColliderPosition pos) {
        //Call the basic constructor
        this(context, drawing, positionX, positionY, scaleFactor);

        //Set the collider
        this.colliderPositionRelativeToSprite = pos;
        PointF position = colliderPos(this.colliderPositionRelativeToSprite);
        collider = new RectangleCollider(positionX, positionY, position.x, position.y, width, height);
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


    //Check sprites width and height with consideration of scaling
    public static int checkScaledDrawableWidth(Context context, int drawable, float scaleFactor) {
        Bitmap bmp;
        bmp = BitmapFactory.decodeResource(context.getResources(), drawable);
        return (int)(bmp.getWidth()*scaleFactor);
    }

    public static int checkScaledDrawableHeight(Context context, int drawable, float scaleFactor) {
        Bitmap bmp;
        bmp = BitmapFactory.decodeResource(context.getResources(), drawable);
        return (int)(bmp.getHeight()*scaleFactor);
    }


    //ColliderPos function checks what position is needed through checking enum and returns vector with x and y
    private PointF colliderPos(Data.ColliderPosition pos) {
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
            case CENTER_LEFT:
                position.set(-width/4, 0f);
                return position;
            case CENTER_TOP:
                position.set(0f, -height/4);
                return position;
            case CENTER_BOTTOM:
                position.set(0f, height/4);
                return position;
            case CENTER_TOP_LEFT:
                position.set(-width/4, -height/4);
                return position;
            case CENTER_TOP_RIGHT:
                position.set(width/4, -height/4);
                return position;
            case CENTER_BOTTOM_LEFT:
                position.set(-width/4, height/4);
                return position;
            case CENTER_BOTTOM_RIGHT:
                position.set(width/4, height/4);
                return position;
            default:
                position.set(0f, 0f);
                return position;

        }
    }
}
