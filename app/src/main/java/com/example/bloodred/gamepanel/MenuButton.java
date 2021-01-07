package com.example.bloodred.gamepanel;

import android.content.Context;

import com.example.bloodred.R;
/**
 * MenuButton is one of buttons used in the game
 * MenuButton class in an extension of a GameButton, which is an extension of Sprite
 **/
public class MenuButton extends GameButton {

    public MenuButton(Context context, double positionX, double positionY, float scaleFactor) {
        super(context, R.drawable.menubutton, positionX, positionY, scaleFactor);
    }

}
