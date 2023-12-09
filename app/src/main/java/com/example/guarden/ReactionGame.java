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

    private Button reactionButton;
    private TextView textViewPrompt, textViewScore;
    private long startTime;
    private Handler handler = new Handler();
    private boolean isWaitingForClick = false;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reaction_game);
        ImageView backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(v -> finish());

        reactionButton = findViewById(R.id.reactionButton);
        textViewPrompt = findViewById(R.id.textViewPrompt);
        textViewScore = findViewById(R.id.textViewScore);

        reactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isWaitingForClick) {
                    long reactionTime = System.currentTimeMillis() - startTime;
                    textViewScore.setText("Your Time: " + reactionTime / 1000.0 + "s\nTap square to play again!");
                    reactionButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    isWaitingForClick = false;
                } else {
                    textViewScore.setText("");
                    prepareGame();
                }
            }
        });

        prepareGame();
    }

    private void prepareGame() {
        textViewPrompt.setVisibility(View.INVISIBLE);
        reactionButton.setEnabled(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reactionButton.setEnabled(true);
                textViewPrompt.setVisibility(View.VISIBLE);
                reactionButton.setBackgroundColor(randomColor());
                startTime = System.currentTimeMillis();
                isWaitingForClick = true;
            }
        }, random.nextInt(2000) + 1000);
    }

    private int randomColor() {
        return 0xFF000000 | random.nextInt(0xFFFFFF);
    }
}

