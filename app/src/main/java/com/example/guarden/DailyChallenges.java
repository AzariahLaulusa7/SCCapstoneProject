package com.example.guarden;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;

public class DailyChallenges extends Activity {

    private ImageView gameChallengeIconEmpty;
    private ImageView gameChallengeIconCheck;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_of_mind);

        sharedPreferences = this.getSharedPreferences("DailyChallenges", Context.MODE_PRIVATE);

        // Initialize both ImageView references
        gameChallengeIconEmpty = findViewById(R.id.gameChallengeIconEmpty);
        gameChallengeIconCheck = findViewById(R.id.gameChallengeIconCheck);

        updateGameChallengeIcon();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateGameChallengeIcon();
    }

    private void updateGameChallengeIcon() {
        boolean isCompleted = sharedPreferences.getBoolean("MemoryGameCompleted", false);

        // Update visibility based on whether the challenge is completed
        if (isCompleted) {
            gameChallengeIconEmpty.setVisibility(View.GONE);
            gameChallengeIconCheck.setVisibility(View.VISIBLE);
        } else {
            gameChallengeIconEmpty.setVisibility(View.VISIBLE);
            gameChallengeIconCheck.setVisibility(View.GONE);
        }
    }
}
