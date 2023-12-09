package com.example.guarden;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BreatheMain extends AppCompatActivity {

    private ImageView breathingAnimation;
    private Button btnStartBreathing;
    private Animation animation;
    private TextView breatheText;
    private CountDownTimer breatheTimer;
    private boolean isInhaling = false;
    private int totalTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.breathe_main);

        ImageButton back_button = findViewById(R.id.backIcon);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (breatheTimer != null) {
                    breatheTimer.cancel();
                }
                Intent moveToMain = new Intent(BreatheMain.this, HomeScreen.class);
                startActivity(moveToMain);
            }
        });

        breathingAnimation = findViewById(R.id.breathingAnimation);
        btnStartBreathing = findViewById(R.id.btnStartBreathing);
        breatheText = findViewById(R.id.breatheText);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.circle_scale_animation);

        btnStartBreathing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBreathing();
            }
        });
    }

    private void startBreathing() {
        if (breatheTimer != null) {
            breatheTimer.cancel();
        }
        totalTime = 0;
        isInhaling = true;
        startTimer();
    }

    private void startTimer() {
        breatheTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                totalTime += 1000;
                if (isInhaling) {
                    if (totalTime >= 4000) {
                        isInhaling = false;
                        totalTime = 0;
                        updateText("Hold"); // Update text for holding breath
                        startTimer();
                    }
                } else {
                    if (totalTime >= 4000) {
                        isInhaling = true;
                        totalTime = 0;
                        updateText("Breathe Out"); // Update text for exhaling
                        startTimer();
                    }
                }
            }

            @Override
            public void onFinish() {
                startBreathing();
            }
        }.start();

        animateBreathing();
    }

    private void animateBreathing() {
        if (isInhaling) {
            breathingAnimation.setVisibility(View.VISIBLE);
            breathingAnimation.startAnimation(animation);
            updateText("Breathe In"); // Update text for inhaling
        } else {
            breathingAnimation.setVisibility(View.INVISIBLE);
            breathingAnimation.clearAnimation();
        }
    }

    private void updateText(String text) {
        breatheText.setText(text);
    }

    @Override
    protected void onDestroy() {
        if (breatheTimer != null) {
            breatheTimer.cancel();
        }
        super.onDestroy();
    }
}
