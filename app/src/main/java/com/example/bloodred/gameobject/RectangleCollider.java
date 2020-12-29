package com.example.bloodred.gameobject;

import android.graphics.Canvas;

/**
 * RectangleCollider is an collider object, its only purpose is to be attached to Sprite objects
 * and detect collisions between them. Its shape is that of a rectangle
 * RectangleCollider implements method from Collider abstract class
 **/


public class RectangleCollider extends Collider {

    protected double width;
    protected double height;

    public RectangleCollider(double positionX, double positionY, double colliderPosX, double colliderPosY, double width, double height, boolean drawable) {
        super(positionX, positionY, colliderPosX, colliderPosY, drawable);

        this.width = width;
        this.height = height;

    }

    public void draw(Canvas canvas) {
        canvas.drawRect((float)(positionX - width/2), (float)(positionY - height/2), (float)(positionX + height/2), (float)(positionY + width/2), paint);
    }

    protected double getWidth() {
        return width;
    }

    protected  double getHeight() {
        return height;
    }

    public void update(){};
}
