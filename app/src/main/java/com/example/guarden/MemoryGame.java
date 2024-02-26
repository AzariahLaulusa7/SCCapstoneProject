package com.example.guarden;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryGame extends Activity {

    GridLayout gridLayout;
    List<Integer> buttonIds = new ArrayList<>();
    List<Integer> sequence = new ArrayList<>();
    int sequenceIndex = 0;
    int difficultyLevel = 1;
    Handler handler = new Handler();
    private Button startGameButton;
    private Button playAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory_game);

        gridLayout = findViewById(R.id.gridLayout);
        startGameButton = findViewById(R.id.startGameButton);
        playAgainButton = findViewById(R.id.playAgainButton);

        initializeButtons();

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the game logic here
                startGame();
                // Make the start button disappear
                v.setVisibility(View.GONE);
            }
        });
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset game state and start a new game
                startGame();
                playAgainButton.setVisibility(View.GONE); // Hide play again button
                startGameButton.setVisibility(View.VISIBLE); // Show start game button
            }
        });
        ImageView backButton = findViewById(R.id.backIcon);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the current activity and return to the previous one
                finish();
            }
        });
    }

    private void initializeButtons() {
        for (int i = 0; i < 9; i++) {
            final Button button = new Button(this);
            button.setId(View.generateViewId());
            buttonIds.add(button.getId());
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.button_background));
            button.setText(String.valueOf(i + 1));

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.rowSpec = GridLayout.spec(i / 3, 1, 1f);
            params.columnSpec = GridLayout.spec(i % 3, 1, 1f);
            button.setLayoutParams(params);
            gridLayout.addView(button);

            // Set the button click listener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check if the button clicked is the correct one in the sequence
                    if (v.getId() == sequence.get(sequenceIndex)) {
                        sequenceIndex++;
                        if (sequenceIndex == sequence.size()) {
                            // User has successfully completed the sequence
                            Toast.makeText(MemoryGame.this, "Sequence completed!", Toast.LENGTH_SHORT).show();
                            difficultyLevel++; // Increase difficulty for the next round

                            // Clear the current sequence and prepare for the next round
                            sequence.clear();
                            sequenceIndex = 0;

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    generateSequence(difficultyLevel);
                                    flashSequence();
                                }
                            }, 1500);
                        }
                    } else {
                        // User clicked the wrong button
                        Toast.makeText(MemoryGame.this, "Wrong button! Game Over.", Toast.LENGTH_SHORT).show();

                        // Disable all buttons to prevent further input
                        for (int id : buttonIds) {
                            Button button = findViewById(id);
                            if (button != null) {
                                button.setEnabled(false);
                            }
                        }
                        playAgainButton.setVisibility(View.VISIBLE);
                    }
                }

            });
        }
    }

    private void startGame() {
        sequenceIndex = 0;
        generateSequence(difficultyLevel);
        flashSequence();
        for (int id : buttonIds) {
            findViewById(id).setEnabled(true);
        }
    }

    private void generateSequence(int length) {
        Collections.shuffle(buttonIds);
        sequence.clear();
        for (int i = 0; i < length; i++) {
            sequence.add(buttonIds.get(i));
        }
    }

    private void flashSequence() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sequenceIndex < sequence.size()) {
                    Button button = findViewById(sequence.get(sequenceIndex));
                    flashButton(button);
                    sequenceIndex++;
                    handler.postDelayed(this, 1000);
                } else {
                    // End of sequence, allow user interaction
                    sequenceIndex = 0;
                    for (int id : sequence) {
                        Button button = findViewById(id);
                        button.setClickable(true);
                    }
                }
            }
        }, 1000);
    }

    private void flashButton(final Button button) {
        final Drawable originalDrawable = button.getBackground();
        final Drawable flashDrawable = getResources().getDrawable(android.R.drawable.btn_default);

        button.setBackground(flashDrawable);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                button.setBackground(originalDrawable);
            }
        }, 500);
    }
}