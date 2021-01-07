package com.example.bloodred.gameobject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;

import androidx.appcompat.view.menu.MenuBuilder;

import com.example.bloodred.Data;

import java.util.ArrayList;


/**
 * Sprite is an object which implements method from GameObject for drawing the object as a image
 *
 **/

public abstract class Sprite extends GameObject {

    //Sprite bitmap
    private Bitmap bmp;

    //Sprite measurements
    private float width;
    private float height;

    //If sprite is created with collider
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
    public Sprite(Context context, int drawing, double positionX, double positionY, float scaleFactor, float radius, Data.ColliderPosition pos, boolean drawable) {
        //Call the basic constructor
        this(context, drawing, positionX, positionY, scaleFactor);

        //Set the collider
        this.colliderPositionRelativeToSprite = pos;
        PointF position = Collider.colliderPos(this.colliderPositionRelativeToSprite, this.width, this.height);

        collider = new CircleCollider(positionX, positionY, radius, position.x, position.y, drawable);
    }

    //Constructor for Sprites with RectangleCollider
    public Sprite (Context context, int drawing, double positionX, double positionY, float scaleFactor, double width, double height,  Data.ColliderPosition pos, boolean drawable) {
        //Call the basic constructor
        this(context, drawing, positionX, positionY, scaleFactor);

        //Set the collider
        this.colliderPositionRelativeToSprite = pos;
        PointF position = Collider.colliderPos(this.colliderPositionRelativeToSprite, this.width, this.height);
        collider = new RectangleCollider(positionX, positionY, position.x, position.y, width, height, drawable);
    }

    //Draw the Sprite on canvas and set the position of collider (draw collider if needed)
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bmp, (float) positionX - width / 2, (float) positionY - height / 2, null);
        if (collider != null) {
            collider.setPosition(positionX, positionY);
            if (collider.setToDraw()) {
                collider.draw(canvas);
            }
        }
    }

    //Return sprite's width and height
    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    //Check if sprite was clicked by user
    public static boolean isClicked(Sprite sprite, double x, double y) {
        return x >= (sprite.getPositionX() - sprite.getWidth()/2) && x < (sprite.getPositionX() + sprite.getWidth()/2) && y >= (sprite.getPositionY() - sprite.getHeight()/2) && y < (sprite.getPositionY() + sprite.getHeight()/2);
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

    //Change bitmap of sprite
    public void changeTexture(int drawing, Context context, double scaleFactor) {
        bmp = BitmapFactory.decodeResource(context.getResources(), drawing);
        bmp = bmp.createScaledBitmap(bmp, (int) (bmp.getWidth() * scaleFactor), (int) (bmp.getHeight() * scaleFactor), true);

        width = bmp.getWidth();
        height = bmp.getHeight();
    }

}
