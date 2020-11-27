package com.example.bloodred.gameobject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Collider is an abstract class which is the foundation of all colliders in the game
 * It an extension of GameObject
 **/

public abstract class Collider extends GameObject {

    private final double colliderPosX;
    private final double colliderPosY;
    protected Paint paint;

    private boolean active = true;

    public Collider(double positionX, double positionY, double colliderPosX, double colliderPosY) {
        super(positionX, positionY);

        this.colliderPosX = colliderPosX;
        this.colliderPosY = colliderPosY;

        this.paint = new Paint();
        paint.setColor(Color.RED);
    }

    public abstract void draw(Canvas canvas);

    /**
     * IsColliding checks if two Collider objects are colliding, based on their positions
     * and type
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

    public void setInactive() {
        active = false;
    }

    public void setActive() {
        active = true;
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX + colliderPosX;
        this.positionY = positionY + colliderPosY;
    }

}
