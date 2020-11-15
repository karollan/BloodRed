package com.example.bloodred;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.bloodred.object.GameObject;

/**
 * CircleCollider is an object which only purpose is to detect collisions with other objects
 * of its type. It is attached to another objects to handle collision event between them.
 * CircleCollider class is an extension of GameObject
 **/

public class CircleCollider extends GameObject {

    protected double radius;
    protected Paint paint;
    private final double colliderPosX;
    private final double colliderPosY;

    public CircleCollider(double positionX, double positionY, double radius, double colliderPosX, double colliderPosY) {
        super(positionX, positionY);
        this.radius = radius;

        this.colliderPosX = colliderPosX;
        this.colliderPosY = colliderPosY;

        this.paint = new Paint();
        paint.setColor(Color.RED);
    }
    public void update() {

    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float)positionX, (float)positionY, (float)radius, paint);
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX + colliderPosX;
        this.positionY = positionY + colliderPosY;
    }

    //isColliding method checks if two CircleColliders objects are colliding, based on their distance and radius
    public static boolean isColliding(CircleCollider obj1, CircleCollider obj2) {
        double distance = getDistanceBetweenObjects(obj1, obj2);
        double distanceToCollision = obj1.getRadius() + obj2.getRadius();
        if (distance < distanceToCollision) {
            return true;
        } else return false;

    }

    private double getRadius() {
        return radius;
    }

}
