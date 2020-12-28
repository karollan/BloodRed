package com.example.bloodred;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.bloodred.gamepanel.Performance;


/**
 * Game manages all object in the game and is responsible for updating all states and render
 * all objects to the screen
**/

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final SceneManager sceneManager;
    private GameLoop gameLoop;

    //User screen size
    private int mWidth = this.getResources().getDisplayMetrics().widthPixels;
    private int mHeight = this.getResources().getDisplayMetrics().heightPixels;

    //Performance
    private Performance performance;


    public Game(Context context) {
        super(context);

        //Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);


        gameLoop = new GameLoop(this, surfaceHolder);

        //Scene Manager
        sceneManager = new SceneManager(getContext(), mWidth, mHeight);

        //Initialize game panels
        performance = new Performance(gameLoop, getContext());

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

                sceneManager.menuSceneClickEvents(x, y);
                sceneManager.gameSceneClickEvents(x, y);
                sceneManager.infoSceneClickEvents(x, y);
                sceneManager.gameOverSceneClickEvents(x, y);

                return true;
            case MotionEvent.ACTION_MOVE:
                sceneManager.gameSceneMoveEvents(x, y);

                return true;

            case MotionEvent.ACTION_UP:
                sceneManager.gameSceneUpEvents(x, y);

                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
            gameLoop = new GameLoop(this, surfaceHolder);
        }
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

        sceneManager.draw(canvas);

    }


    public void update() {
        // Update game state

        sceneManager.update();
    }

    public void pause() {
        gameLoop.stopLoop();
    }
}
