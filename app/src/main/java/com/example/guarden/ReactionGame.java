package com.example.guarden;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Random;

public class ReactionGame extends AppCompatActivity {

    private RelativeLayout mainLayout;
    private TextView textViewScore, instructions;
    private Button startButton;
    private Handler handler = new Handler();
    private Runnable colorChangeRunnable;
    boolean readyForReaction = false;
    private boolean gameIsActive = false;
    long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reaction_game);

        mainLayout = findViewById(R.id.mainLayout);
        textViewScore = findViewById(R.id.textViewScore);
        instructions = findViewById(R.id.instructions);
        startButton = findViewById(R.id.startButton);
        ImageView backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(v -> finish());

        startButton.setOnClickListener(v -> {
            startGame();
        });

        mainLayout.setOnClickListener(v -> {
            if (gameIsActive) {
                if (!readyForReaction) {
                    gameLostPrematurely();
                } else {
                    gameWon(SystemClock.elapsedRealtime());
                }
            }
        });
    }

    private void startGame() {
        startButton.setVisibility(View.GONE);
        instructions.setVisibility(View.GONE);
        textViewScore.setVisibility(View.INVISIBLE);
        mainLayout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
        gameIsActive = true;
        prepareGame();
    }

    private void gameWon(long reactionEndTime) {
        long reactionTime = reactionEndTime - startTime;
        textViewScore.setText(String.format("Reaction time: %.3f s", reactionTime / 1000.0));
        textViewScore.setVisibility(View.VISIBLE);

        // SharedPreferences to save best time logic
        SharedPreferences gamePrefs = getSharedPreferences("GAME_DATA", MODE_PRIVATE);
        long currentBestTime = gamePrefs.getLong("ReactionGameBestTime", Long.MAX_VALUE);
        if (reactionTime < currentBestTime) {
            SharedPreferences.Editor editor = gamePrefs.edit();
            editor.putLong("ReactionGameBestTime", reactionTime);
            editor.apply();
        }

        endGame();
    }

    private void gameLostPrematurely() {
        handler.removeCallbacks(colorChangeRunnable);
        gameLost();
    }

    private void gameLost() {
        textViewScore.setText("You lose! Try again.");
        textViewScore.setVisibility(View.VISIBLE);
        endGame();
    }

    private void endGame() {
        mainLayout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
        startButton.setText("Play Again");
        startButton.setVisibility(View.VISIBLE);
        readyForReaction = false;
        gameIsActive = false;
    }

    private void prepareGame() {
        colorChangeRunnable = new Runnable() {
            @Override
            public void run() {
                mainLayout.setBackgroundColor(ContextCompat.getColor(ReactionGame.this, android.R.color.holo_blue_bright));
                startTime = SystemClock.elapsedRealtime();
                readyForReaction = true;
            }
        };
        handler.postDelayed(colorChangeRunnable, new Random().nextInt(4000) + 1000);
    }
}
