package com.example.guarden;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class BreatheMain extends AppCompatActivity {

    private ImageView breathingAnimation;
    private Button btnStartBreathing;
    private Animation animation;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.breathe_main);

        ImageButton back_button = (ImageButton) findViewById(R.id.backIcon);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MoveMain = new Intent(BreatheMain.this, HomeScreen.class);
                startActivity(MoveMain);
            }
        });

        breathingAnimation = findViewById(R.id.breathingAnimation);
        btnStartBreathing = findViewById(R.id.btnStartBreathing);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.drawable.breathing_animation);

        btnStartBreathing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (breathingAnimation.getVisibility() == View.VISIBLE) {
                    breathingAnimation.setVisibility(View.INVISIBLE);
                    breathingAnimation.clearAnimation();
                } else {
                    breathingAnimation.setVisibility(View.VISIBLE);
                    breathingAnimation.startAnimation(animation);
                }
            }
        });
    }
}