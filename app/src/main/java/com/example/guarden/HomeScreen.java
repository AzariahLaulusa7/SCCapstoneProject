package com.example.guarden;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;

public class HomeScreen extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_home);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void startMove(View view) {
        /**
         * Initializes and starts a new game by setting the current content view to a new instance of GameView
         * @param view: The View object that triggered this method call
         */
        move move2 = new move();
        setContentView(R.layout.move);
    }
}