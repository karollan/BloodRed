package com.example.bloodred;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

public class Syringe {
    public double positionX;
    public double positionY;
    Sprite sprite;

    public Syringe(Context context, double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;

        sprite = new Sprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.syringe_1));

    }

    public void draw(Canvas canvas) {
        //canvas.drawCircle((float)positionX, (float)positionY, (float)radius, paint);
        sprite.draw(canvas, (int) positionX, (int) positionY);
    }

    public void update() {
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
    public void backToOrigin(double positionX, double positionY) {

    }
}
