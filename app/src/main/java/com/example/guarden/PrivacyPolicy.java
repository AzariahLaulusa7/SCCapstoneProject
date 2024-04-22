package com.example.guarden;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
//Creates privacy policy tab under settings tab
public class PrivacyPolicy extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_policy);
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
    }
}
