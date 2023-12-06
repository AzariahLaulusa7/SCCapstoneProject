package com.example.guarden;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.View;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;
public class Movement extends AppCompatActivity {
    TextView timer;
    TextView name;
    Button start;
    Button next;
    ImageButton thumbsUp;
    ImageButton thumbsDown;

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
        name = (TextView) findViewById(R.id.pose_name);
        pose = (ImageView) findViewById(R.id.pose);
        next = (Button) findViewById(R.id.next);
        thumbsUp = (ImageButton) findViewById(R.id.thumbs_up);
        thumbsDown = (ImageButton) findViewById(R.id.thumbs_down);
        thumbsUp.setImageResource(R.drawable.baseline_thumb_up_off_alt_24);
        thumbsDown.setImageResource(R.drawable.baseline_thumb_down_off_alt_24);
        poseCounter=0;
        name.setText(yogaPoses.get(poseCounter).getName());
        pose.setImageResource(yogaPoses.get(poseCounter).getImageRes());
        setThumbs(yogaPoses.get(poseCounter).getLike());
        timer.setVisibility(INVISIBLE);
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timer.setVisibility(VISIBLE);
                    start.setVisibility(INVISIBLE);
                    next.setVisibility(INVISIBLE);
                    new CountDownTimer(4 * 1000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            timer.setText("" + millisUntilFinished / 1000);
                        }
                        public void onFinish() {
                            timer.setVisibility(INVISIBLE);
                            start.setVisibility(VISIBLE);
                            next.setVisibility(VISIBLE);
                        }
                    }.start();
                }
            });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (poseCounter+1 >= yogaPoses.size()) poseCounter = 0;
                else poseCounter++;
                while(yogaPoses.get(poseCounter).getLike()==2){ //Needs to address situation where the user dislikes all
                    if (poseCounter+1 >= yogaPoses.size()) poseCounter = 0;
                    else poseCounter++;
                }
                pose.setImageResource(yogaPoses.get(poseCounter).getImageRes());
                name.setText(yogaPoses.get(poseCounter).getName());
                setThumbs(yogaPoses.get(poseCounter).getLike());
                start.setVisibility(VISIBLE);
                }
        });
        thumbsUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLike(yogaPoses.get(poseCounter).getLike(),1);
            }
        });
        thumbsDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLike(yogaPoses.get(poseCounter).getLike(),2);
            }
        });
    }
    public void changeLike(int oldRating, int click){
        if(oldRating==0&&click==1) {
            setThumbs(1);
            yogaPoses.get(poseCounter).setLike(1);
            return;
        }
        if(oldRating==0&&click==2) {
            setThumbs(2);
            yogaPoses.get(poseCounter).setLike(2);
            return;
        }
        if(oldRating==1&&click==1) {
            setThumbs(0);
            yogaPoses.get(poseCounter).setLike(0);
            return;
        }
        if(oldRating==1&&click==2) {
            setThumbs(2);
            yogaPoses.get(poseCounter).setLike(2);
            return;
        }
        if(oldRating==2&&click==2) {
            setThumbs(0);
            yogaPoses.get(poseCounter).setLike(0);
            return;
        }
        if(oldRating==2&&click==1) {
            setThumbs(1);
            yogaPoses.get(poseCounter).setLike(1);
        }
    }
    public void setThumbs(int rating){
        if(rating==0){
            thumbsDown.setImageResource(R.drawable.baseline_thumb_down_off_alt_24);
            thumbsUp.setImageResource(R.drawable.baseline_thumb_up_off_alt_24);
        }
        if(rating==1){
            thumbsDown.setImageResource(R.drawable.baseline_thumb_down_off_alt_24);
            thumbsUp.setImageResource(R.drawable.baseline_thumb_up_alt_24);
        }
        if(rating==2){
            thumbsDown.setImageResource(R.drawable.baseline_thumb_down_alt_24);
            thumbsUp.setImageResource(R.drawable.baseline_thumb_up_off_alt_24);
        }
    }
}
