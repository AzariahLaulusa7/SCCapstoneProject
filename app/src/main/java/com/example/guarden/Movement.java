package com.example.guarden;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.graphics.drawable.Drawable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;


public class Movement extends AppCompatActivity {
    TextView timer;
    Button start;
    ImageView pose;
    private int poseCount;
    private List<Integer> yogaPoses;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movement);
        start = (Button) findViewById(R.id.start);
        timer = (TextView) findViewById(R.id.timer);
        pose = (ImageView) findViewById(R.id.pose);
        yogaPoses = new Poses("yoga");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setVisibility(INVISIBLE);
                new CountDownTimer(4*1000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        timer.setText(""+millisUntilFinished/1000);
                    }
                    public void onFinish() {
                        pose.setImageResource(yogaPoses.get(poseCount));
                        start.setText("Continue");
                        start.setVisibility(VISIBLE);
                    }
                }.start();
            }
        });
    }
}
