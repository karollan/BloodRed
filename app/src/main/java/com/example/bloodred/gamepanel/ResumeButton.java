package com.example.bloodred.gamepanel;

import android.content.Context;

import com.example.bloodred.R;
import com.example.bloodred.gamepanel.GameButton;
/**
 * ResumeButton is one of buttons used in the game
 * ResumeButton class in an extension of a GameButton, which is an extension of Sprite
 **/
public class ResumeButton extends GameButton {

    public ResumeButton(Context context, double positionX, double positionY, float scaleFactor) {
        super(context, R.drawable.resume, positionX, positionY, scaleFactor);
    }

}
