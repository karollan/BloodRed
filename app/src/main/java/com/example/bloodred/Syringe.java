package com.example.bloodred;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.bloodred.gameobject.Sprite;

public class Syringe2 extends SpriteSheet {

    public Syringe2(Context context,  int positionX, int positionY, int fps, int frameCount) {
        super(BitmapFactory.decodeResource(context.getResources(), R.drawable.syringe_sprite), positionX, positionY, fps, frameCount, 0.8f, 30, Data.ColliderPosition.BOTTOM_LEFT);
    }

    public void update() {
    }

    //Set syringe position
    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
