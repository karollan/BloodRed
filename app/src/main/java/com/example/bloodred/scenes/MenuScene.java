package com.example.bloodred.scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.bloodred.FXPlayer;
import com.example.bloodred.R;
import com.example.bloodred.gameobject.Sprite;
import com.example.bloodred.gamepanel.ExitButton;
import com.example.bloodred.gamepanel.RestartButton;
import com.example.bloodred.gamepanel.ResumeButton;
import com.example.bloodred.gamepanel.SoundButton;
/**
 * MenuScene is a scene that contains sound settings, and game restart, exit and resume buttons
 * GameOverScene class in an extension of a ScenePrototype
 **/
public class MenuScene extends ScenePrototype {


    private final FXPlayer fxPlayer;
    private final ResumeButton resumeButton;
    private final RestartButton restartButton;
    private final ExitButton exitButton;
    private final SoundButton soundButton;

    public MenuScene(Bitmap res, Context context, int mWidth, int mHeight, SceneManager sceneManager, FXPlayer fxPlayer) {
        super(res, context, mWidth, mHeight, sceneManager);

        //Create all UI
        resumeButton = new ResumeButton(context, mWidth/2+300, mHeight/2, 0.15f);
        restartButton = new RestartButton(context, mWidth/2, mHeight/2, 0.4f);
        exitButton = new ExitButton(context, mWidth/3, mHeight/2, 0.6f);
        soundButton = new SoundButton(context, 200, mHeight - 200, 0.3f);
        this.fxPlayer = fxPlayer;
    }

    public void drawScene(Canvas canvas) {
        background.draw(canvas);
        resumeButton.draw(canvas);
        restartButton.draw(canvas);
        exitButton.draw(canvas);
        soundButton.draw(canvas);
    }

    public void update(){}

    //Menu scene contains only buttons, simple click events and sounds
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

        //Sound button have two states, sound off and on
        if (Sprite.isClicked(soundButton, x, y)) {
            soundButton.changeState();
            soundButton.changeTexture(soundButton.getState() ? R.drawable.soundoff : R.drawable.sound, context, 0.3f);
            fxPlayer.changeState(soundButton.getState());
        }

    }

}
