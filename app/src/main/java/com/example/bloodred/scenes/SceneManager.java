package com.example.bloodred.scenes;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import com.example.bloodred.FXPlayer;
import com.example.bloodred.R;
import com.example.bloodred.Score;
/**
 * SceneManager is an class which only purpose is to manage scenes due to user decisions
 *
 **/

public class SceneManager {

    //Game sounds
    private final FXPlayer fXPlayer;

    //All scenes in game
    private GameOverScene gameOverScene;
    private MenuScene menuScene;
    private GameScene gameScene;
    private InfoScene infoScene;

    //User screen sizes
    protected final int mWidth;
    protected final int mHeight;
    private final Context context;

    public SceneManager(Context context, int mWidth, int mHeight) {
        this.context = context;
        this.mWidth = mWidth;
        this.mHeight = mHeight;

        //Create all scenes and sound player
        fXPlayer = new FXPlayer(context);
        infoScene = new InfoScene(BitmapFactory.decodeResource(context.getResources(), R.drawable.menubg), context, mWidth, mHeight, this, fXPlayer);
        gameOverScene = new GameOverScene(BitmapFactory.decodeResource(context.getResources(), R.drawable.menubg), context, mWidth, mHeight, this, fXPlayer);
        menuScene = new MenuScene(BitmapFactory.decodeResource(context.getResources(), R.drawable.menubg), context, mWidth, mHeight, this, fXPlayer);
        gameScene = new GameScene(BitmapFactory.decodeResource(context.getResources(), R.drawable.bg), context, mWidth, mHeight, this, fXPlayer);

        //GameScene is first scene that is shown after starting the game
        gameScene.setActive();
    }

    //Scene is drawn on canvas only if active
    public void draw(Canvas canvas) {

        if (menuScene.isActive()) {
            menuScene.drawScene(canvas);
        }

        if (gameScene.isActive()) {
            gameScene.drawScene(canvas);
        }

        if (infoScene.isActive()) {
            infoScene.drawScene(canvas);
        }

        if (gameOverScene.isActive()) {
            gameOverScene.drawScene(canvas);
        }

    }


    //Decision which scene is drawn by changing active status. It is not the best solution but for small number
    //of scenes it is good. Change of this solution is needed if the game grows bigger.
    public void drawInfoScene() {
        infoScene.setActive();
        gameScene.setInactive();
        menuScene.setInactive();
        gameOverScene.setInactive();
    }

    public void drawGameScene() {
        gameScene.setActive();
        menuScene.setInactive();
        infoScene.setInactive();
        gameOverScene.setInactive();
    }

    public void drawMenuScene() {
        menuScene.setActive();
        gameScene.setInactive();
        infoScene.setInactive();
        gameOverScene.setInactive();
    }

    public void drawGameOverScene() {
        gameOverScene.setActive();
        menuScene.setInactive();
        gameScene.setInactive();
        infoScene.setInactive();
    }

    //Touch screen events are only working on active scene
    public void infoSceneClickEvents(double x, double y) {
        if (infoScene.isActive()) {
            infoScene.clickEvents(x, y);
        }
    }

    public void menuSceneClickEvents(double x, double y) {
        if (menuScene.isActive()) {
            menuScene.clickEvents(x, y);
        }
    }

    public void gameOverSceneClickEvents(double x, double y) {
        if (gameOverScene.isActive()) {
            gameOverScene.clickEvents(x, y);
        }
    }

    public void gameSceneClickEvents(double x, double y) {
        if (gameScene.isActive() && gameScene.isChoiceMessageClosed()) {
            gameScene.clickEvents(x, y);
        } else gameScene.choiceMessageClickEvents(x, y);
    }

    public void gameSceneMoveEvents(double x, double y) {
        if (gameScene.isActive()) {
            gameScene.moveEvents(x, y);
        }
    }
    public void gameSceneUpEvents(double x, double y) {
        if (gameScene.isActive()) {
            gameScene.upEvents(x, y);
        }
    }

    //Update all scenes
    public void update() {
        gameScene.update();
        infoScene.update();
        menuScene.update();
        gameOverScene.update();
    }

    //Return user screen sizes
    public int getmWidth() {return mWidth;}

    public int getmHeight() {return mHeight;}

    //Return current game stange
    public int getCurrentGameStage() {
        return gameScene.getCurrentStage();
    }

    //Return game result
    public boolean getGameResult() {return gameScene.getGameResult();}

    //If game restarted recreate all scenes and reset score
    public void restartGame() {
        menuScene = new MenuScene(BitmapFactory.decodeResource(context.getResources(), R.drawable.menubg), context, mWidth, mHeight, this, fXPlayer);
        gameScene = new GameScene(BitmapFactory.decodeResource(context.getResources(), R.drawable.bg), context, mWidth, mHeight, this, fXPlayer);
        infoScene = new InfoScene(BitmapFactory.decodeResource(context.getResources(), R.drawable.menubg), context, mWidth, mHeight, this, fXPlayer);
        gameOverScene = new GameOverScene(BitmapFactory.decodeResource(context.getResources(), R.drawable.menubg), context, mWidth, mHeight, this, fXPlayer);
        gameScene.setActive();
        Score.resetScore();
    }
}
