package com.example.guarden;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
public class AddWidget extends AppCompatActivity {

    // Variables
    ImageButton back, journal, games, breath, forums, move;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_temp);

        // Initialize variables
        back = findViewById(R.id.add_back);
        journal = findViewById(R.id.Journal);
        games = findViewById(R.id.Games);
        breath = findViewById(R.id.Breath);
        forums = findViewById(R.id.Forums);
        move = findViewById(R.id.Move);

        Intent myIntent = new Intent(AddWidget.this, HomeScreen.class);

        back.setOnClickListener(v -> {
            startActivity(myIntent);
        });

        journal.setOnClickListener(v -> {
            startActivity(myIntent);
        });

        games.setOnClickListener(v -> {
            startActivity(myIntent);
        });

        breath.setOnClickListener(v -> {
            startActivity(myIntent);
        });

        forums.setOnClickListener(v -> {
            startActivity(myIntent);
        });

        move.setOnClickListener(v -> {
            startActivity(myIntent);
        });

    }
}
