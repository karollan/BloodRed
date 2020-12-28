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
    private final GameScene gameScene;

    public StatusText(int mWidth, int mHeight, GameScene gameScene) {
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        this.gameScene = gameScene;
    }

    public void draw(Canvas canvas) {
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
