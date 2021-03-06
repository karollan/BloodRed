package com.example.bloodred.gamepanel;

import android.content.Context;

import com.example.bloodred.Data;
import com.example.bloodred.R;
/**
 * RhButton is one of buttons used in the game
 * RhButton class in an extension of a GameButton, which is an extension of Sprite
 **/
public class RhButton extends GameButton {

    //Array of possible Rh Sprites
    public static int[] rh = {
            R.drawable.rhminus,
            R.drawable.rhplus
    };

    private Data.Rh rhType;


    public RhButton(Context context, int drawing, double positionX, double positionY, float scaleFactor) {
        super(context, drawing, positionX, positionY, scaleFactor);
        decideRhType(drawing);
    }

    public Data.Rh getRhType() {
        return rhType;
    }

    //Rh type of this button is decided by its sprite
    private void decideRhType(int drawing) {
        switch (drawing) {
            case R.drawable.rhminus:
                rhType = Data.Rh.RhMinus;
                break;
            case R.drawable.rhplus:
                rhType = Data.Rh.RhPlus;
        }
    }
}
