package com.example.bloodred.object;

import android.content.Context;
import android.util.Log;

import com.example.bloodred.R;

/**
 * Patient is an interactive element of background which cannot be moved
 * Patient class in an extension of a Sprite, which is an extension of GameObject
 **/


public class Patient extends Sprite{


    public Patient(Context context, double positionX, double positionY, float scaleFactor) {
        super(context, R.drawable.patient, positionX, positionY, scaleFactor);
    }

    public void update() {

    }

}
