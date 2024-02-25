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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_home);

        buttonReaction = findViewById(R.id.buttonReaction);
        buttonBalloons = findViewById(R.id.buttonBalloons);
        buttonMemory = findViewById(R.id.buttonMemory);

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

        // Handle the back button click
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}