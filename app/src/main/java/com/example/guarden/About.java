package com.example.guarden;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
//Class for about screen under settings tab
public class About extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
    }
}
