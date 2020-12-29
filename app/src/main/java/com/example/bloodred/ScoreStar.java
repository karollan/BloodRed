package com.example.bloodred;

import android.content.Context;
import android.graphics.BitmapFactory;

public class ScoreStar extends SpriteSheet {

    private final boolean starColor;

    public ScoreStar(Context context, int positionX, int positionY, int fps, int frameCount, int numberOfPauses, boolean alwaysOn, boolean isGranted) {
        super(
                isGranted ? BitmapFactory.decodeResource(context.getResources(), R.drawable.star) : BitmapFactory.decodeResource(context.getResources(), R.drawable.stargray),
                positionX, positionY, fps, frameCount, 0.8f, numberOfPauses, alwaysOn);

        this.starColor = isGranted;
    }

    public boolean getStarColor() {return starColor;}

    public void update() {
    }

}
