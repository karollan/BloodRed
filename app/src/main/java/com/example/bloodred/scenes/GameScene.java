package com.example.bloodred.scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.example.bloodred.Data;
import com.example.bloodred.FXPlayer;
import com.example.bloodred.R;
import com.example.bloodred.Score;
import com.example.bloodred.gameobject.BloodBag;
import com.example.bloodred.gameobject.BloodRack;
import com.example.bloodred.gameobject.Collider;
import com.example.bloodred.gameobject.Patient;
import com.example.bloodred.gameobject.Sprite;
import com.example.bloodred.gameobject.SpriteSheet;
import com.example.bloodred.gameobject.Syringe;
import com.example.bloodred.gameobject.TestTube;
import com.example.bloodred.gamepanel.BloodGroupButton;
import com.example.bloodred.gamepanel.BloodType;
import com.example.bloodred.gameobject.ChoiceMessage;
import com.example.bloodred.gamepanel.InfoButton;
import com.example.bloodred.gamepanel.MenuButton;
import com.example.bloodred.gamepanel.NextButton;
import com.example.bloodred.gamepanel.RhButton;
import com.example.bloodred.gamepanel.StatusText;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.swap;
/**
 * GameScene is the most important scene it is the game itself
 * GameOverScene class in an extension of a ScenePrototype
 **/
public class GameScene extends ScenePrototype{

    //Limit values
    private static final int MAX_TEST_TUBE_AMOUNT = 3;
    private static final int MAX_BLOOD_GROUP_BUTTON_AMOUNT = 4;
    private static final int MAX_RH_BUTTON_AMOUNT = 2;
    private static final int MAX_BLOOD_BAG_AMOUNT = 8;
    private static final int MAX_NUMBER_OF_MISTAKES = 3;

    //Game objects
    private final Syringe syringe;
    private final Patient patient;
    private final BloodRack bloodRack;
    private final NextButton nextButton;
    private final MenuButton menuButton;
    private final InfoButton infoButton;

    //Sounds
    private final FXPlayer fxPlayer;

    //TestTubes
    private final List<TestTube> testTubeList = new ArrayList<TestTube>();
    private int firstTestTubeX;
    private int testTubeY;

    //StatusText
    private final StatusText statusText;

    //BloodBags
    private final List<BloodBag> bloodBagList = new ArrayList<>();
    private int firstBloodBagX;
    private int bloodBagY;

    //BloodGroupButtons
    private final List<BloodGroupButton> BloodGroupButtonList = new ArrayList<>();
    private int firstBloodGroupButtonX;
    private final int BloodGroupButtonY = 200;

    //RhButtons
    private final List<RhButton> RhButtonList = new ArrayList<>();
    private final int GroupButtonWidth;
    private final int rhButtonShift = 150;
    private final int RhButtonY = BloodGroupButtonY + 200;

    //Current Blood Group chosen by user
    private Data.BloodGroups currentBloodGroup;

    //Blood Type
    private final BloodType bloodType;

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
    private boolean gameResult;

    //Choice message
    private ChoiceMessage choiceMessage;
    private boolean isChoiceMessageClosed = true;

    //Score counting
    private boolean mistakeMade = false;
    private boolean secondMistake = false;


    public GameScene(Bitmap res, Context context, int mWidth, int mHeight, SceneManager sceneManager, FXPlayer fxPlayer) {
        super(res, context, mWidth, mHeight, sceneManager);

        this.fxPlayer = fxPlayer;

        //Initialize variables values
        firstTestTubeX = mWidth/2 - 200;
        testTubeY = mHeight - 200;
        firstBloodBagX = mWidth/2 - 300;
        bloodBagY = mHeight/2 - 200;
        firstBloodGroupButtonX = mWidth/2 - 300;
        GroupButtonWidth = Sprite.checkScaledDrawableWidth(context, R.drawable.group0, 0.4f)/2;

        //Initialize StatusText
        statusText = new StatusText(mWidth, mHeight, this);

        // Initialize syringe
        syringe = new Syringe(context, mWidth/2, mHeight/3, 12, 20, 4, false);

        // Initialize patient
        patient = new Patient(context, mWidth/2, mHeight-150, 1.5f);

        //Initialize bloodRack
        bloodRack = new BloodRack(context, 200, mHeight/1.5f, 0.7f);

        // Initialize all buttons
        nextButton = new NextButton(context, mWidth-120, mHeight-120, 0.4f);
        menuButton = new MenuButton(context, 120, 120, 0.3f);
        infoButton = new InfoButton(context, mWidth-120, 120, 0.15f);

        //Randomize Blood Group
        bloodType = new BloodType(Data.Rh.getRandomRh(), Data.BloodGroups.getRandomBloodGroup());
        numberOfDonors = BloodType.numberOfPossibleDonors(bloodType);
    }

