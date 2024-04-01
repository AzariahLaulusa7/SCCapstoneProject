package com.example.guarden;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class GameHome extends Activity {

    private LinearLayout buttonReaction;
    private LinearLayout buttonBalloons;
    private LinearLayout buttonMemory;
    private LinearLayout buttonLeaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_home);
        NotificationService.setRecentView("game_home");
        buttonReaction = findViewById(R.id.buttonReaction);
        buttonBalloons = findViewById(R.id.buttonBalloons);
        buttonMemory = findViewById(R.id.buttonMemory);
        buttonLeaderboard = findViewById(R.id.buttonLeaderboard);

        // Set up the listeners for the buttons
        buttonReaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Reaction game activity
                Intent intent = new Intent(GameHome.this, ReactionGame.class);
                startActivity(intent);
            }
        });

        buttonBalloons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Balloons game activity
                Intent intent = new Intent(GameHome.this, BalloonGame.class);
                startActivity(intent);
            }
        });

        buttonMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Coloring game activity
                Intent intent = new Intent(GameHome.this, MemoryGame.class);
                startActivity(intent);
            }
        });
        buttonLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Coloring game activity
                Intent intent = new Intent(GameHome.this, LeaderBoard.class);
                startActivity(intent);
            }
        });

        // Handle the back button click
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameHome.this, HomeScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }
}