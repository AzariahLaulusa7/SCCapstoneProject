package com.example.guarden;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class BreatheBackup extends AppCompatActivity {
    Button startBreathing;
    ImageView drop;
    ObjectAnimator grow;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.breathe_backup);
        startBreathing = (Button) findViewById(R.id.start_breathing);
        drop = (ImageView) findViewById(R.id.drop);
        startBreathing.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                grow = grow.ofPropertyValuesHolder(drop, PropertyValuesHolder.ofFloat("scaleX", 1.5f),
                        PropertyValuesHolder.ofFloat("scaleY", 1.5f));
                grow.setDuration(2000);
                grow.setRepeatMode(ValueAnimator.REVERSE);
                grow.setRepeatCount(ValueAnimator.INFINITE);
                grow.start();
            }
        });
    }
}

