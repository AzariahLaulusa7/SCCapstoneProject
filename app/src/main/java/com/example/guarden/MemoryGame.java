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

        startGameButton.setOnClickListener(v -> {
            startGame();
            startGameButton.setVisibility(View.GONE);
        });

        playAgainButton.setOnClickListener(v -> {
            startGame();
            playAgainButton.setVisibility(View.GONE); // Hide play again button
            startGameButton.setVisibility(View.VISIBLE); // Optionally show start game button
        });

        ImageView backButton = findViewById(R.id.backIcon);
        backButton.setOnClickListener(v -> finish());
    }

    private void initializeButtons() {
        for (int i = 0; i < 9; i++) {
            final Button button = new Button(this);
            button.setId(View.generateViewId());
            buttonIds.add(button.getId());
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.button_background)); // Ensure you have this drawable
            button.setText(""); // Set text to empty for now

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.rowSpec = GridLayout.spec(i / 3, 1, 1f);
            params.columnSpec = GridLayout.spec(i % 3, 1, 1f);
            button.setLayoutParams(params);
            gridLayout.addView(button);

            button.setOnClickListener(v -> {
                if (sequenceIndex < sequence.size() && v.getId() == sequence.get(sequenceIndex)) {
                    sequenceIndex++;
                    if (sequenceIndex == sequence.size()) {
                        Toast.makeText(MemoryGame.this, "Sequence completed!", Toast.LENGTH_SHORT).show();
                        difficultyLevel++;
                        handler.postDelayed(() -> {
                            sequence.clear();
                            sequenceIndex = 0;
                            generateSequence(difficultyLevel);
                            flashSequence();
                        }, 1500);
                    }
                } else {
                    Toast.makeText(MemoryGame.this, "Wrong button! Game Over.", Toast.LENGTH_SHORT).show();
                    difficultyLevel = 1;
                    disableButtons();
                    playAgainButton.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void startGame() {
        sequenceIndex = 0;
        generateSequence(difficultyLevel);
        flashSequence();
        for (int id : buttonIds) {
            Button button = findViewById(id);
            button.setEnabled(true);
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
    private void disableButtons() {
        for (int id : buttonIds) {
            Button button = findViewById(id);
            if (button != null) {
                button.setEnabled(false);
            }
        }
    }
}