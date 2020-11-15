package com.example.bloodred;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.core.content.ContextCompat;

import com.example.bloodred.object.GameButton;
import com.example.bloodred.object.InfoButton;
import com.example.bloodred.object.MenuButton;
import com.example.bloodred.object.NextButton;
import com.example.bloodred.object.Patient;
import com.example.bloodred.object.Sprite;
import com.example.bloodred.object.Syringe;
import com.example.bloodred.object.TestTube;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



/*
 * Game manages all object in the game and is responsible for updating all states and render
 * all objects to the screen
*/

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoop gameLoop;

    private int mWidth = this.getResources().getDisplayMetrics().widthPixels;
    private int mHeight = this.getResources().getDisplayMetrics().heightPixels;

    private static final int MAX_TEST_TUBE_AMOUNT = 3;


    private final Syringe syringe;
    private final Patient patient;

    //Buttons
    private final NextButton nextButton;
    private final MenuButton menuButton;
    private final InfoButton infoButton;

    private List<TestTube> testTubeList = new ArrayList<TestTube>();
    private int firstTestTubeX = mWidth/2 - 200;
    private int testTubeY = mHeight - 200;

    private Background background;


    // Status of current stage, if stage is cleared set true
    public static boolean stageCleared;

    //Current stage
    private int currentStage = 0;

    //Activated TestTubes
    private int activateTestTube = 0;



    public Game(Context context) {
        super(context);

        //Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);


        gameLoop = new GameLoop(this, surfaceHolder);



        // Initialize syringe
        syringe = new Syringe(getContext(), mWidth/2, mHeight/4, 0.8f);

        // Initialize patient
        patient = new Patient(getContext(), mWidth/2, mHeight-150, 1.5f);

        // Initialize all buttons
        nextButton = new NextButton(getContext(), mWidth-120, mHeight-120, 0.4f);
        menuButton = new MenuButton(getContext(), 120, 120, 0.3f);
        infoButton = new InfoButton(getContext(), mWidth-120, 120, 0.15f);

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
                if (x >= (syringe.getPositionX() - syringe.getWidth()/2) && x < (syringe.getPositionX() + syringe.getWidth()/2) && y >= (syringe.getPositionY() - syringe.getHeight()/2) && y < (syringe.getPositionY() + syringe.getHeight()/2)) {
                    syringe.setPosition(x, y);
                }

                if (stageCleared && x >= (nextButton.getPositionX() - nextButton.getWidth()/2) && x < (nextButton.getPositionX() + nextButton.getWidth()/2) && y >= (nextButton.getPositionY() - nextButton.getHeight()/2) && y < (nextButton.getPositionY() + nextButton.getHeight()/2)) {
                    //Continue to next stage
                    Log.d("Stage cleared", "Następny etap wczytaj");

                    //Set next stage as not yet cleared
                    stageCleared = false;

                    //Syringe is fixed to starting position after clearing stage (only on first two stages)
                    if (currentStage < 2) {
                        syringe.setPosition(mWidth/2, mHeight/4);
                    }

                    //Next stage
                    currentStage++;

                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (x >= (syringe.getPositionX() - syringe.getWidth()/2) && x < (syringe.getPositionX() + syringe.getWidth()/2) && y >= (syringe.getPositionY() - syringe.getHeight()/2) && y < (syringe.getPositionY() + syringe.getHeight()/2)) {
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

        //All buttons are rendered
        nextButton.draw(canvas);
        menuButton.draw(canvas);
        infoButton.draw(canvas);

        //Objects drawn on First Stage
        if (currentStage == 0) {
            patient.draw(canvas);
            syringe.draw(canvas);
        }

        //Objects drawn of Second Stage
        else if (currentStage == 1) {
            syringe.draw(canvas);
            for (TestTube tube : testTubeList) {
                tube.draw(canvas);
            }
        }

        //Objects drawn on Third Stage
        else if (currentStage == 2) {

        }
    }

    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText( "UPS: " + averageUPS,  100,  300, paint);
    }

    public void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText( "FPS: " + averageFPS,  100,  400, paint);
    }


    public void update() {
        // Update game state

        //Background objects which are always updated
        background.update();
        nextButton.update();
        infoButton.update();
        menuButton.update();

        syringe.update();
        patient.update();

        //Updates for objects on First Stage
        if (currentStage == 0) {
            //Check for collision between syringe and patient
            if (CircleCollider.isColliding(patient.collider, syringe.collider) && patient.isActive) {
                Log.d("Syringe and Patient", "Pobrano krew");

                //Blood can be collected only once
                patient.setInactive();

                //Syringe gets fixed

                //Syringe animation begins

                //Next stage button available

                stageCleared = true;
            }
        }
        //Updates for objects of Second Stage
        else if (currentStage == 1) {
            //Create TestTubes
            if (testTubeList.size() < MAX_TEST_TUBE_AMOUNT) {
                testTubeList.add(new TestTube(getContext(), firstTestTubeX, testTubeY, 0.7f));
                firstTestTubeX += 200;
            }
            //Update state of each Test Tube
            for (TestTube tube : testTubeList) {
                if (CircleCollider.isColliding(tube.collider, syringe.collider) && tube.isActive) {
                    //Actualize active tube test number
                    activateTestTube++;

                    //Each tube works only once
                    tube.setInactive();

                    //Start animation on test tube


                    Log.d("Syringe and Tube", "Wlano krew!");
                }

                tube.update();
            }

            //If all TestTubes were activated show buttons to choose BloodType
            if (activateTestTube == 3) {

            }

        }
    }
}
