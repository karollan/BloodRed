package com.example.bloodred.gamepanel;

import android.content.Context;

import com.example.bloodred.R;
import com.example.bloodred.gamepanel.GameButton;
/**
 * PreviousButton is one of buttons used in the game
 * PreviousButton class in an extension of a GameButton, which is an extension of Sprite
 **/
public class PreviousButton extends GameButton {
    public PreviousButton(Context context, double positionX, double positionY, float scaleFactor) {
        super(context, R.drawable.previousbutton, positionX, positionY, scaleFactor);
    }
}
