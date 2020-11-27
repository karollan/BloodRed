package com.example.bloodred.gameobject;

import android.content.Context;

import com.example.bloodred.Data;
import com.example.bloodred.R;

/**
 * Patient is an interactive element of background which cannot be moved
 * It has CircleCollider attached
 * Patient class in an extension of a Sprite, which is an extension of GameObject
 **/


public class Patient extends Sprite{

    public Patient(Context context, double positionX, double positionY, float scaleFactor) {
        super(context, R.drawable.patient, positionX, positionY, scaleFactor, 60, Data.ColliderPosition.CENTER_LEFT);
    }

    public void update() {

    }

}
