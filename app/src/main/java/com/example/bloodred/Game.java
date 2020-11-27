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
    private GameLoop gameLoop;

    //User screen size
    private int mWidth = this.getResources().getDisplayMetrics().widthPixels;
    private int mHeight = this.getResources().getDisplayMetrics().heightPixels;

    private static final int MAX_TEST_TUBE_AMOUNT = 3;
    private static final int MAX_BLOOD_GROUP_BUTTON_AMOUNT = 4;
    private static final int MAX_RH_BUTTON_AMOUNT = 2;
    private static final int MAX_BLOOD_BAG_AMOUNT = 8;
    private static final int MAX_NUMBER_OF_MISTAKES = 3;

    //Syringe
    private final Syringe syringe;

    //Patient
    private final Patient patient;

    //BloodRack
    private final BloodRack bloodRack;

    //Performance
    private Performance performance;

    //UI Buttons
    private final NextButton nextButton;
    private final MenuButton menuButton;
    private final InfoButton infoButton;

    //TestTubes
    private final List<TestTube> testTubeList = new ArrayList<TestTube>();
    private int firstTestTubeX = mWidth/2 - 200;
    private final int testTubeY = mHeight - 200;

    //BloodBags
    private final List<BloodBag> bloodBagList = new ArrayList<>();
    private int firstBloodBagX = mWidth/2 - 300;
    private int bloodBagY = mHeight/2 - 200;

    //BloodGroupButtons
    private final List<BloodGroupButton> BloodGroupButtonList = new ArrayList<>();
    private int firstBloodGroupButtonX = mWidth/2 - 300;
    private final int BloodGroupButtonY = 200;

    //RhButtons
    private final List<RhButton> RhButtonList = new ArrayList<>();
    private final int GroupButtonWidth = Sprite.checkScaledDrawableWidth(getContext(), R.drawable.group0, 0.4f)/2;
    private final int rhButtonShift = 150;
    private final int RhButtonY = BloodGroupButtonY + 200;

    //Background
    private Background background;

    //Blood Type
    private final BloodType bloodType;

    //Current Blood Group chosen by user
    private Data.BloodGroups currentBloodGroup;

    //Possible donors
    private int numberOfDonors;

    //Current number of mistakes
    private int numberOfMistakes = 0;

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

        //Initialize bloodRack
        bloodRack = new BloodRack(getContext(), 200, mHeight/1.5f, 0.7f);

        // Initialize all buttons
        nextButton = new NextButton(getContext(), mWidth-120, mHeight-120, 0.4f);
        menuButton = new MenuButton(getContext(), 120, 120, 0.3f);
        infoButton = new InfoButton(getContext(), mWidth-120, 120, 0.15f);

        //Randomize Blood Group
        bloodType = new BloodType(Data.Rh.getRandomRh(), Data.BloodGroups.getRandomBloodGroup());
        numberOfDonors = BloodType.numberOfPossibleDonors(bloodType);

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
                //Move syringe
                if (x >= (syringe.getPositionX() - syringe.getWidth()/2) && x < (syringe.getPositionX() + syringe.getWidth()/2) && y >= (syringe.getPositionY() - syringe.getHeight()/2) && y < (syringe.getPositionY() + syringe.getHeight()/2)) {
                    syringe.setPosition(x, y);
                }

                //Move blood bag
                for (BloodBag bag : bloodBagList) {
                    if (x >= (bag.getPositionX() - bag.getWidth()/2) && x < (bag.getPositionX() + bag.getWidth()/2) && y >= (bag.getPositionY() - bag.getHeight()/2) && y < (bag.getPositionY() + bag.getHeight()/2)) {
                        bag.setPosition(x, y);
                        bag.setActive();
                    } else bag.setInactive();
                }

                /**
                 * Alogrithm to check if more than one bag was clicked, if yes choose the one that is closer
                 *
                 **/
                //Create new list and add all active bags
                List<BloodBag> activeBloodBags = new ArrayList<>();
                for (BloodBag bag : bloodBagList) {
                    if (bag.isActive()) {
                        activeBloodBags.add(bag);
                    }
                }
                //If list has at least two bags check distance between them and event distances
                if (activeBloodBags.size() > 1) {
                    double differenceX = Math.abs(x - activeBloodBags.get(0).getOriginalPositionX());
                    double differenceY = Math.abs(y - activeBloodBags.get(0).getOriginalPositionY());
                    int index = 0;
                    for (int i = 0; i < activeBloodBags.size(); i++) {
                        double newDifferenceX = Math.abs(x - activeBloodBags.get(i).getOriginalPositionX());
                        double newDifferenceY = Math.abs(y - activeBloodBags.get(i).getOriginalPositionY());
                        if (newDifferenceX < differenceX && newDifferenceY <= differenceY)  {
                            index = i;
                            differenceX = newDifferenceX;
                            differenceY = newDifferenceY;
                        }
                    }
                    //Choose the one that is closer and remove it from list
                    activeBloodBags.remove(index);

                    //All remaining bags are set to original positions, inactivated and the list is cleared
                    for (BloodBag bag : activeBloodBags) {
                        bag.setInactive();
                        bag.setOriginalPosition();
                    }
                    activeBloodBags.clear();
                }
                //If list has less than 2 elements -> clear the list
                else activeBloodBags.clear();

                //Click on nextButton
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

                //Click on one of BloodGroupButtons
                for (BloodGroupButton btn : BloodGroupButtonList) {
                    if (x >= (btn.getPositionX() - btn.getWidth()/2) && x < (btn.getPositionX() + btn.getWidth()/2) && y >= (btn.getPositionY() - btn.getHeight()/2) && y < (btn.getPositionY() + btn.getHeight()/2)) {
                        RhButtonList.clear();
                        btn.setActive();
                        currentBloodGroup = btn.getBloodGroup();
                    } else btn.setInactive();
                }

                //Click on one of RhButtons
                for (RhButton btn : RhButtonList) {
                    if (x >= (btn.getPositionX() - btn.getWidth()/2) && x < (btn.getPositionX() + btn.getWidth()/2) && y >= (btn.getPositionY() - btn.getHeight()/2) && y < (btn.getPositionY() + btn.getHeight()/2)) {
                        if (currentBloodGroup == bloodType.getBloodGroup() && btn.getRhType() == bloodType.getRhType()) {
                            Log.d("DOBRY WYBOR", currentBloodGroup.toString() + bloodType.getBloodGroup().toString() + btn.getRhType().toString() + bloodType.getRhType().toString());

                            stageCleared = true;
                        } else {
                            Log.d("ZLY WYBOR", currentBloodGroup.toString() + bloodType.getBloodGroup().toString() + btn.getRhType().toString() + bloodType.getRhType().toString());
                            for (BloodGroupButton BGB : BloodGroupButtonList) {
                                BGB.setInactive();
                            }
                        }
                    }
                }


                return true;
            case MotionEvent.ACTION_MOVE:
                //Move syringe
                if (x >= (syringe.getPositionX() - syringe.getWidth()/2) && x < (syringe.getPositionX() + syringe.getWidth()/2) && y >= (syringe.getPositionY() - syringe.getHeight()/2) && y < (syringe.getPositionY() + syringe.getHeight()/2)) {
                    syringe.setPosition(x, y);
                }

                //Move blood bag
                for (BloodBag bag : bloodBagList) {
                    if (bag.isActive() && x >= (bag.getPositionX() - bag.getWidth()/2) && x < (bag.getPositionX() + bag.getWidth()/2) && y >= (bag.getPositionY() - bag.getHeight()/2) && y < (bag.getPositionY() + bag.getHeight()/2)) {
                        bag.setPosition(x, y);
                    }
                }

                return true;

            case MotionEvent.ACTION_UP:
                //After releasing one of Blood Bags
                for (BloodBag bag : bloodBagList) {
                    bag.setOriginalPosition();
                    bag.setInactive();
                }
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

        //Performance
        performance.drawUPS(canvas);
        performance.drawFPS(canvas);

        //All buttons are rendered
        nextButton.draw(canvas);
        menuButton.draw(canvas);
        infoButton.draw(canvas);

        /**
         * DRAWING FOR STAGE 1
         *
         **/
        if (currentStage == 0) {
            patient.draw(canvas);
            syringe.draw(canvas);
        }

        /**
         * DRAWING FOR STAGE 2
         *
         **/
        else if (currentStage == 1) {

            //Syringe in being drawn until all test tubes are activated
            if (activateTestTube < MAX_TEST_TUBE_AMOUNT) {
                syringe.draw(canvas);
            }

            for (TestTube tube : testTubeList) {
                tube.draw(canvas);
            }
            //Check if all tubes were activated
            if (activateTestTube == MAX_TEST_TUBE_AMOUNT) {
                //Show bloodgroup buttons
                for (BloodGroupButton btn : BloodGroupButtonList) {
                    btn.draw(canvas);

                    //If one of buttons was clicked show Rh buttons
                    if (btn.isActive()) {
                        for (RhButton rhBtn : RhButtonList) {
                            rhBtn.draw(canvas);
                        }
                    }
                }

            }
        }


        /**
         * DRAWING FOR STAGE 3
         *
         **/
        else if (currentStage == 2) {
            for (BloodBag bag : bloodBagList) {
                if (bag.doNotDraw()) continue;
                else bag.draw(canvas);
            }

            bloodRack.draw(canvas);
        }
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

        /**
         * UPDATES FOR STAGE 1
         *
         **/
        if (currentStage == 0) {
            //Check for collision between syringe and patient
            if (Collider.isColliding(patient.collider, syringe.collider)) {
                Log.d("Syringe and Patient", "Pobrano krew");

                //Blood can be collected only once
                patient.collider.setInactive();

                //Syringe gets fixed

                //Syringe animation begins

                //Next stage button available

                stageCleared = true;
            }
        }
        /**
         * UPDATES FOR STAGE 2
         *
         **/
        else if (currentStage == 1) {
            //Create TestTubes
            if (testTubeList.size() < MAX_TEST_TUBE_AMOUNT) {
                testTubeList.add(new TestTube(getContext(), firstTestTubeX, testTubeY, 0.7f));
                firstTestTubeX += 200;
            }
            //Update state of each Test Tube
            for (TestTube tube : testTubeList) {
                if (Collider.isColliding(tube.collider, syringe.collider)) {
                    //Actualize active tube test number
                    activateTestTube++;

                    //Each tube works only once
                    tube.collider.setInactive();

                    //Start animation on test tube


                    Log.d("Syringe and Tube", "Wlano krew!");
                }

                tube.update();
            }

            //If all TestTubes were activated show buttons to choose BloodType
            if (activateTestTube == MAX_TEST_TUBE_AMOUNT) {

                // TYMCZASOWO
                //stageCleared = true;

                //Create BloodGroupButtons
                if (BloodGroupButtonList.size() < MAX_BLOOD_GROUP_BUTTON_AMOUNT) {
                    BloodGroupButtonList.add(new BloodGroupButton(getContext(), BloodGroupButton.bloodGroups[BloodGroupButtonList.size()], firstBloodGroupButtonX, BloodGroupButtonY, 0.4f));
                    firstBloodGroupButtonX += 200;
                }

                //Create RhButtons only on active Blood Group Button
                for (BloodGroupButton btn : BloodGroupButtonList) {
                    if (btn.isActive()) {
                        Log.d("ISACTIVE", "JESTEM AKTYWNY");
                        while (RhButtonList.size() < MAX_RH_BUTTON_AMOUNT) {
                            if (RhButtonList.isEmpty()) {
                                RhButtonList.add(new RhButton(getContext(), RhButton.rh[RhButtonList.size()], btn.getPositionX()-GroupButtonWidth, RhButtonY, 0.3f));
                            } else RhButtonList.add(new RhButton(getContext(), RhButton.rh[RhButtonList.size()], btn.getPositionX()-GroupButtonWidth+rhButtonShift, RhButtonY, 0.3f));

                        }
                    }
                }

            }
            /**
             * UPDATES FOR STAGE 3
             *
             **/
        } else if (currentStage == 2) {
            //Create Blood Bags
            if (bloodBagList.size() < MAX_BLOOD_BAG_AMOUNT) {
                if (bloodBagList.size() == MAX_BLOOD_BAG_AMOUNT/2) {
                    firstBloodBagX = mWidth/2 - 300;
                    bloodBagY = mHeight/2 + 200;
                }
                bloodBagList.add(new BloodBag(getContext(), BloodBag.bloodBag[bloodBagList.size()], firstBloodBagX, bloodBagY, 0.4f));
                firstBloodBagX += 250;
            }

            //Current active Blood Bag always drawn first
            for (BloodBag bag : bloodBagList) {
                if (bag.isActive()) {
                    swap(bloodBagList, bloodBagList.indexOf(bag), bloodBagList.size()-1);
                }
            }

            //Check if the BloodBag collided with BloodRack
            for (BloodBag bag : bloodBagList) {
                if (Collider.isColliding(bag.collider, bloodRack.collider) && numberOfMistakes < MAX_NUMBER_OF_MISTAKES) {
                    if (BloodType.checkBloodCompatibility(bag.getBloodType(), bloodType)) {
                        Log.d("Number of donors", String.valueOf(numberOfDonors));
                        Log.d("BloodBag", "Removed");
                        bag.setDoNotDraw();
                        bag.collider.setInactive();
                        numberOfDonors--;
                        Log.d("Number of donors", String.valueOf(numberOfDonors));
                    } else {
                        bag.collider.setInactive();
                        bag.setOriginalPosition();
                        numberOfMistakes++;
                        Log.d("Number of mistakes", String.valueOf(numberOfMistakes));
                    }
                }
            }
            //Update state of BloodRack
            bloodRack.update();

            //GAME OVER
            if (numberOfMistakes == MAX_NUMBER_OF_MISTAKES) {

            }

        }
    }

    public void pause() {
        gameLoop.stopLoop();
    }
}
