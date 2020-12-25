package com.example.bloodred;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


public class SceneManager {
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
        gameScene = new GameScene(context, mWidth, mHeight, this);
        infoScene = new InfoScene(BitmapFactory.decodeResource(context.getResources(), R.drawable.menubg), context, mWidth, mHeight, this);
    }

    public void draw(Canvas canvas) {

        if (menuScene.isActive()) {
            menuScene.draw(canvas);
        }

        if (gameScene.isActive()) {
            gameScene.draw(canvas);
        }

        if (infoScene.isActive()) {
            infoScene.draw(canvas);
        }

    }

    //Tymczasowe rozwiązanie
    public void drawInfoScene() {
        infoScene.setActive();
        gameScene.setInactive();
        menuScene.setInactive();
    }

    public void drawGameScene() {
        menuScene.setInactive();
        gameScene.setActive();
        infoScene.setInactive();
    }

    public void drawMenuScene() {
        gameScene.setInactive();
        menuScene.setActive();
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

    public void gameSceneClickEvents(double x, double y) {
        if (gameScene.isActive()) {
            gameScene.clickEvents(x, y);
        }
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

    public void restartGame() {
        menuScene = new MenuScene(BitmapFactory.decodeResource(context.getResources(), R.drawable.menubg), context, mWidth, mHeight, this);
        gameScene = new GameScene(context, mWidth, mHeight, this);
        infoScene = new InfoScene(BitmapFactory.decodeResource(context.getResources(), R.drawable.menubg), context, mWidth, mHeight, this);
    }
}
