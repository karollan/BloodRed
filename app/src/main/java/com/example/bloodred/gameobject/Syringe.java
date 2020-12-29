package com.example.bloodred.gameobject;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.bloodred.Data;
import com.example.bloodred.R;

/**
 * Syringe is an object that is interactive and directly moved by a player.
 * Syringe class in an extension of a Sprite, which is an extension of GameObject
 **/

public class Syringe extends SpriteSheet {

    public Syringe(Context context,  int positionX, int positionY, int fps, int frameCount, int numberOfPauses, boolean alwaysOn) {
        super(BitmapFactory.decodeResource(context.getResources(), R.drawable.syringe_sprite), positionX, positionY, fps, frameCount, 0.8f, numberOfPauses, alwaysOn, 30, Data.ColliderPosition.BOTTOM_LEFT, false);
    }

    public void update() {
    }

    //Set syringe position
    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
