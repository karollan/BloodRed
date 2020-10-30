package com.example.bloodred;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import androidx.core.content.ContextCompat;


/*
    * Game manages all object in the game and is responsible for updating all states and render
    * all objects to the screen
 */

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoop gameLoop;

    private final Syringe syringe;
    private final Patient patient;
    private Background background;

    private int mWidth = this.getResources().getDisplayMetrics().widthPixels;
    private int mHeight = this.getResources().getDisplayMetrics().heightPixels;

    public Game(Context context) {
        super(context);

        //Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);


        gameLoop = new GameLoop(this, surfaceHolder);



        // Initialize syringe
        syringe = new Syringe(getContext(), mWidth/2, mHeight/4);

        // Initialize patient
        patient = new Patient(getContext(), 350, mHeight/2);

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        double x = event.getX();
        double y = event.getY();

        //Handle touch event actions
        switch(action) {
            case MotionEvent.ACTION_DOWN:
                if (x >= (syringe.positionX - syringe.sprite.getWidth()/2) && x < (syringe.positionX + syringe.sprite.getWidth()/2) && y >= (syringe.positionY - syringe.sprite.getHeight()/2) && y < (syringe.positionY + syringe.sprite.getHeight()/2)) {
                    syringe.setPosition(x, y);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (x >= (syringe.positionX - syringe.sprite.getWidth()/2) && x < (syringe.positionX + syringe.sprite.getWidth()/2) && y >= (syringe.positionY - syringe.sprite.getHeight()/2) && y < (syringe.positionY + syringe.sprite.getHeight()/2)) {
                    syringe.setPosition(x, y);
                }
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.bg2));
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //Background
        background.draw(canvas);

        drawUPS(canvas);
        drawFPS(canvas);

        patient.draw(canvas);
        syringe.draw(canvas);
    }

    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText( "UPS: " + averageUPS,  100,  100, paint);
    }

    public void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText( "FPS: " + averageFPS,  100,  200, paint);
    }


    public void update() {
        // Update game state
        background.update();
        syringe.update();
        patient.update();
    }
}