    public void drawScene(Canvas canvas) {
        background.draw(canvas);
        menuButton.draw(canvas);
        infoButton.draw(canvas);

        //Next button only on first two stages
        if (currentStage < 2) {
            nextButton.draw(canvas);
        }

        /**
         * DRAWING FOR STAGE 1
         *
         **/
        if (currentStage == 0) {
            patient.draw(canvas);
        }

        /**
         * DRAWING FOR STAGE 2
         *
         **/
        else if (currentStage == 1) {

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

            //Draw choice message
            try {
                if (choiceMessage.isCreated()) {
                    choiceMessage.draw(canvas);
                }
            }
            catch(Exception e){}


        }

        //Syringe is drawn until animation is done
        if (!syringe.inLastFrame()) {
            syringe.draw(canvas);
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

            try {
                if (choiceMessage.isCreated()) {
                    choiceMessage.draw(canvas);
                }
            }
            catch(Exception e){}

        }

        statusText.draw(canvas);

    }

    public void update() {
        nextButton.update();
        infoButton.update();
        menuButton.update();

        patient.update();

        syringe.update(System.currentTimeMillis());

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
                syringe.setPosition(patient.collider.getPositionX() + syringe.getSpriteWidth()/2, patient.collider.getPositionY() - syringe.getSpriteHeight()/2);

                //Syringe animation begins
                syringe.startAnimation();

                //Syringe sound
                fxPlayer.playSyringeSuckSound();

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

                testTubeList.add(new TestTube(Data.TestTubeTypes.getTestTubeTypeType(testTubeList.size()),bloodType, context, firstTestTubeX, testTubeY, 8, 11, 1, false));
                firstTestTubeX += 200;
            }
            //Update state of each Test Tube
            for (TestTube tube : testTubeList) {
                if (Collider.isColliding(tube.collider, syringe.collider)) {
                    //Actualize active tube test number
                    activateTestTube++;

                    //Each tube works only once
                    tube.collider.setInactive();

                    //Syringe get fixed to test tube
                    syringe.setPosition(tube.collider.getPositionX() + syringe.getSpriteWidth()/2, tube.collider.getPositionY() - syringe.getSpriteHeight()/2);

                    //Start animation on syringe
                    syringe.startAnimation();
                    fxPlayer.playSyringeDropSound();

                    //Start animation on test tube
                    tube.setDelay(syringe.getAnimationTime());
                    tube.startAnimation();

                    //Test tube sound
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            fxPlayer.playWaterDropSound();
                        }
                    };
                    tube.playSoundInAnimation(r, 6);

                }

                tube.update(System.currentTimeMillis());
            }

            //If all TestTubes were activated show buttons to choose BloodType
            if (activateTestTube == MAX_TEST_TUBE_AMOUNT) {

                //Create BloodGroupButtons
                if (BloodGroupButtonList.size() < MAX_BLOOD_GROUP_BUTTON_AMOUNT) {
                    BloodGroupButtonList.add(new BloodGroupButton(context, BloodGroupButton.bloodGroups[BloodGroupButtonList.size()], firstBloodGroupButtonX, BloodGroupButtonY, 0.4f));
                    firstBloodGroupButtonX += 200;
                }

                //Create RhButtons only on active Blood Group Button
                for (BloodGroupButton btn : BloodGroupButtonList) {
                    if (btn.isActive()) {
                        while (RhButtonList.size() < MAX_RH_BUTTON_AMOUNT) {
                            if (RhButtonList.isEmpty()) {
                                RhButtonList.add(new RhButton(context, RhButton.rh[RhButtonList.size()], btn.getPositionX()-GroupButtonWidth, RhButtonY, 0.3f));
                            } else RhButtonList.add(new RhButton(context, RhButton.rh[RhButtonList.size()], btn.getPositionX()-GroupButtonWidth+rhButtonShift, RhButtonY, 0.3f));

                        }
                    }
                }

            }
            /**
             * UPDATES FOR STAGE 3
             *
             **/
        } else if (currentStage == 2) {
            BloodGroupButtonList.clear();
            RhButtonList.clear();
            testTubeList.clear();

            //Create Blood Bags
            if (bloodBagList.size() < MAX_BLOOD_BAG_AMOUNT) {
                if (bloodBagList.size() == MAX_BLOOD_BAG_AMOUNT/2) {
                    firstBloodBagX = mWidth/2 - 300;
                    bloodBagY = mHeight/2 + 200;
                }
                bloodBagList.add(new BloodBag(context, BloodBag.bloodBag[bloodBagList.size()], firstBloodBagX, bloodBagY, 0.4f));
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
                        bag.setPosition(bloodRack.collider.getPositionX() + bag.getWidth()/2, bloodRack.collider.getPositionY() - bag.getHeight()/2);
                        bag.setDoNotDraw();
                        bag.collider.setInactive();
                        choiceMessage = new ChoiceMessage(true, context, mWidth, mHeight);
                        isChoiceMessageClosed = false;

                        fxPlayer.playGoodAnswerSound();
                        fxPlayer.playBloodDropSound();

                        numberOfDonors--;
                    } else {
                        bag.changeTexture(bag.getDrawingIndex(), context, 0.4f);
                        bag.collider.setInactive();
                        bag.setMovable(0);
                        bag.setOriginalPosition();
                        choiceMessage = new ChoiceMessage(false, context, mWidth, mHeight);
                        isChoiceMessageClosed = false;

                        fxPlayer.playWrongAnswerSound();

                        numberOfMistakes++;
                    }
                }
            }
            //Update state of BloodRack
            bloodRack.update();

            //GAME OVER
            if (numberOfMistakes == MAX_NUMBER_OF_MISTAKES) {
                gameResult = false;
                Score.addGoldStars(0);
                Score.setGrantedStars();
                sceneManager.drawGameOverScene();
            }

            //GAME WON
            if (numberOfDonors == 0) {
                gameResult = true;
                Score.addGoldStars(MAX_NUMBER_OF_MISTAKES - numberOfMistakes + (mistakeMade ? 0 : 1) + (secondMistake ? 0 : 1));
                Score.setGrantedStars();
                sceneManager.drawGameOverScene();
            }

        }
    }

    public void clickEvents(double x, double y) {

        if (Sprite.isClicked(menuButton, x, y)) {
            fxPlayer.playButtonClickSound();
            sceneManager.drawMenuScene();
        }

        if (Sprite.isClicked(infoButton, x, y)) {
            fxPlayer.playButtonClickSound();
            sceneManager.drawInfoScene();
        }

        //Move syringe
        if (SpriteSheet.isClicked(syringe, x, y)) {
            syringe.setPosition(x, y);
        }

        //Move blood bag
        for (BloodBag bag : bloodBagList) {
            if (Sprite.isClicked(bag, x, y) && bag.isMovable()) {
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
        if (stageCleared && Sprite.isClicked(nextButton, x, y)) {

            //Next stage sound
            fxPlayer.playNextStageSound();

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
            if (Sprite.isClicked(btn, x, y)) {

                fxPlayer.playButtonClickSound();

                RhButtonList.clear();
                btn.setActive();
                currentBloodGroup = btn.getBloodGroup();
            } else btn.setInactive();
        }

        //Click on one of RhButtons
        for (RhButton btn : RhButtonList) {
            if (Sprite.isClicked(btn, x, y)) {

                fxPlayer.playButtonClickSound();

                if (currentBloodGroup == bloodType.getBloodGroup() && btn.getRhType() == bloodType.getRhType()) {
                    choiceMessage = new ChoiceMessage(true, context, mWidth, mHeight);
                    isChoiceMessageClosed = false;
                    fxPlayer.playGoodAnswerSound();
                    stageCleared = true;
                } else {
                    choiceMessage = new ChoiceMessage(false, context, mWidth, mHeight);
                    isChoiceMessageClosed = false;
                    fxPlayer.playWrongAnswerSound();
                    if (mistakeMade) secondMistake = true;
                    mistakeMade = true;
                    for (BloodGroupButton BGB : BloodGroupButtonList) {
                        BGB.setInactive();
                    }
                }
            }
        }

    }

    public void moveEvents(double x, double y) {
        //Move syringe
        if (SpriteSheet.isClicked(syringe, x, y) && !syringe.inAnimation()) {
            syringe.setPosition(x, y);
        }

        //Move blood bag
        for (BloodBag bag : bloodBagList) {
            if (bag.isActive() && Sprite.isClicked(bag, x, y)) {
                bag.setPosition(x, y);
            }
        }

    }

    public void upEvents(double x, double y) {
        //After releasing one of Blood Bags
        for (BloodBag bag : bloodBagList) {
            bag.setOriginalPosition();
            bag.setInactive();
        }
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public boolean getGameResult() {
        return gameResult;
    }

    public boolean isChoiceMessageClosed() {return isChoiceMessageClosed;}

    public void choiceMessageClickEvents(double x, double y) {
        try {
            if (Sprite.isClicked(choiceMessage, x, y)) {
                choiceMessage = null;
                isChoiceMessageClosed = true;
            }
        } catch (Exception e){}
    }

    public int getNumberOfMistakes() {
        return MAX_NUMBER_OF_MISTAKES-numberOfMistakes;
    }

    public int getNumberOfDonors() {
        return numberOfDonors;
    }

    public BloodType getRecipientBloodType() {
        return bloodType;
    }

    public List<TestTube> getTestTubeList() {return testTubeList;}

}
