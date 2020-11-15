package com.example.bloodred.object;

import android.content.Context;

import com.example.bloodred.R;

import static com.example.bloodred.Game.stageCleared;

/**
 * GameButton is an object that is the precursor of all button objects in game
 * GameButton class in an extension of a Sprite, which is an extension of GameObject
 **/

public abstract class GameButton extends Sprite {

    public GameButton(Context context, int drawing, double positionX, double positionY, float scaleFactor) {
        super(context, drawing, positionX, positionY, scaleFactor);
    }

    public void update() {

    }

}
