package com.example.guarden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class BreatheMain extends AppCompatActivity {

    TextView breatheInText;
    TextView breatheOutText;
    TextView holdText;
    TextView mouthText;
    TextView noseText;
    TextView relaxText;
    Button start;
    ImageButton back;
    ObjectAnimator grow;
    ObjectAnimator decrement;
    ImageView circle;
    Handler handler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.breathe_main);
        start = (Button) findViewById(R.id.start_breathing);
        circle = (ImageView) findViewById(R.id.circle);
        back = (ImageButton) findViewById(R.id.breathe_back);
        breatheInText = (TextView) findViewById(R.id.breathe_in);
        breatheOutText = (TextView) findViewById(R.id.breathe_out);
        holdText = (TextView) findViewById(R.id.hold);
        mouthText = (TextView) findViewById(R.id.mouth);
        noseText = (TextView) findViewById(R.id.nose);
        relaxText = (TextView) findViewById(R.id.relax);
        Intent myIntent = new Intent(this, HomeScreen.class);
        handler = new Handler();

        breatheInText.setVisibility(View.GONE);
        breatheOutText.setVisibility(View.GONE);
        holdText.setVisibility(View.GONE);
        mouthText.setVisibility(View.GONE);
        noseText.setVisibility(View.GONE);
        relaxText.setVisibility(View.GONE);

        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                start.setVisibility(View.GONE);
                grow = grow.ofPropertyValuesHolder(circle, PropertyValuesHolder.ofFloat("scaleX", 3f),
                        PropertyValuesHolder.ofFloat("scaleY", 3f));
                grow.setDuration(2500);
                grow.setRepeatCount(ValueAnimator.INFINITE);
                breatheOutText.setVisibility(View.GONE);
                holdText.setVisibility(View.GONE);
                circleAnim();
            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(myIntent);
            }
        });
    }

    public void holdAnim() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                breatheInText.setVisibility(View.GONE);
                noseText.setVisibility(View.GONE);
                grow.end();
                holdText.setVisibility(View.VISIBLE);
            }
        }, 3000);
    }

    public void reverseAnim() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                holdText.setVisibility(View.GONE);
                //grow.end();
                breatheOutText.setVisibility(View.VISIBLE);
                mouthText.setVisibility(View.VISIBLE);
                grow.reverse();
            }
        }, 5500);
    }
    public void secondHold() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                breatheOutText.setVisibility(View.GONE);
                mouthText.setVisibility(View.GONE);
                holdText.setVisibility(View.VISIBLE);
            }
        }, 8400);
    }

    public void animEnd() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                holdText.setVisibility(View.GONE);
                start.setVisibility(View.VISIBLE);
            }
        }, 11500);
    }
    public void circleAnim() {
        grow = grow.ofPropertyValuesHolder(circle, PropertyValuesHolder.ofFloat("scaleX", 3f),
                PropertyValuesHolder.ofFloat("scaleY", 3f));
        grow.setDuration(2500);
        grow.setRepeatCount(0);
        breatheInText.setVisibility(View.VISIBLE);
        noseText.setVisibility(View.VISIBLE);
        grow.start();
        holdAnim();
        reverseAnim();
        secondHold();
        animEnd();
    }

}
