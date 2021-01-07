package com.example.bloodred.gamepanel;

import android.content.Context;

import com.example.bloodred.R;
/**
 * InfoButton is one of buttons used in the game
 * InfoButton class in an extension of a GameButton, which is an extension of Sprite
 **/
public class InfoButton extends GameButton {
    public InfoButton(Context context, double positionX, double positionY, float scaleFactor) {
        super(context, R.drawable.infobutton, positionX, positionY, scaleFactor);
    }
}
