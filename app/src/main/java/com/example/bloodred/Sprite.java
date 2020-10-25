package com.example.bloodred;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Sprite {
    private Bitmap image;

    public Sprite(Bitmap bmp) {
        image = bmp;
    }
    public void draw(Canvas canvas,  int positionX, int positionY) {
        canvas.drawBitmap(image, positionX - (image.getWidth()/2), positionY - (image.getHeight()/2), null);
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }
}
