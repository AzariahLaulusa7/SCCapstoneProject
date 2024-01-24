package com.example.guarden;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

public class ReactionGame extends AppCompatActivity {

    private Button reactionButton, startButton;
    private TextView textViewPrompt, textViewScore;
    private Handler handler = new Handler();
    private ReactionGameLogic gameLogic = new ReactionGameLogic();

    private boolean gameStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reaction_game);

        reactionButton = findViewById(R.id.reactionButton);
        textViewPrompt = findViewById(R.id.textViewPrompt);
        textViewScore = findViewById(R.id.textViewScore);
        startButton = findViewById(R.id.startButton);
        ImageView backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(v -> finish());

        startButton.setOnClickListener(v -> {
            if (!gameStarted) {
                prepareGame();
                gameStarted = true;
                startButton.setVisibility(View.GONE);
            }
        });

        reactionButton.setOnClickListener(v -> {
            if (gameLogic.isWaitingForClick()) {
                long reactionTime = gameLogic.stopGame();
                textViewScore.setText("Your Time: " + reactionTime / 1000.0 + "s\nTap square to play again!");
                reactionButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            } else {
                textViewScore.setText("");
                prepareGame();
            }
        });
    }

    private void prepareGame() {
        textViewPrompt.setVisibility(View.INVISIBLE);
        reactionButton.setEnabled(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reactionButton.setEnabled(true);
                textViewPrompt.setVisibility(View.VISIBLE); // Make the text visible here
                reactionButton.setBackgroundColor(gameLogic.randomColor());
                gameLogic.startGame();
            }
        }, new Random().nextInt(4000) + 1000); // Random delay between 1 and 5 seconds
    }
}
