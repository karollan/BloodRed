package com.example.bloodred.gameobject;

import android.content.Context;

import com.example.bloodred.Data;
import com.example.bloodred.R;

/**
 * TestTube is an interactive element of background which cannot be moved
 * TestTube class in an extension of a Sprite, which is an extension of GameObject
 **/


public class TestTube extends Sprite {

    public TestTube(Context context, double positionX, double positionY, float scaleFactor) {
        super(context, R.drawable.testtube, positionX, positionY, scaleFactor, 30, Data.ColliderPosition.TOP);


    }

    public void update() {

    }

}
