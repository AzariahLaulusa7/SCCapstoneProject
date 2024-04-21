package com.example.guarden;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BalloonGame extends Activity {

    private int currentScore = 0;
    private int bestScore = 0;
    private SharedPreferences preferences;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference userScoresRef;
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
        firebaseAuth = FirebaseAuth.getInstance();

        initializeViews();


        imageBalloon.setOnClickListener(v -> {
            if (isGameActive) {
                currentScore++;
                txtCurrentScore.setText("Score: " + currentScore);
            }
        });

        btnPlayAgain.setOnClickListener(v -> startGame());

        startButton.setOnClickListener(v -> {
            startGame();
            v.setVisibility(View.GONE);
        });

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        userScoresRef = FirebaseDatabase.getInstance().getReference("users").child(SaveUser.getUserName(BalloonGame.this)).child("userScores").child("balloonGame");

    }

    private void initializeViews() {
        txtCurrentScore = findViewById(R.id.txtCurrentScore);
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
        isGameActive = true;

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


        if (userScoresRef != null) {
            userScoresRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Integer dbScore = dataSnapshot.getValue(Integer.class);
                    if (dbScore == null || currentScore > dbScore) {
                        userScoresRef.setValue(currentScore);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("Firebase", "Failed to read value for game score update.", databaseError.toException());
                }

            });
        }
    }
}


