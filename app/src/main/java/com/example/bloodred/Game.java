package com.example.bloodred;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.bloodred.gamebackground.Background;
import com.example.bloodred.gameobject.BloodBag;
import com.example.bloodred.gameobject.BloodRack;
import com.example.bloodred.gameobject.CircleCollider;
import com.example.bloodred.gameobject.Collider;
import com.example.bloodred.gamepanel.BloodGroupButton;
import com.example.bloodred.gamepanel.BloodType;
import com.example.bloodred.gamepanel.InfoButton;
import com.example.bloodred.gamepanel.MenuButton;
import com.example.bloodred.gamepanel.NextButton;
import com.example.bloodred.gameobject.Patient;
import com.example.bloodred.gamepanel.Performance;
import com.example.bloodred.gamepanel.RhButton;
import com.example.bloodred.gameobject.Sprite;
import com.example.bloodred.gameobject.Syringe;
import com.example.bloodred.gameobject.TestTube;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static java.util.Collections.swap;



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


    //Background
    private Background background;

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
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.bg));
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

        //Background
        background.draw(canvas);

        sceneManager.draw(canvas);

    }


    public void update() {
        // Update game state

        background.update();
        sceneManager.update();
    }

    public void pause() {
        gameLoop.stopLoop();
    }
}
