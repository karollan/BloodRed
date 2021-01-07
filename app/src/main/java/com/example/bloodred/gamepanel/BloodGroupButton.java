package com.example.bloodred.gamepanel;

import android.content.Context;

import com.example.bloodred.Data;
import com.example.bloodred.R;
/**
 * BloodGroupButton is one of buttons used in the game
 * BloodGroupButton class in an extension of a GameButton, which is an extension of Sprite
 **/
public class BloodGroupButton extends GameButton {

    private boolean active = false;

    private Data.BloodGroups bloodGroup;

    //Array of possible BloodGroupButton Sprites
    public static int[] bloodGroups = {
            R.drawable.groupa,
            R.drawable.groupb,
            R.drawable.groupab,
            R.drawable.group0
    };


    public BloodGroupButton(Context context, int drawing, double positionX, double positionY, float scaleFactor) {
        super(context, drawing, positionX, positionY, scaleFactor);
        decideBloodGroup(drawing);
    }

    //Blood group of this button is decided by its sprite
    private void decideBloodGroup(int drawing) {
        switch (drawing) {
            case R.drawable.groupa:
                bloodGroup = Data.BloodGroups.A;
                break;
            case R.drawable.groupb:
                bloodGroup = Data.BloodGroups.B;
                break;
            case R.drawable.groupab:
                bloodGroup = Data.BloodGroups.AB;
                break;
            case R.drawable.group0:
                bloodGroup = Data.BloodGroups.Zero;
                break;
        }

    }

    public Data.BloodGroups getBloodGroup() {
        return bloodGroup;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive() {
        active = true;
    }

    public void setInactive() {
        active = false;
    }
}
