package com.example.bloodred;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.bloodred.gamebackground.Background;
import com.example.bloodred.gameobject.Sprite;
import com.example.bloodred.gamepanel.ExitButton;
import com.example.bloodred.gamepanel.RestartButton;
import com.example.bloodred.gamepanel.ResumeButton;

public class MenuScene extends ScenePrototype {

    private final FXPlayer fxPlayer;
    private final ResumeButton resumeButton;
    private final RestartButton restartButton;
    private final ExitButton exitButton;

    public MenuScene(Bitmap res, Context context, int mWidth, int mHeight, SceneManager sceneManager, FXPlayer fxPlayer) {
        super(res, context, mWidth, mHeight, sceneManager);
        resumeButton = new ResumeButton(context, mWidth-200, 200, 0.3f);
        restartButton = new RestartButton(context, mWidth/2, mHeight/2, 0.4f);
        exitButton = new ExitButton(context, mWidth/3, mHeight/2, 0.4f);
        this.fxPlayer = fxPlayer;
    }

    public void drawScene(Canvas canvas) {
        background.draw(canvas);
        resumeButton.draw(canvas);
        restartButton.draw(canvas);
        exitButton.draw(canvas);
    }

    public void update(){}

    public void clickEvents(double x, double y) {
        
        if (Sprite.isClicked(restartButton, x, y)) {
            fxPlayer.playButtonClickSound();
            sceneManager.restartGame();
        }

        if (Sprite.isClicked(resumeButton, x, y)) {
            fxPlayer.playButtonClickSound();
            sceneManager.drawGameScene();
        }

        if (Sprite.isClicked(exitButton, x, y)) {
            fxPlayer.playButtonClickSound();
            android.os.Process.killProcess(android.os.Process.myPid());
        }

    }

}
