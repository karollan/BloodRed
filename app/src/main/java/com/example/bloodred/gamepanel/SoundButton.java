package com.example.bloodred.gamepanel;

import android.content.Context;

import com.example.bloodred.R;
import com.example.bloodred.gamepanel.GameButton;
/**
 * SoundButton is one of buttons used in the game
 * SoundButton class in an extension of a GameButton, which is an extension of Sprite
 * It has two states - on and off
 **/
public class SoundButton extends GameButton {

    private boolean state;

    public SoundButton(Context context, double positionX, double positionY, float scaleFactor) {
        super(context, R.drawable.sound, positionX, positionY, scaleFactor);
        state = false;
    }

    public boolean getState() {
        return state;
    }

    public void changeState() {
        state = !state;
    }
}
