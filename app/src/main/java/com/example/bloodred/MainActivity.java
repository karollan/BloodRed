package com.example.bloodred;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.os.Bundle;

/*
* MainActivity application start point
 */

public class MainActivity extends AppCompatActivity {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Window set to fullscreen, hide the status bar
        Window window = getWindow();
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // Content set view to game, objects in Game class can be rendered to the screen
        game = new Game(this);
        setContentView(game);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        game.pause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //Comment out super to prevent any back press action
        //super.onBackPressed();
    }
}