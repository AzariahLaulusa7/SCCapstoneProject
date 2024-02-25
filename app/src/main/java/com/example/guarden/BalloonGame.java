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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balloon_game);

        txtCurrentScore = findViewById(R.id.txtCurrentScore);
        txtBestScore = findViewById(R.id.txtBestScore);
        txtTimer = findViewById(R.id.txtTimer);
        imageBalloon = findViewById(R.id.imageBalloon);
        txtGameOver = findViewById(R.id.txtGameOver);
        btnPlayAgain = findViewById(R.id.btnPlayAgain);
        startButton = findViewById(R.id.startButton);

        preferences = getSharedPreferences("GAME_DATA", MODE_PRIVATE);
        bestScore = preferences.getInt("BEST_SCORE", 0);
        txtBestScore.setText("Best: " + bestScore);

        imageBalloon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentScore++;
                txtCurrentScore.setText("Score: " + currentScore);
            }
        });

        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
                v.setVisibility(View.GONE); // Hide the start button when the game starts
            }
        });
    }

    private void startGame() {
        currentScore = 0;
        txtCurrentScore.setText("Score: " + currentScore);
        txtGameOver.setVisibility(View.GONE);
        btnPlayAgain.setVisibility(View.GONE);
        startButton.setVisibility(View.GONE);
        imageBalloon.setEnabled(true);

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                txtTimer.setText("TIME REMAINING\n     " + millisUntilFinished / 1000 + " SECONDS");
            }

            public void onFinish() {
                txtTimer.setText("TIME REMAINING\n     0 SECONDS");
                txtGameOver.setVisibility(View.VISIBLE);
                btnPlayAgain.setVisibility(View.VISIBLE);
                imageBalloon.setEnabled(false);

                if(currentScore > bestScore) {
                    bestScore = currentScore;
                    txtBestScore.setText("Best: " + bestScore);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("BEST_SCORE", bestScore);
                    editor.apply();
                }
            }
        }.start();

        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}