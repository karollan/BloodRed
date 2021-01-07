package com.example.bloodred.gameobject;

import android.content.Context;

import com.example.bloodred.Data;
import com.example.bloodred.R;
import com.example.bloodred.gamepanel.BloodType;
/**
 * BloodBag is an interactive object which purpose is used during stage 3
 * It contains its own blood type
 * BloodBag class in an extension of a Sprite, which is an extension of GameObject
 **/


public class BloodBag extends Sprite {

    //All available textures of still usable blood bag
    public static int[] bloodBag = {
            R.drawable.bbarhplus,
            R.drawable.bbarhminus,
            R.drawable.bbbrhplus,
            R.drawable.bbbrhminus,
            R.drawable.bbabrhplus,
            R.drawable.bbabrhminus,
            R.drawable.bb0rhplus,
            R.drawable.bb0rhminus
    };

    //All available textures of not usable blood bag
    public static int[] bloodBagUsed = {
            R.drawable.bbarhplusused,
            R.drawable.bbarhminusused,
            R.drawable.bbbrhplusused,
            R.drawable.bbbrhminusused,
            R.drawable.bbabrhplusused,
            R.drawable.bbabrhminusused,
            R.drawable.bb0rhplusused,
            R.drawable.bb0rhminusused
    };

    private final int drawing;

    //Starting position of blood bag
    private final double originalPositionX;
    private final double originalPositionY;
    private boolean active = false;

    //Each blood bag contains its own blood type
    private BloodType bloodType;

    //State that determines drawing of blood bag
    private boolean doNotDraw = false;

    //Stat that determines ability to move blood bag
    private boolean isMovable = true;

    public BloodBag(Context context, int drawing, double positionX, double positionY, float scaleFactor) {
        super(context, drawing, positionX, positionY, scaleFactor,
                Sprite.checkScaledDrawableWidth(context, drawing, scaleFactor) * 0.5,
                Sprite.checkScaledDrawableHeight(context, drawing, scaleFactor) * 0.5,
                Data.ColliderPosition.CENTER, false
                );

        this.originalPositionX = positionX;
        this.originalPositionY = positionY;
        this.drawing = drawing;

        //Set the blood bag's blood type
        decideBloodType(drawing);
    }

    public void update() {
    }

    //Blood bag's blood type is decided by using an image
    private void decideBloodType(int drawing) {
        switch (drawing) {
            case R.drawable.bbarhplus:
                bloodType = new BloodType(Data.Rh.RhPlus, Data.BloodGroups.A);
                break;
            case R.drawable.bbarhminus:
                bloodType = new BloodType(Data.Rh.RhMinus, Data.BloodGroups.A);
                break;
            case R.drawable.bbbrhplus:
                bloodType = new BloodType(Data.Rh.RhPlus, Data.BloodGroups.B);
                break;
            case R.drawable.bbbrhminus:
                bloodType = new BloodType(Data.Rh.RhMinus, Data.BloodGroups.B);
                break;
            case R.drawable.bbabrhplus:
                bloodType = new BloodType(Data.Rh.RhPlus, Data.BloodGroups.AB);
                break;
            case R.drawable.bbabrhminus:
                bloodType = new BloodType(Data.Rh.RhMinus, Data.BloodGroups.AB);
                break;
            case R.drawable.bb0rhplus:
                bloodType = new BloodType(Data.Rh.RhPlus, Data.BloodGroups.Zero);
                break;
            case R.drawable.bb0rhminus:
                bloodType = new BloodType(Data.Rh.RhMinus, Data.BloodGroups.Zero);
                break;
        }
    }

    //Set syringe position
    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    //Return to starting position
    public void setOriginalPosition() {
        this.positionX = originalPositionX;
        this.positionY = originalPositionY;
    }

    //Return blood type
    public BloodType getBloodType() {
        return this.bloodType;
    }

    //Change/Return states
    public void setDoNotDraw() {
        doNotDraw = true;
    }

    public boolean doNotDraw() {
        return doNotDraw;
    }

    public void setActive() {
        active = true;
    }

    public void setInactive() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isMovable() {
        return isMovable;
    }

    public void setMovable(int move) {
        if (move == 0) isMovable = false;
        else if (move == 1) isMovable = true;
    }

    //Return original position
    public double getOriginalPositionX() {
        return originalPositionX;
    }

    public double getOriginalPositionY() {
        return originalPositionY;
    }

    public int getDrawingIndex() {
        int drawingIndex = -1;
        for(int i = 0; i < bloodBag.length; i++) {
            if (bloodBag[i] == drawing) {
                drawingIndex = bloodBagUsed[i];
            }
        }

        return drawingIndex;
    }


}
