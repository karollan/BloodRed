package com.example.bloodred.gameobject;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.bloodred.R;
/**
 * ScoreStar is an non-interactive element of background which cannot be moved
 * ScoreStar class in an extension of a SpriteSheet, which is an extension of GameObject
 * Its purpose is to inform user of his game results
 **/
public class ScoreStar extends SpriteSheet {

    private final boolean starColor;

    public ScoreStar(Context context, int positionX, int positionY, int fps, int frameCount, int numberOfPauses, boolean alwaysOn, boolean isGranted) {
        //ScoreStar spritesheet is decided by user's game results
        super(
                isGranted ? BitmapFactory.decodeResource(context.getResources(), R.drawable.star) : BitmapFactory.decodeResource(context.getResources(), R.drawable.stargray),
                positionX, positionY, fps, frameCount, 0.8f, numberOfPauses, alwaysOn);

        this.starColor = isGranted;
    }

    public boolean getStarColor() {return starColor;}

    public void update() {
    }

}
