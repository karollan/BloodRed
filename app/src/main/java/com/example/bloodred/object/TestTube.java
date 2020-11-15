package com.example.bloodred.object;

import android.content.Context;

import com.example.bloodred.CircleColliderPosition;
import com.example.bloodred.R;

/**
 * TestTube is an interactive element of background which cannot be moved
 * TestTube class in an extension of a Sprite, which is an extension of GameObject
 **/


public class TestTube extends Sprite {

    public boolean isActive = true;

    public TestTube(Context context, double positionX, double positionY, float scaleFactor) {
        super(context, R.drawable.testtube, positionX, positionY, scaleFactor, 30, CircleColliderPosition.TOP);


    }

    public void update() {

    }

    public void setInactive() {
        isActive = false;
    }

}
