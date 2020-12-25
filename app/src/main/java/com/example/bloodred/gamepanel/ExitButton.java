package com.example.bloodred;

import android.content.Context;

import com.example.bloodred.gamepanel.GameButton;

public class ExitButton extends GameButton {
    public ExitButton(Context context, double positionX, double positionY, float scaleFactor) {
        super(context, R.drawable.menubutton, positionX, positionY, scaleFactor);
    }
}
