package com.example.bloodred.gamepanel;

import android.content.Context;

import com.example.bloodred.R;
import com.example.bloodred.gamepanel.GameButton;
/**
 * RestartButton is one of buttons used in the game
 * RestartButton class in an extension of a GameButton, which is an extension of Sprite
 **/
public class RestartButton extends GameButton {

    public RestartButton(Context context, double positionX, double positionY, float scaleFactor) {
        super(context, R.drawable.restart, positionX, positionY, scaleFactor);
    }

}
