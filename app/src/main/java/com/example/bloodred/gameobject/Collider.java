package com.example.bloodred.gameobject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.PointF;

import com.example.bloodred.Data;

/**
 * Collider is an abstract class which is the foundation of all colliders in the game
 * It an extension of GameObject
 **/

public abstract class Collider extends GameObject {

    //Collider position
    private final double colliderPosX;
    private final double colliderPosY;
    private final boolean drawable;
    protected Paint paint;

    private boolean active = true;

    public Collider(double positionX, double positionY, double colliderPosX, double colliderPosY, boolean drawable) {
        super(positionX, positionY);

        this.colliderPosX = colliderPosX;
        this.colliderPosY = colliderPosY;
        this.drawable = drawable;

        //Collider's paint
        this.paint = new Paint();
        paint.setColor(Color.RED);
        PathEffect dashPath = new DashPathEffect(new float[]{0.5f,0.5f},1);
        paint.setPathEffect(dashPath);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8.0f);
    }

    public abstract void draw(Canvas canvas);

    /**
     * IsColliding checks if two Collider objects are colliding, based on their positions
     * and type
     * It does not check collisions between circle and rectangle collider because it was not needed
     * but it can be added
     **/
    public static boolean isColliding(Collider obj1, Collider obj2) {
        if (obj1.active && obj2.active) {
            double distance = getDistanceBetweenObjects(obj1, obj2);
            if (obj1 instanceof CircleCollider && obj2 instanceof  CircleCollider) {
                double distanceToCollision = ((CircleCollider) obj1).getRadius() + ((CircleCollider) obj2).getRadius();
                return distance < distanceToCollision;
            } else if (obj1 instanceof RectangleCollider && obj2 instanceof RectangleCollider) {
                return obj1.getPositionX() < obj2.getPositionX() + ((RectangleCollider) obj2).getWidth() &&
                        obj1.getPositionX() + ((RectangleCollider) obj1).getWidth() > obj2.getPositionX() &&
                        obj1.getPositionY() < obj2.getPositionY() + ((RectangleCollider) obj2).getHeight() &&
                        obj1.getPositionY() + ((RectangleCollider) obj1).getHeight() > obj2.getPositionY();
            }
            else return false;
        } else return false;

    }

    //Collider can be disabled
    public void setInactive() {
        active = false;
    }

    public void setActive() {
        active = true;
    }

    //Set collider's position
    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX + colliderPosX;
        this.positionY = positionY + colliderPosY;
    }

    //ColliderPos function checks what position is needed through checking enum and returns vector with x and y
    //It is easier to create fixed positions for colliders because it is easier to use and that solution
    //provides sufficient accuracy
    public static PointF colliderPos(Data.ColliderPosition pos, float width, float height) {
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

    //Collider can be drawn on canvas if needed
    public boolean setToDraw() {
        return this.drawable;
    }
}
