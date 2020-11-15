package com.example.bloodred.object;

import android.content.Context;

import com.example.bloodred.CircleColliderPosition;
import com.example.bloodred.R;

/**
 * Syringe is an object that is interactive and directly moved by a player.
 * Syringe class in an extension of a Sprite, which is an extension of GameObject
 **/

public class Syringe extends Sprite {

    public Syringe(Context context, double positionX, double positionY, float scaleFactor) {
        super(context, R.drawable.syringe_1, positionX, positionY, scaleFactor, 30, CircleColliderPosition.BOTTOM_LEFT);
    }

    public void update() {
    }

    //Set syringe position
    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

}
