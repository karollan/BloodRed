package com.example.bloodred.gamepanel;

import android.content.Context;

import com.example.bloodred.R;
/**
 * NextButton is one of buttons used in the game
 * NextButton class in an extension of a GameButton, which is an extension of Sprite
 **/
public class NextButton extends GameButton {

    public NextButton(Context context, double positionX, double positionY, float scaleFactor) {
        super(context, R.drawable.nextbutton, positionX, positionY, scaleFactor);
    }

}
