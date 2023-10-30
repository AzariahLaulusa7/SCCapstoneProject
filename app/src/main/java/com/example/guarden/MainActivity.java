package com.example.guarden;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    public void startGame(View view) {
        /**
         * Initializes and starts a new game by setting the current content view to a new instance of GameView
         * @param view: The View object that triggered this method call
         */
        GameView gameView = new GameView(this);
        setContentView(gameView);
    }
}