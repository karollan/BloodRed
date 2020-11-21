package com.example.bloodred.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.bloodred.GameLoop;
import com.example.bloodred.R;

public class Performance {
    private GameLoop gameLoop;
    private Context context;

    public Performance(GameLoop gameLoop, Context context) {
        this.gameLoop = gameLoop;
        this.context = context;
    }

    public void draw(Canvas canvas) {
        drawUPS(canvas);
        drawFPS(canvas);
    }

    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText( "UPS: " + averageUPS,  100,  300, paint);
    }

    public void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText( "FPS: " + averageFPS,  100,  400, paint);
    }


}
