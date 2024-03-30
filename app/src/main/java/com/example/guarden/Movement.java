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
import android.content.Intent;
public class Movement extends AppCompatActivity {
    TextView timer;
    TextView name;
    Button start;
    Button next;
    ImageButton back;
    String mode;
    ImageButton thumbsUp;
    ImageButton thumbsDown;

    ImageView pose;
    private static ArrayList<Pose> poseList;
    private int poseCounter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        poseList=new ArrayList<Pose>();
        Bundle extras = getIntent().getExtras();
        if(extras!=null) mode=extras.getString("mode");
        setContentView(R.layout.movement);
        start = (Button) findViewById(R.id.start);
        timer = (TextView) findViewById(R.id.timer);
        name = (TextView) findViewById(R.id.pose_name);
        pose = (ImageView) findViewById(R.id.pose);
        next = (Button) findViewById(R.id.next);
        back = (ImageButton) findViewById(R.id.movement_back);
        thumbsUp = (ImageButton) findViewById(R.id.thumbs_up);
        thumbsDown = (ImageButton) findViewById(R.id.thumbs_down);
        thumbsUp.setImageResource(R.drawable.baseline_thumb_up_off_alt_24);
        thumbsDown.setImageResource(R.drawable.baseline_thumb_down_off_alt_24);
        Intent myIntent = new Intent(this, MoveMain.class);
        poseCounter=0;
        //name.setText(poseList.get(poseCounter).getName());
        //pose.setImageResource(poseList.get(poseCounter).getImageRes());
        //setThumbs(poseList.get(poseCounter).getLike());
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
                if (poseCounter+1 >= poseList.size()) poseCounter = 0;
                else poseCounter++;
                if(poseList.get(poseCounter).getLike()==2) poseCounter++;
                while(poseList.get(poseCounter).getLike()==0||poseList.get(poseCounter).getLike()==1){
                    pose.setImageResource(poseList.get(poseCounter).getImageRes());
                    name.setText(poseList.get(poseCounter).getName());
                    setThumbs(poseList.get(poseCounter).getLike());
                    start.setVisibility(VISIBLE);
                    break;
                }

            }

        });
        thumbsUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLike(poseList.get(poseCounter).getLike(),1);
            }
        });
        thumbsDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLike(poseList.get(poseCounter).getLike(),2);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(myIntent);
            }
        });
    }
    public void changeLike(int oldRating, int click){
        if(oldRating==0&&click==1) {
            setThumbs(1);
            poseList.get(poseCounter).setLike(1);
            return;
        }
        if(oldRating==0&&click==2) {
            setThumbs(2);
            poseList.get(poseCounter).setLike(2);
            return;
        }
        if(oldRating==1&&click==1) {
            setThumbs(0);
            poseList.get(poseCounter).setLike(0);
            return;
        }
        if(oldRating==1&&click==2) {
            setThumbs(2);
            poseList.get(poseCounter).setLike(2);
            return;
        }
        if(oldRating==2&&click==2) {
            setThumbs(0);
            poseList.get(poseCounter).setLike(0);
            return;
        }
        if(oldRating==2&&click==1) {
            setThumbs(1);
            poseList.get(poseCounter).setLike(1);
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
    public void setMode(String mode){
        this.mode=mode;
    }
    public static void addPose(Pose pose){
        poseList.add(pose);
    }
    public static ArrayList<Pose> getPoseList(){
        return poseList;
    }
}
