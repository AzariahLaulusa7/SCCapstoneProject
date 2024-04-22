package com.example.guarden;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class BreatheMain extends AppCompatActivity {

    // Variables
    TextView breatheInText;
    TextView breatheOutText;
    TextView holdText;
    TextView mouthText;
    TextView noseText;
    Button start;
    ImageButton back;
    ObjectAnimator circleGrow, heartGrow;
    ImageView circle;
    ImageView heart;
    Handler handler;
    Runnable runnable;
    Vibrator vibrate;
    private SharedPreferences sharedPreferences;

    boolean ignore;
    Intent myIntent;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.breathe_main);

        // Initialize variables
        start = findViewById(R.id.start_breathing);
        circle = findViewById(R.id.circle);
        heart = findViewById(R.id.heart);
        back = (ImageButton) findViewById(R.id.backButton);
        breatheInText = findViewById(R.id.breathe_in);
        breatheOutText = findViewById(R.id.breathe_out);
        holdText = findViewById(R.id.hold);
        mouthText = findViewById(R.id.mouth);
        sharedPreferences = getSharedPreferences("DailyChallenges", MODE_PRIVATE);
        noseText = findViewById(R.id.nose);
        handler = new Handler();
        vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        ignore = false;

        myIntent = new Intent(this, HomeScreen.class);

        breatheInText.setVisibility(View.GONE);
        breatheOutText.setVisibility(View.GONE);
        holdText.setVisibility(View.GONE);
        mouthText.setVisibility(View.GONE);
        noseText.setVisibility(View.GONE);

        // When start button is pressed, start animation
        // TODO: change to hold and reset if the user stops touching screen
        start.setOnClickListener(v -> {
            start.setVisibility(View.GONE);
            infiniteAnim();
        });

        // When back button is pressed, go to previous screen -> home
        back.setOnClickListener(v -> {
            ignore();
            handler.removeCallbacks(runnable);
            vibrate.cancel();
            startActivity(myIntent);
            finish();
        });
    }

    // If app is destroyed -> reset
    @Override
    public void onDestroy() {
        ignore();
        handler.removeCallbacks(runnable);
        vibrate.cancel();
        super.onDestroy();
    }

    // If app is paused -> reset
    @Override
    public void onPause() {
        ignore();
        handler.removeCallbacks(runnable);
        vibrate.cancel();
        super.onPause();
    }

    // Infinite loop for circle animation
    public void infiniteAnim() {

        runnable = () -> {
            circleAnim();
            handler.postDelayed(runnable, 11500);
        };

        handler.post(runnable);
    }

    // Pause animation and display hold text
    public void holdAnim() {
        handler.postDelayed(() -> {
            if(!ignore)
                vibrate.vibrate(500);
            breatheInText.setVisibility(View.GONE);
            noseText.setVisibility(View.GONE);
            circleGrow.end();
            heartGrow.end();
            holdText.setVisibility(View.VISIBLE);
        }, 3000);
    }

    // Reverse animation and decrement circle size
    public void reverseAnim() {
        handler.postDelayed(() -> {
            if(!ignore)
                vibrate.vibrate(500);
            holdText.setVisibility(View.GONE);
            //grow.end();
            breatheOutText.setVisibility(View.VISIBLE);
            mouthText.setVisibility(View.VISIBLE);
            circleGrow.reverse();
            heartGrow.reverse();
        }, 5500);
    }

    // Hold after breathe out
    public void secondHold() {
        handler.postDelayed(() -> {
            if(!ignore)
                vibrate.vibrate(500);
            breatheOutText.setVisibility(View.GONE);
            mouthText.setVisibility(View.GONE);
            holdText.setVisibility(View.VISIBLE);
        }, 8400);
    }

    // End animation and sleep
    public void animEnd() {
        handler.postDelayed(() -> holdText.setVisibility(View.GONE), 11500);
        markChallengeAsCompleted();
    }

    // Circle animation
    public void circleAnim() {
        circleGrow = ObjectAnimator.ofPropertyValuesHolder(circle, PropertyValuesHolder.ofFloat("scaleX", 4f),
                PropertyValuesHolder.ofFloat("scaleY", 4f));
        heartGrow = ObjectAnimator.ofPropertyValuesHolder(heart, PropertyValuesHolder.ofFloat("scaleX", 4f),
                PropertyValuesHolder.ofFloat("scaleY", 4f));
        circleGrow.setDuration(2500);
        heartGrow.setDuration(2500);
        circleGrow.setRepeatCount(0);
        heartGrow.setRepeatCount(0);
        breatheOutText.setVisibility(View.GONE);
        holdText.setVisibility(View.GONE);
        breatheInText.setVisibility(View.VISIBLE);
        noseText.setVisibility(View.VISIBLE);
        circleGrow.start();
        heartGrow.start();
        if(!ignore)
            vibrate.vibrate(500);
        holdAnim();
        reverseAnim();
        secondHold();
        animEnd();
    }

    // Used to ignore vibrations if necessary
    public void ignore() {
        ignore = true;
    }

    private void markChallengeAsCompleted() {
        SharedPreferences sharedPreferences = getSharedPreferences("DailyChallenges", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("BreatheCompleted", true);
        editor.apply();
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(myIntent);
    }

}
