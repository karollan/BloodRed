package com.example.bloodred.gamepanel;

import android.content.Context;

import com.example.bloodred.R;
import com.example.bloodred.gamepanel.GameButton;

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
