package com.example.bloodred;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.bloodred.gameobject.TestTube;
import com.example.bloodred.gamepanel.BloodType;

import java.util.List;

public class StatusText {

    private final int mWidth;
    private final int mHeight;
    private final ScenePrototype scenePrototype;

    public StatusText(int mWidth, int mHeight, ScenePrototype scene) {
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        this.scenePrototype = scene;
    }

    public void draw(Canvas canvas) {

        if (scenePrototype instanceof GameScene) {
            GameScene gameScene = (GameScene)scenePrototype; 
            switch (gameScene.getCurrentStage()) {
                case 1:
                    drawTestTubeNames(canvas, gameScene.getTestTubeList());
                    break;
                case 2:
                    drawNumberOfMistakes(canvas, gameScene.getNumberOfMistakes());
                    drawNumberOfDonors(canvas, gameScene.getNumberOfDonors());
                    drawRecipientBloodType(canvas, gameScene.getRecipientBloodType());
                    break;
            }
        } else if (scenePrototype instanceof GameOverScene) {
            GameOverScene gameOverScene = (GameOverScene)scenePrototype;
            if (gameOverScene.isActive()) {
                drawGameOverText(canvas, gameOverScene.getGameResult(), Score.getGoldStarsAmount());
            }
        }

    }

    private void drawGameOverText(Canvas canvas, boolean gameResult, int goldStars) {
        String text = "";
        String score = "Twój wynik!";
        if (gameResult) {
            if (goldStars == 5) {
                text = "Gratuluję! Pacjent został uratowany!";
            } else if (goldStars < 5 && goldStars >= 3) {
                text = "Pacjent przeżył, ale jest miejsce na poprawę!";
            } else if (goldStars < 3) {
                text = "Pacjent jest na granicy życia i śmierci! Za dużo pomyłek!";
            }
        } else {
            text = "Pacjent nie żyje! Następnym razem przeczytaj dostępne informacje.";
        }

        Paint fillPaint = getFillPaint(80f, true, Color.YELLOW);
        Paint strokePaint = getStrokePaint(80f, true, Color.BLACK, 2f);

        canvas.drawText( score,  (float)mWidth/2 - fillPaint.measureText(score)/2,  120, fillPaint);
        canvas.drawText( score,  (float)mWidth/2 - strokePaint.measureText(score)/2,  120, strokePaint);

        fillPaint.setTextSize(55f);
        strokePaint.setTextSize(55f);
        canvas.drawText( text,  (float)mWidth/2 - fillPaint.measureText(text)/2,  (float)mHeight/2, fillPaint);
        canvas.drawText( text,  (float)mWidth/2 - strokePaint.measureText(text)/2,  (float)mHeight/2, strokePaint);

    }


    private void drawTestTubeNames(Canvas canvas, List<TestTube> list) {

        for (TestTube tube : list) {
            String text = tube.getTestTubeType().toString().contains("Plus") ? "Rh" : tube.getTestTubeType().toString();
            Paint fillPaint = getFillPaint(50f, true, Color.BLACK);
            canvas.drawText( text,  (float)tube.getPositionX() - fillPaint.measureText(text)/2,  (float)tube.getPositionY(), fillPaint);

            Paint strokePaint = getStrokePaint(50f, true, Color.BLACK, 2f);
            canvas.drawText( text,  (float)tube.getPositionX() - strokePaint.measureText(text)/2,  (float)tube.getPositionY(), strokePaint);
        }

    }

    private void drawRecipientBloodType(Canvas canvas, BloodType recipientBloodType) {
        String blood = "Grupa krwi pacjenta: " + (recipientBloodType.getBloodGroup().toString().equals("Zero") ? "0" : recipientBloodType.getBloodGroup().toString()) + " " + recipientBloodType.getRhTypeString();
        Paint fillPaint = getFillPaint(50f, true, Color.RED);
        canvas.drawText( blood,  mWidth/2 - fillPaint.measureText(blood)/2,  100, fillPaint);

        Paint strokePaint = getStrokePaint(50f, true, Color.RED, 2f);
        canvas.drawText( blood,  mWidth/2 - strokePaint.measureText(blood)/2,  100, strokePaint);
    }

    private void drawNumberOfDonors(Canvas canvas, int numberOfDonors) {
        String donors = "Ilość dobrych worków: " + numberOfDonors;
        Paint fillPaint = getFillPaint(50f, true, Color.WHITE);
        canvas.drawText(  donors,  mWidth/2 - fillPaint.measureText(donors)/2,  mHeight-50, fillPaint);

        Paint strokePaint = getStrokePaint(50f, true, Color.GREEN, 2f);
        canvas.drawText(  donors,  mWidth/2 - strokePaint.measureText(donors)/2,  mHeight-50, strokePaint);
    }

    private void drawNumberOfMistakes(Canvas canvas, int numberOfMistakes) {
        String mistakes = "Ilość żyć: " + numberOfMistakes;
        Paint fillPaint = getFillPaint(50f, true, Color.WHITE);
        canvas.drawText( mistakes,  mWidth-300,  mHeight-50, fillPaint);

        Paint strokePaint = getStrokePaint(50f, true, Color.RED, 2f);
        canvas.drawText( mistakes,  mWidth-300,  mHeight-50, strokePaint);
    }

    private Paint getFillPaint(float textSize, boolean antiAlias, int color) {
        Paint fillPaint = new Paint();
        fillPaint.setAntiAlias(antiAlias);
        fillPaint.setColor(color);
        fillPaint.setTextSize(textSize);
        return fillPaint;
    }

    private Paint getStrokePaint(float textSize, boolean antiAlias, int color, float strokeWidth) {
        Paint strokePaint = new Paint();
        strokePaint.setAntiAlias(antiAlias);
        strokePaint.setTextSize(textSize);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(strokeWidth);
        strokePaint.setColor(color);
        return strokePaint;
    }
}
