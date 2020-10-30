package com.example.bloodred;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Patient {

    Sprite sprite;
    public double positionX;
    public double positionY;

    public Patient(Context context, double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        sprite = new Sprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.patient));
    }

    public void draw(Canvas canvas) {
        //canvas.save();
        //canvas.scale(2f, 2f);
        sprite.draw(canvas, (int) positionX, (int) positionY);

        //canvas.restore();
    }

    public void update() {
    }
}
