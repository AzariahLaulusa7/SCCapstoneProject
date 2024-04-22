package com.example.guarden;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryGame extends Activity {

    private GridLayout gridLayout;
    private List<Integer> buttonIds = new ArrayList<>();
    private List<Integer> sequence = new ArrayList<>();
    //Sets game defaults
    private int sequenceIndex = 0;
    private int difficultyLevel = 1;
    private Handler handler = new Handler();
    private boolean gameIsActive = false;

    private TextView textViewFeedback;
    private Button startButton, playAgainButton;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference userScoresRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory_game);
        //Initializes game elements
        gridLayout = findViewById(R.id.gridLayout);
        textViewFeedback = findViewById(R.id.feedbackText);
        startButton = findViewById(R.id.startGameButton);
        playAgainButton = findViewById(R.id.playAgainButton);
        sharedPreferences = getSharedPreferences("DailyChallenges", MODE_PRIVATE);

        firebaseAuth = FirebaseAuth.getInstance();
        userScoresRef = FirebaseDatabase.getInstance().getReference("users").child(SaveUser.getUserName(MemoryGame.this)).child("userScores").child("memoryGame");

        ImageView backButton = findViewById(R.id.backIcon);
        backButton.setOnClickListener(v -> finish());

        initializeButtons();
        //Starts game
        startButton.setOnClickListener(v -> {
            startGame();
            startButton.setVisibility(View.GONE);
        });
        //Restarts game
        playAgainButton.setOnClickListener(v -> {
            startGame();
            playAgainButton.setVisibility(View.GONE);
        });
    }

    private void initializeButtons() {
        for (int i = 0; i < 9; i++) {
            final Button button = new Button(this);
            button.setId(View.generateViewId());
            buttonIds.add(button.getId());
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.button_background));
            button.setText("");
            button.setEnabled(false);
            //Creates game grid
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 0;
            params.rowSpec = GridLayout.spec(i / 3, 1, 1f); //Creates evenly spaced 3x3 grid
            params.columnSpec = GridLayout.spec(i % 3, 1, 1f);
            button.setLayoutParams(params);

            gridLayout.addView(button);
            button.setOnClickListener(v -> {
                buttonReactionLogic(button);
                button.setBackground(ContextCompat.getDrawable(this, R.drawable.button_flash));
                new Handler().postDelayed(() -> {
                    button.setBackground(ContextCompat.getDrawable(this, R.drawable.button_background));
                }, 250); //Adds delay to flashes

            });
        }
    }

    private void buttonReactionLogic(Button button) {
        if (!gameIsActive || !button.isEnabled()) return;

        if (sequenceIndex < sequence.size() && button.getId() == sequence.get(sequenceIndex)) {
            sequenceIndex++;
            if (sequenceIndex == sequence.size()) {
                showFeedbackForAShortTime("Correct!", true);
                difficultyLevel++;
                disableButtons();
                handler.postDelayed(this::resetGame, 1500);
            }
        } else {
            showFeedbackForAShortTime("Wrong button! Game over!", false);
            updateBestScore();
            gameIsActive = false;
            playAgainButton.setVisibility(View.VISIBLE);
            markChallengeAsCompleted();
        }
    }

    private void startGame() {
        difficultyLevel = 1;
        sequenceIndex = 0;
        gameIsActive = true;
        generateSequence(difficultyLevel);
        flashSequence();
    }

    private void resetGame() {
        sequence.clear();
        sequenceIndex = 0;
        generateSequence(difficultyLevel);
        flashSequence();
    }
    //Uses shared preferences to save whether or not user has completed the memory game, for state of mind graph page
    private void markChallengeAsCompleted() {
        SharedPreferences sharedPreferences = getSharedPreferences("DailyChallenges", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("MemoryGameCompleted", true);
        editor.apply();
    }

    //Generates a list of random buttons from the grid, with a given size
    private void generateSequence(int length) {
        Collections.shuffle(buttonIds);
        sequence.clear();
        for (int i = 0; i < length; i++) {
            sequence.add(buttonIds.get(i));
        }
    }

    //Handles the sequence of flashing buttons the user has to recreate
    private void flashSequence() {
        disableButtons();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sequenceIndex < sequence.size()) { //If sequence has buttons remaining
                    Button button = findViewById(sequence.get(sequenceIndex));
                    flashButton(button);
                    sequenceIndex++;
                    handler.postDelayed(this, 1000);
                } else { //Ensures the user cannot tap buttons before sequence has finished
                    sequenceIndex = 0;
                    enableButtons();
                }
            }
        }, 1000);
    }

    //Swaps appearance of buttons when they are flashed
    private void flashButton(Button button) {
        Drawable originalDrawable = button.getBackground();
        Drawable flashDrawable = ContextCompat.getDrawable(this, android.R.drawable.btn_default);

        button.setBackground(flashDrawable);
        handler.postDelayed(() -> button.setBackground(originalDrawable), 500);
    }

    //Allows the user to press buttons
    private void enableButtons() {
        for (int id : buttonIds) {
            Button button = findViewById(id);
            button.setEnabled(true);
        }
    }

    //Prevents the user from pressing buttons
    private void disableButtons() {
        for (int id : buttonIds) {
            Button button = findViewById(id);
            button.setEnabled(false);
        }
    }

    //Updates db high score if user exceeds current high score
    private void updateBestScore() {
        if (userScoresRef != null) {
            userScoresRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Integer dbScore = dataSnapshot.getValue(Integer.class);
                    if (dbScore == null || (difficultyLevel - 1) > dbScore) { //Compares user score to high score in db
                        userScoresRef.setValue((difficultyLevel - 1)); //Sets db high score to current user score
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("Firebase", "Failed to read value for game score update.", databaseError.toException());
                }

            });
        }
        disableButtons();
    }

    //Flashes the button green if correctly tapped or red if incorrectly tapped
    private void showFeedbackForAShortTime(String feedback, boolean isCorrect) {
        textViewFeedback.setText(feedback);
        if (isCorrect) {
            textViewFeedback.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
        } else {
            textViewFeedback.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
        }
        textViewFeedback.setVisibility(View.VISIBLE);
        handler.postDelayed(() -> textViewFeedback.setVisibility(View.INVISIBLE), 1500);
    }
}
