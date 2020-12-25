package com.example.bloodred;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.bloodred.gameobject.Sprite;
import com.example.bloodred.gamepanel.ExitButton;
import com.example.bloodred.gamepanel.RestartButton;
import com.example.bloodred.gamepanel.ResumeButton;

public class MenuScene {

    private final ResumeButton resumeButton;
    private final RestartButton restartButton;
    private final ExitButton exitButton;
    private final SceneManager sceneManager;
    private Bitmap bitmap;
    private boolean active = false;

    public MenuScene(Bitmap res, Context context, int mWidth, int mHeight, SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        bitmap = res;
        resumeButton = new ResumeButton(context, mWidth/1.5, mHeight/2, 0.4f);
        restartButton = new RestartButton(context, mWidth/2, mHeight/2, 0.4f);
        exitButton = new ExitButton(context, mWidth/3, mHeight/2, 0.4f);
    }


    public void draw(Canvas canvas) {
        Rect dstRect = new Rect();
        canvas.getClipBounds(dstRect);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        resumeButton.draw(canvas);
        restartButton.draw(canvas);
        exitButton.draw(canvas);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive() {
        active = true;
    }

    public void setInactive() {
        active = false;
    }

    public void clickEvents(double x, double y) {
        
        if (Sprite.isClicked(restartButton, x, y)) {
            sceneManager.restartGame();
        }

        if (Sprite.isClicked(resumeButton, x, y)) {
            sceneManager.drawGameScene();
        }

        if (Sprite.isClicked(exitButton, x, y)) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }

    }

}
