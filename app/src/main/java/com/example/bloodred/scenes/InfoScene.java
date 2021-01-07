package com.example.bloodred.scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.bloodred.FXPlayer;
import com.example.bloodred.R;
import com.example.bloodred.gamebackground.Background;
import com.example.bloodred.gameobject.Sprite;
import com.example.bloodred.gamepanel.NextButton;
import com.example.bloodred.gamepanel.PreviousButton;
import com.example.bloodred.gamepanel.ResumeButton;
/**
 * InfoScene is a scene that contains informations of current stage
 * InfoScene class in an extension of a ScenePrototype
 **/
public class InfoScene extends ScenePrototype{

    //Sounds
    private final FXPlayer fxPlayer;

    //UI
    private final NextButton nextButton;
    private final PreviousButton previousButton;
    private final ResumeButton resumeButton;

    //Page information
    private int currentPage = 0;
    private int lastPage;

    public InfoScene(Bitmap res, Context context, int mWidth, int mHeight, SceneManager sceneManager, FXPlayer fxPlayer) {
        super(res, context, mWidth, mHeight, sceneManager);

        resumeButton = new ResumeButton(context, mWidth/2,  mHeight - 120, 0.13f);
        nextButton = new NextButton(context, mWidth - 120, mHeight - 120, 0.3f);
        previousButton = new PreviousButton(context, 120, mHeight-120, 0.3f);

        this.fxPlayer = fxPlayer;
    }

    public void drawScene(Canvas canvas) {
        String text;
        background = new Background(bitmap);
        background.draw(canvas);
        resumeButton.draw(canvas);
        //Draw different text for every stage
        switch (sceneManager.getCurrentGameStage()) {
            case 0:
                lastPage = 1;
                switch (currentPage) {
                    case 0:
                        text = context.getResources().getString(R.string.Stage1);
                        bitmap = drawMultilineTextToBitmap(context, R.drawable.menubg, text);
                        break;
                    case 1:
                        text = context.getResources().getString(R.string.Stage1_1);
                        bitmap = drawMultilineTextToBitmap(context, R.drawable.menubg, text);
                        break;
                }
                break;

            case 1:
                lastPage = 4;
                switch (currentPage) {
                    case 0:
                        text = context.getResources().getString(R.string.Stage2);
                        bitmap = drawMultilineTextToBitmap(context, R.drawable.menubg, text);
                        break;
                    case 1:
                        text = context.getResources().getString(R.string.Stage2_1);
                        bitmap = drawMultilineTextToBitmap(context, R.drawable.menubg, text);
                        break;
                    case 2:
                        text = context.getResources().getString(R.string.Stage2_2);
                        bitmap = drawMultilineTextToBitmap(context, R.drawable.menubg, text);
                        break;
                    case 3:
                        text = context.getResources().getString(R.string.Stage2_3);
                        bitmap = drawMultilineTextToBitmap(context, R.drawable.menubg, text);
                        break;
                    case 4:
                        text = context.getResources().getString(R.string.Stage2_4);
                        bitmap = drawMultilineTextToBitmap(context, R.drawable.menubg, text);
                        break;
                }
                break;

            case 2:
                lastPage = 5;
                switch (currentPage) {
                    case 0:
                        text = context.getResources().getString(R.string.Stage3);
                        bitmap = drawMultilineTextToBitmap(context, R.drawable.menubg, text);
                        break;
                    case 1:
                        text = context.getResources().getString(R.string.Stage3_1);
                        bitmap = drawMultilineTextToBitmap(context, R.drawable.menubg, text);
                        break;
                    case 2:
                        text = context.getResources().getString(R.string.Stage3_2);
                        bitmap = drawMultilineTextToBitmap(context, R.drawable.menubg, text);
                        break;
                    case 3:
                        text = context.getResources().getString(R.string.Stage3_3);
                        bitmap = drawMultilineTextToBitmap(context, R.drawable.menubg, text);
                        break;
                    case 4:
                        text = context.getResources().getString(R.string.Stage3_4);
                        bitmap = drawMultilineTextToBitmap(context, R.drawable.menubg, text);
                        break;
                    case 5:
                        text = context.getResources().getString(R.string.Stage3_5);
                        bitmap = drawMultilineTextToBitmap(context, R.drawable.menubg, text);
                        break;
                }
                break;
        }
        if (currentPage < lastPage) {
            nextButton.draw(canvas);
        }
        if (currentPage > 0) previousButton.draw(canvas);
    }

    public void update(){};

    public void clickEvents(double x, double y) {
        if (Sprite.isClicked(resumeButton, x, y)) {
            currentPage = 0;
            fxPlayer.playButtonClickSound();
            sceneManager.drawGameScene();
        }
        if (Sprite.isClicked(nextButton, x, y) && currentPage < lastPage) {
            fxPlayer.playButtonClickSound();
            currentPage++;
        }
        if (Sprite.isClicked(previousButton, x, y) && currentPage > 0) {
            fxPlayer.playButtonClickSound();
            currentPage--;
        }
    }


}
