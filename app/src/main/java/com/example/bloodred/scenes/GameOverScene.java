package com.example.bloodred.scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.bloodred.FXPlayer;
import com.example.bloodred.Score;
import com.example.bloodred.gamebackground.Background;
import com.example.bloodred.gameobject.ScoreStar;
import com.example.bloodred.gameobject.Sprite;
import com.example.bloodred.gamepanel.ExitButton;
import com.example.bloodred.gamepanel.RestartButton;
import com.example.bloodred.gamepanel.StatusText;

import java.util.ArrayList;
import java.util.List;
/**
 * GameOverScene is a scene that is shown after losing or winning the game
 * GameOverScene class in an extension of a ScenePrototype
 **/
public class GameOverScene extends ScenePrototype {

    //UI elements
    private final RestartButton restartButton;
    private final ExitButton exitButton;

    //Score
    private final List<ScoreStar> scoreStarList = new ArrayList<ScoreStar>();
    private final StatusText statusText;
    private int firstScoreStarX;
    private final int scoreStarY;
    private final int spaceBetweenStars = 50;
    private int starAnimationDelay = 0;
    private ScoreStar lastStar;

    //Sound
    private final FXPlayer fxPlayer;

    public GameOverScene(Bitmap res, Context context, int mWidth, int mHeight, SceneManager sceneManager, FXPlayer fxPlayer) {
        super(res, context, mWidth, mHeight, sceneManager);

        restartButton = new RestartButton(context, (float)mWidth/2 - 200,mHeight - 150, 0.4f);
        exitButton = new ExitButton(context, (float)mWidth/2 + 200, mHeight - 150, 0.6f);
        statusText = new StatusText(mWidth, mHeight, this);

        this.scoreStarY = sceneManager.getmHeight()/4;
        this.firstScoreStarX = sceneManager.getmWidth()/2 - (int)(res.getWidth()/10*2.5) + spaceBetweenStars* Score.getStarAmount();
        this.fxPlayer = fxPlayer;
    }

    public void drawScene(Canvas canvas) {
        background = new Background(bitmap);
        background.draw(canvas);
        restartButton.draw(canvas);
        exitButton.draw(canvas);
        statusText.draw(canvas);

        for (ScoreStar star : scoreStarList) {
            star.draw(canvas);
        }
    }

    public void update(){
        //Create ScoreStars
        if (this.isActive()) {


            if (scoreStarList.size() < Score.getStarAmount()) {
                scoreStarList.add(new ScoreStar(context, firstScoreStarX, scoreStarY, 16, 10, 1, false, Score.isStarGranted(scoreStarList.size())));
                firstScoreStarX += scoreStarList.get(0).getSpriteWidth() + spaceBetweenStars;
                lastStar = scoreStarList.get(0);
            }

            //Update Score Stars
            for (ScoreStar star : scoreStarList) {

                //Play stars sounds
                Runnable r;
                if (star.getStarColor()) {
                    r = new Runnable() {
                        @Override
                        public void run() {
                            fxPlayer.playGoldStarSound();
                        }
                    };
                }
                else {
                    r = new Runnable() {
                        @Override
                        public void run() {
                            fxPlayer.playGrayStarSound();
                        }
                    };
                }
                star.playSoundInAnimation(r, 6);

                //STAR ANIMATIONS
                if (star.getCurrentFrame() == 0) {

                    if (lastStar.getCurrentFrame() == lastStar.getFrameNr()-1) {
                        lastStar = star;
                    }

                    star.setDelay(starAnimationDelay);
                    star.startAnimation();
                    starAnimationDelay += star.getAnimationTime();
                }
                //NEXT STAR IS UPDATED ONLY IF ANIMATION OF LAST STAR HAS FINISHED
                if (star.equals(lastStar)) {
                    star.update(System.currentTimeMillis());
                }
            }
        }
    }

    public void clickEvents(double x, double y) {

        if (Sprite.isClicked(restartButton, x, y)) {
            fxPlayer.playButtonClickSound();
            sceneManager.restartGame();
        }

        if (Sprite.isClicked(exitButton, x, y)) {
            fxPlayer.playButtonClickSound();
            android.os.Process.killProcess(android.os.Process.myPid());
        }

    }

    public boolean getGameResult() {
        return sceneManager.getGameResult();
    }
}
