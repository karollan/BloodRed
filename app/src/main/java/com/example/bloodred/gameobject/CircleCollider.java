package com.example.bloodred.gameobject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * CircleCollider is an collider object, its only purpose is to be attached to Sprite objects
 * and detect collisions between them. Its shape is that of a circle
 * CircleCollider implements method from Collider abstract class
 **/

public class CircleCollider extends Collider {

    protected double radius;

    public CircleCollider(double positionX, double positionY, double radius, double colliderPosX, double colliderPosY, boolean drawable) {
        super(positionX, positionY, colliderPosX, colliderPosY, drawable);
        this.radius = radius;

    }
    public void update() {

    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float)positionX, (float)positionY, (float)radius, paint);
    }

    protected double getRadius() {
        return radius;
    }

}
