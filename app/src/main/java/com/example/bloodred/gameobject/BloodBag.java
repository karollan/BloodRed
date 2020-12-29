package com.example.bloodred.gameobject;

import android.content.Context;

import com.example.bloodred.Data;
import com.example.bloodred.R;
import com.example.bloodred.gamepanel.BloodType;


public class BloodBag extends Sprite {

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

    private double originalPositionX;
    private double originalPositionY;
    private boolean active = false;

    private BloodType bloodType;
    private boolean doNotDraw = false;
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

        decideBloodType(drawing);
    }

    public void update() {
    }

//TYMCZASOWE ROZWIAZANIE
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

    public void setOriginalPosition() {
        this.positionX = originalPositionX;
        this.positionY = originalPositionY;
    }

    public BloodType getBloodType() {
        return this.bloodType;
    }

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

    public boolean isMovable() {
        return isMovable;
    }
    public void setMovable(int move) {
        if (move == 0) isMovable = false;
        else if (move == 1) isMovable = true;
    }
}
