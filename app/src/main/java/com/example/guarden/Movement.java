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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    Intent myIntent;
    private static ArrayList<Pose> poseList;
    private int poseCounter, listCounter, poseMax;
    private int firstPoseIndex;
    private DatabaseReference databaseReference;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras!=null) mode=extras.getString("mode");
        setContentView(R.layout.movement);
        start = findViewById(R.id.start);
        timer = findViewById(R.id.timer);
        name = findViewById(R.id.pose_name);
        pose = findViewById(R.id.pose);
        next = findViewById(R.id.next);
        back = findViewById(R.id.movement_back);
        thumbsUp = findViewById(R.id.thumbs_up);
        thumbsDown = findViewById(R.id.thumbs_down);
        thumbsUp.setImageResource(R.drawable.baseline_thumb_up_off_alt_24);
        thumbsDown.setImageResource(R.drawable.baseline_thumb_down_off_alt_24);
        poseList = MoveMain.getPoseList();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        myIntent = new Intent(this, MoveMain.class);
        poseCounter=0;
        poseMax = 0;
        listCounter = 0;
        while (poseCounter < poseList.size()) {
            if ((poseList.get(poseCounter).getLike() == 0 || poseList.get(poseCounter).getLike() == 1) && poseList.get(poseCounter).getCategory().equals(mode)) {
                pose.setImageResource(poseList.get(poseCounter).getImageRes());
                name.setText(poseList.get(poseCounter).getName());
                setThumbs(poseList.get(poseCounter).getLike());
                firstPoseIndex=poseCounter;
                start.setVisibility(VISIBLE);
                break;
            }
            else poseCounter++;
        }
        poseCounter++;
        timer.setVisibility(INVISIBLE);
        if (poseCounter > poseList.size() - 1) {
            name.setText("No Poses Activated");
            start.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
            thumbsDown.setVisibility(View.GONE);
            thumbsUp.setVisibility(View.GONE);
            pose.setVisibility(View.GONE);
        }
        start.setOnClickListener(v -> {
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
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean foundValidPose = false;
                while (!foundValidPose) {
                    if (poseCounter >= poseList.size() - 1) {
                        poseCounter = firstPoseIndex;
                        poseMax = listCounter;
                    }
                    if (poseList.get(poseCounter).getLike() == 2 && poseList.get(poseCounter).getCategory().equalsIgnoreCase(mode) && poseMax == poseCounter) {
                        name.setText("No Poses Activated");
                        start.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);
                        thumbsDown.setVisibility(View.GONE);
                        thumbsUp.setVisibility(View.GONE);
                        pose.setVisibility(View.GONE);
                        break;
                    }
                    if ((poseList.get(poseCounter).getLike() == 0 || poseList.get(poseCounter).getLike() == 1) && poseList.get(poseCounter).getCategory().equalsIgnoreCase(mode)) {
                        pose.setImageResource(poseList.get(poseCounter).getImageRes());
                        name.setText(poseList.get(poseCounter).getName());
                        setThumbs(poseList.get(poseCounter).getLike());
                        start.setVisibility(View.VISIBLE);
                        foundValidPose = true;
                        listCounter++;
                    }
                    poseCounter++;
                    if (poseCounter >= poseList.size()) {
                        poseCounter = firstPoseIndex;
                        poseMax = listCounter;
                    }
                }
            }
        });
        thumbsUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               changeLike(poseList.get(poseCounter-1).getLike(),1);
            }
        });
        thumbsDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLike(poseList.get(poseCounter-1).getLike(),2);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(myIntent);
                finish();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(myIntent);
    }

    public void changeLike(int oldRating, int click){
        if(oldRating==0&&click==1) {
            setThumbs(1);
            poseList.get(poseCounter-1).setLike(1);
            databaseReference.child("users").child(SaveUser.getUserName(Movement.this)).child("customPoses")
                    .child(poseList.get(poseCounter-1).getName()).child("like").setValue(1);
            return;
        }
        if(oldRating==0&&click==2) {
            setThumbs(2);
            poseList.get(poseCounter-1).setLike(2);
            databaseReference.child("users").child(SaveUser.getUserName(Movement.this)).child("customPoses")
                    .child(poseList.get(poseCounter-1).getName()).child("like").setValue(2);
            return;
        }
        if(oldRating==1&&click==1) {
            setThumbs(0);
            poseList.get(poseCounter-1).setLike(0);
            databaseReference.child("users").child(SaveUser.getUserName(Movement.this)).child("customPoses")
                    .child(poseList.get(poseCounter-1).getName()).child("like").setValue(0);
            return;
        }
        if(oldRating==1&&click==2) {
            setThumbs(2);
            poseList.get(poseCounter-1).setLike(2);
            databaseReference.child("users").child(SaveUser.getUserName(Movement.this)).child("customPoses")
                    .child(poseList.get(poseCounter-1).getName()).child("like").setValue(2);
            return;
        }
        if(oldRating==2&&click==2) {
            setThumbs(0);
            poseList.get(poseCounter-1).setLike(0);
            databaseReference.child("users").child(SaveUser.getUserName(Movement.this)).child("customPoses")
                    .child(poseList.get(poseCounter-1).getName()).child("like").setValue(0);
            return;
        }
        if(oldRating==2&&click==1) {
            setThumbs(1);
            poseList.get(poseCounter-1).setLike(1);
            databaseReference.child("users").child(SaveUser.getUserName(Movement.this)).child("customPoses")
                    .child(poseList.get(poseCounter-1).getName()).child("like").setValue(1);
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

}
