package com.example.bloodred.gameobject;

import android.content.Context;

import com.example.bloodred.Data;
import com.example.bloodred.R;

/**
 * Patient is an interactive element of background which cannot be moved
 * Patient class in an extension of a Sprite, which is an extension of GameObject
 **/


public class Patient extends Sprite{

    public boolean isActive = true;

    public Patient(Context context, double positionX, double positionY, float scaleFactor) {
        super(context, R.drawable.patient, positionX, positionY, scaleFactor, 60, Data.CircleColliderPosition.CENTER_LEFT);
    }

    public void update() {

    }

    public void setInactive() {
        isActive = false;
    }

}
