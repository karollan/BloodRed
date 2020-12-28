package com.example.bloodred;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;


public class SceneManager {
    private GameOverScene gameOverScene;
    private MenuScene menuScene;
    protected final int mWidth;
    protected final int mHeight;
    private final Context context;
    private GameScene gameScene;
    private InfoScene infoScene;


    public SceneManager(Context context, int mWidth, int mHeight) {
        this.context = context;
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        menuScene = new MenuScene(BitmapFactory.decodeResource(context.getResources(), R.drawable.menubg), context, mWidth, mHeight, this);
        gameScene = new GameScene(BitmapFactory.decodeResource(context.getResources(), R.drawable.bg), context, mWidth, mHeight, this);
        infoScene = new InfoScene(BitmapFactory.decodeResource(context.getResources(), R.drawable.menubg), context, mWidth, mHeight, this);
        gameOverScene = new GameOverScene(BitmapFactory.decodeResource(context.getResources(), R.drawable.menubg), context, mWidth, mHeight, this);
        gameScene.setActive();
    }

    public void draw(Canvas canvas) {

        if (menuScene.isActive()) {
            menuScene.drawScene(canvas);
        }

        if (gameScene.isActive()) {
            gameScene.drawScene(canvas);
        }

        if (infoScene.isActive()) {
            Log.d("klikanie", "aktywnosc");
            infoScene.drawScene(canvas);
        }

        if (gameOverScene.isActive()) {
            gameOverScene.drawScene(canvas);
        }

    }

    //Tymczasowe rozwiązanie
    public void drawInfoScene() {
        Log.d("klikanie", "aktywnosc");
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

    public void update() {
        gameScene.update();
    }


    public int getCurrentGameStage() {
        return gameScene.getCurrentStage();
    }

    public boolean getGameResult() {return gameScene.getGameResult();}

    public void restartGame() {
        menuScene = new MenuScene(BitmapFactory.decodeResource(context.getResources(), R.drawable.menubg), context, mWidth, mHeight, this);
        gameScene = new GameScene(BitmapFactory.decodeResource(context.getResources(), R.drawable.bg), context, mWidth, mHeight, this);
        infoScene = new InfoScene(BitmapFactory.decodeResource(context.getResources(), R.drawable.menubg), context, mWidth, mHeight, this);
        gameOverScene = new GameOverScene(BitmapFactory.decodeResource(context.getResources(), R.drawable.menubg), context, mWidth, mHeight, this);
        gameScene.setActive();
    }
}
