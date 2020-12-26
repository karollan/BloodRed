package com.example.bloodred;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.bloodred.gamebackground.Background;
import com.example.bloodred.gameobject.Sprite;
import com.example.bloodred.gamepanel.ExitButton;
import com.example.bloodred.gamepanel.RestartButton;

public class GameOverScene extends ScenePrototype {
    private final RestartButton restartButton;
    private final ExitButton exitButton;

    public GameOverScene(Bitmap res, Context context, int mWidth, int mHeight, SceneManager sceneManager) {
        super(res, context, mWidth, mHeight, sceneManager);

        restartButton = new RestartButton(context, mWidth/2, mHeight/2, 0.4f);
        exitButton = new ExitButton(context, mWidth/2, mHeight/2, 0.4f);

    }

    public void drawScene(Canvas canvas) {
        String text;
        background = new Background(bitmap);
        background.draw(canvas);
        restartButton.draw(canvas);
        exitButton.draw(canvas);

        if (sceneManager.getGameResult()) {
            text = context.getResources().getString(R.string.Victory);
        }
        else {
            text = context.getResources().getString(R.string.Failure);
        }
        bitmap = drawMultilineTextToBitmap(context, R.drawable.menubg, text);
    }

    public void update(){};

    public void clickEvents(double x, double y) {

        if (Sprite.isClicked(restartButton, x, y)) {
            sceneManager.restartGame();
        }

        if (Sprite.isClicked(exitButton, x, y)) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }

    }

}
