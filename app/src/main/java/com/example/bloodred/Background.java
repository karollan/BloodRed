package com.example.bloodred;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;

/**
 * Background is an non-interactive object which purpose is to be Background image
 *
 **/

public class Background {

    private Bitmap bitmap;

    public Background(Bitmap res) {
        bitmap = res;
    }

    public void update() {

    }

    public void draw(Canvas canvas) {
        Rect dstRect = new Rect();
        canvas.getClipBounds(dstRect);
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }

}
