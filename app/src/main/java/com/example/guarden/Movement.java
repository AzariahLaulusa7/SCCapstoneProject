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
    TextView name;
    Button start;
    ImageView pose;
    private ArrayList<Pose> yogaPoses;
    private int poseCounter;
    Pose pose2;
    Pose pose3;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        yogaPoses=new ArrayList<Pose>();
        setContentView(R.layout.movement);
        Pose pose1 = new Pose("yoga","Lunge",R.drawable.pose1);
        yogaPoses.add(pose1);
        pose2 = new Pose("yoga","Triangle",R.drawable.pose2);
        yogaPoses.add(pose2);
        pose3 = new Pose("yoga","Forward Fold",R.drawable.pose3);
        yogaPoses.add(pose3);
        start = (Button) findViewById(R.id.start);
        timer = (TextView) findViewById(R.id.timer);
        name = (TextView) findViewById(R.id.poseName);
        pose = (ImageView) findViewById(R.id.pose);
        poseCounter=0;
        name.setText(yogaPoses.get(poseCounter).getName());
        pose.setImageResource(yogaPoses.get(poseCounter).getImageRes());
        timer.setVisibility(INVISIBLE);
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timer.setVisibility(VISIBLE);
                    start.setVisibility(INVISIBLE);
                    start.setText("Start");
                    new CountDownTimer(4 * 1000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            timer.setText("" + millisUntilFinished / 1000);
                        }

                        public void onFinish() {
                            if(poseCounter==yogaPoses.size()-1) poseCounter=0;
                            else poseCounter++;
                            pose.setImageResource(yogaPoses.get(poseCounter).getImageRes());
                            name.setText(yogaPoses.get(poseCounter).getName());
                            timer.setVisibility(INVISIBLE);
                            start.setVisibility(VISIBLE);
                        }
                    }.start();
                }
            });
    }
}
