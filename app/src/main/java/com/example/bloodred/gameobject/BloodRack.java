package com.example.bloodred.gameobject;

import android.content.Context;

import com.example.bloodred.Data;
import com.example.bloodred.R;

/**
 * BloodRack is an interactive element of background which cannot be moved
 * It has RectangleCollider attached
 * BloodRack class in an extension of a Sprite, which is an extension of GameObject
 **/

public class BloodRack extends Sprite {

    public BloodRack(Context context, double positionX, double positionY, float scaleFactor) {
        super(context, R.drawable.rack, positionX, positionY, scaleFactor,
                Sprite.checkScaledDrawableWidth(context, R.drawable.rack, scaleFactor)/2,
                Sprite.checkScaledDrawableHeight(context, R.drawable.rack, scaleFactor)/4,
                Data.ColliderPosition.CENTER_TOP_RIGHT);
    }

    public void update() {

    }
}
