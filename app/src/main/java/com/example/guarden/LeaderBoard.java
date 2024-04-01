package com.example.guarden;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LeaderBoard extends AppCompatActivity {
    private TextView memoryGameScore, reactionGameScore, balloonGameScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        memoryGameScore = findViewById(R.id.memoryGameScore);
        reactionGameScore = findViewById(R.id.reactionGameScore);
        balloonGameScore = findViewById(R.id.balloonGameScore);

        loadScores();

        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void loadScores() {
        SharedPreferences gamePrefs = getSharedPreferences("GAME_DATA", MODE_PRIVATE);
        int memoryScore = gamePrefs.getInt("MemoryGameBestScore", 0);
        long reactionScore = gamePrefs.getLong("ReactionGameBestTime", 0);
        int balloonScore = gamePrefs.getInt("BEST_SCORE", 0);

        memoryGameScore.setText("Memory Game Best Level: " + memoryScore);
        reactionGameScore.setText("Reaction Game Best Time: " + (reactionScore / 1000.0) + "s");
        balloonGameScore.setText("Balloon Game Best Score: " + balloonScore);
    }

}
