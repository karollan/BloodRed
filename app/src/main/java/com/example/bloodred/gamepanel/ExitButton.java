package com.example.bloodred.gamepanel;

import android.content.Context;

import com.example.bloodred.R;
import com.example.bloodred.gamepanel.GameButton;
/**
 * ExitButton is one of buttons used in the game
 * ExitButton class in an extension of a GameButton, which is an extension of Sprite
 **/
public class ExitButton extends GameButton {
    public ExitButton(Context context, double positionX, double positionY, float scaleFactor) {
        super(context, R.drawable.quit, positionX, positionY, scaleFactor);
    }
}
