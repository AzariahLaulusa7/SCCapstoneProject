package com.example.guarden;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
//Creates terms of use page, under settings
public class TermsOfUse extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_of_use);
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
    }
}
