package com.example.guarden;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class ForumMain extends AppCompatActivity {

    // Variables
    ImageButton back, newPost;
    TextView filter, sort;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_main);

        // Initialize variables
        back = findViewById(R.id.forum_back_icon);

        Intent myIntent = new Intent(ForumMain.this, HomeScreen.class);

        // When back button is pressed, go to previous screen -> home
        back.setOnClickListener(v -> {
            startActivity(myIntent);
        });
    }

}
