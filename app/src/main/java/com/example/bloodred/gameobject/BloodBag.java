package com.example.bloodred.gameobject;

import android.content.Context;

import com.example.bloodred.Data;
import com.example.bloodred.R;

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

    private double originalPositionX;
    private double originalPositionY;
    private boolean active = false;

    private Data.BloodGroups bloodGroup;
    private Data.Rh rhType;

    public BloodBag(Context context, int drawing, double positionX, double positionY, float scaleFactor) {
        super(context, drawing, positionX, positionY, scaleFactor);
        this.originalPositionX = positionX;
        this.originalPositionY = positionY;

        decideBloodType(drawing);
    }
    public void update() {
    }

//TYMCZASOWE ROZWIAZANIE
    private void decideBloodType(int drawing) {
        switch (drawing) {
            case R.drawable.bbarhplus:
                bloodGroup = Data.BloodGroups.A;
                rhType = Data.Rh.RhPlus;
                break;
            case R.drawable.bbarhminus:
                bloodGroup = Data.BloodGroups.A;
                rhType = Data.Rh.RhMinus;
                break;
            case R.drawable.bbbrhplus:
                bloodGroup = Data.BloodGroups.B;
                rhType = Data.Rh.RhPlus;
                break;
            case R.drawable.bbbrhminus:
                bloodGroup = Data.BloodGroups.B;
                rhType = Data.Rh.RhMinus;
                break;
            case R.drawable.bbabrhplus:
                bloodGroup = Data.BloodGroups.AB;
                rhType = Data.Rh.RhPlus;
                break;
            case R.drawable.bbabrhminus:
                bloodGroup = Data.BloodGroups.AB;
                rhType = Data.Rh.RhMinus;
                break;
            case R.drawable.bb0rhplus:
                bloodGroup = Data.BloodGroups.Zero;
                rhType = Data.Rh.RhPlus;
                break;
            case R.drawable.bb0rhminus:
                bloodGroup = Data.BloodGroups.Zero;
                rhType = Data.Rh.RhMinus;
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
}
