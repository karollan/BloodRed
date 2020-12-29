package com.example.bloodred;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class FXPlayer {

    private static SoundPool soundPool;
    private static int syringeSuckSound;
    private static int syringeDropSound;
    private static int goldStarSound;
    private static int grayStarSound;
    private static int waterDropSound;
    private static int buttonClickSound;
    private static int nextStageSound;
    private int delay;

    public FXPlayer(Context context) {
        soundPool = new SoundPool(7, AudioManager.STREAM_MUSIC, 0);

        syringeSuckSound = soundPool.load(context, R.raw.syringesuckbloodshort, 1);
        syringeDropSound = soundPool.load(context, R.raw.syrignedroplet, 1);
        goldStarSound = soundPool.load(context, R.raw.goldstar, 1);
        grayStarSound = soundPool.load(context, R.raw.stargray, 1);
        waterDropSound = soundPool.load(context, R.raw.waterdrop, 1);
        buttonClickSound = soundPool.load(context, R.raw.buttonclick, 1);
        nextStageSound = soundPool.load(context, R.raw.nextstagebutton, 1);

        this.delay = 0;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    //	play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)

    public void playSyringeSuckSound() {
        soundPool.play(syringeSuckSound, 1f, 1f, 1, 0, 1f);
    }

    public void playSyringeDropSound() {
        soundPool.play(syringeDropSound, 1f, 1f, 1, 0, 1f);
    }

    public void playGoldStarSound() {
        soundPool.play(goldStarSound, 1f, 1f, 1, 0, 1f);
    }

    public void playGrayStarSound() {
        soundPool.play(grayStarSound, 1f, 1f, 1, 0, 1f);
    }

    public void playWaterDropSound() {
        soundPool.play(waterDropSound, 1f, 1f, 1, 0, 1f);
    }

    public void playButtonClickSound() {
        soundPool.play(buttonClickSound, 1f, 1f, 1, 0, 1f);
    }

    public void playNextStageSound() {
        soundPool.play(nextStageSound, 1f, 1f, 1, 0, 1f);
    }

}
