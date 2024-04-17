package com.example.guarden;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

public class BalloonGame extends Activity {

    private int currentScore = 0;
    private int bestScore = 0;
    private SharedPreferences preferences;

    private TextView txtCurrentScore;
    private TextView txtBestScore;
    private TextView txtTimer;
    private ImageView imageBalloon;
    private TextView txtGameOver;
    private Button btnPlayAgain;
    private Button startButton;
    private boolean isGameActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balloon_game);

        // Initialize all the view components
        initializeViews();

        // Setup shared preferences
        preferences = getSharedPreferences("GAME_DATA", MODE_PRIVATE);
        bestScore = preferences.getInt("BEST_SCORE", 0);
        txtBestScore.setText("Best: " + bestScore);

        // Listener for the balloon image click
        imageBalloon.setOnClickListener(v -> {
            if (isGameActive) { // Only increase score if the game is active
                currentScore++;
                txtCurrentScore.setText("Score: " + currentScore);
            }
        });

        // Listener for the Play Again button click
        btnPlayAgain.setOnClickListener(v -> startGame());

        // Listener for the Start Game button click
        startButton.setOnClickListener(v -> {
            startGame();
            v.setVisibility(View.GONE);
        });

        // Listener for the back button click
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
    }

    private void initializeViews() {
        txtCurrentScore = findViewById(R.id.txtCurrentScore);
        txtBestScore = findViewById(R.id.txtBestScore);
        txtTimer = findViewById(R.id.txtTimer);
        imageBalloon = findViewById(R.id.imageBalloon);
        txtGameOver = findViewById(R.id.txtGameOver);
        btnPlayAgain = findViewById(R.id.btnPlayAgain);
        startButton = findViewById(R.id.startButton);
    }

    private void startGame() {
        currentScore = 0;
        txtCurrentScore.setText("Score: " + currentScore);
        txtGameOver.setVisibility(View.GONE);
        btnPlayAgain.setVisibility(View.GONE);
        startButton.setVisibility(View.GONE);
        imageBalloon.setEnabled(true);
        isGameActive = true; // Mark game as active

        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                txtTimer.setText("TIME REMAINING\n" + (millisUntilFinished / 1000 + 1) + " SECONDS");
            }

            public void onFinish() {
                gameFinished();
            }
        }.start();
    }

    private void gameFinished() {
        txtTimer.setText("TIME REMAINING\n0 SECONDS");
        txtGameOver.setVisibility(View.VISIBLE);
        btnPlayAgain.setVisibility(View.VISIBLE);
        imageBalloon.setEnabled(false);
        isGameActive = false;

        if(currentScore > bestScore) {
            bestScore = currentScore;
            txtBestScore.setText("Best: " + bestScore);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("BEST_SCORE", bestScore);
            editor.apply();
        }
    }
}