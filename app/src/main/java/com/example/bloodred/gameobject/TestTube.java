package com.example.bloodred.gameobject;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.bloodred.Data;
import com.example.bloodred.R;
import com.example.bloodred.gamepanel.BloodType;

/**
 * TestTube is an interactive element of background which cannot be moved
 * TestTube class in an extension of a SpriteSheet, which is an extension of GameObject
 **/


public class TestTube extends SpriteSheet {

    //TestTube has it's own type included in Data class
    private final Data.TestTubeTypes testTubeType;

    public TestTube(Data.TestTubeTypes testTubeType, BloodType bloodType, Context context, int positionX, int positionY, int fps, int frameCount, int numberOfPauses, boolean alwaysOn) {
        //TestTube spritesheet is decided by recipient blood type
        super(
                (bloodType.getBloodGroup().toString().contains(testTubeType.toString()) || testTubeType.toString().equals(bloodType.getRhType().toString())) ? BitmapFactory.decodeResource(context.getResources(), R.drawable.testtubesprite_second) : BitmapFactory.decodeResource(context.getResources(), R.drawable.testtubesprite_first),
                positionX, positionY, fps, frameCount, 0.7f, numberOfPauses, alwaysOn, 30, Data.ColliderPosition.TOP, true);

        this.testTubeType = testTubeType;
    }

    public Data.TestTubeTypes getTestTubeType() {
        return testTubeType;
    }

    public void update() {

    }

}
