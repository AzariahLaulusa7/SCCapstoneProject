package com.example.guarden;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

//This class is not used
public class BreatheBackup extends AppCompatActivity {
    Button startBreathing;
    ImageView drop;
    ImageButton back;
    ObjectAnimator grow;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.breathe_backup);
        startBreathing = (Button) findViewById(R.id.start_breathing);
        drop = (ImageView) findViewById(R.id.drop);
        back = (ImageButton) findViewById(R.id.breathe_back);
        Intent myIntent = new Intent(this, HomeScreen.class);
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
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(myIntent);
            }
        });
    }
}

