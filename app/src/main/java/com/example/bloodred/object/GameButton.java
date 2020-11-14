package com.example.bloodred.object;

import android.content.Context;

import com.example.bloodred.R;

import static com.example.bloodred.Game.stageCleared;

public class GameButton extends Sprite {

    public GameButton(Context context, double positionX, double positionY, float scaleFactor) {
        super(context, R.drawable.nextbutton, positionX, positionY, scaleFactor);
    }

    public void update() {

    }

}
