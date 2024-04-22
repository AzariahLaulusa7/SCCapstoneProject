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
    private int poseCounter, listCounter, tempTracker, whileLoopStuck;
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
        back = findViewById(R.id.backButton);
        thumbsUp = findViewById(R.id.thumbs_up);
        thumbsDown = findViewById(R.id.thumbs_down);
        thumbsUp.setImageResource(R.drawable.baseline_thumb_up_off_alt_24);
        thumbsDown.setImageResource(R.drawable.baseline_thumb_down_off_alt_24);
        poseList = MoveMain.getPoseList();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        myIntent = new Intent(this, MoveMain.class);
        poseCounter=0;
        tempTracker = 0;
        listCounter = 0;
        whileLoopStuck = 0;
        //Increments to the next pose as long as one exists
        while (poseCounter < poseList.size()) {
            if ((poseList.get(poseCounter).getLike() == 0 || poseList.get(poseCounter).getLike() == 1) && poseList.get(poseCounter).getCategory().equals(mode)) {
                listCounter++;
            }
            poseCounter++;
        }
        poseCounter = 0; //Resets list when the end is reached
        //Continues cycling through poses after reset
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
        //Displays text if no poses are available
        if (poseCounter > poseList.size()) {
            name.setText("No Poses Activated");
            start.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
            thumbsDown.setVisibility(View.GONE);
            thumbsUp.setVisibility(View.GONE);
            pose.setVisibility(View.GONE);
        }
        //Starts timer for exercise
        start.setOnClickListener(v -> {
            timer.setVisibility(VISIBLE);
            start.setVisibility(INVISIBLE);
            next.setVisibility(INVISIBLE);
            new CountDownTimer(16 * 1000, 1000) {

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

        //Makes sure the app doesn't crash if something goes wrong with the looping
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean foundValidPose = false;
                while (!foundValidPose) {
                    if (whileLoopStuck > 777) {
                        name.setText("No Poses Activated");
                        start.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);
                        thumbsDown.setVisibility(View.GONE);
                        thumbsUp.setVisibility(View.GONE);
                        pose.setVisibility(View.GONE);
                        SaveUser.setThumbsDown(Movement.this, false);
                        break;
                    }
                    if (poseCounter >= poseList.size()) {
                        poseCounter = firstPoseIndex;
                    }
                    if (SaveUser.getThumbsDown(Movement.this) == true) {
                        listCounter--;
                        //Toast.makeText(Movement.this, "Counter: **"+poseCounter+"**", Toast.LENGTH_LONG).show();
                        SaveUser.setThumbsDown(Movement.this, false);
                    }
                    //Removes icons from screen if no poses are displayed
                    if (poseList.get(poseCounter).getLike() == 2 && poseList.get(poseCounter).getCategory().equalsIgnoreCase(mode) && listCounter < 0) {
                        name.setText("No Poses Activated");
                        start.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);
                        thumbsDown.setVisibility(View.GONE);
                        thumbsUp.setVisibility(View.GONE);
                        pose.setVisibility(View.GONE);
                        SaveUser.setThumbsDown(Movement.this, false);
                        break;
                    }
                    //Sets image, name, and thumbs for the appropriate pose
                    if ((poseList.get(poseCounter).getLike() == 0 || poseList.get(poseCounter).getLike() == 1) && poseList.get(poseCounter).getCategory().equalsIgnoreCase(mode)) {
                        pose.setImageResource(poseList.get(poseCounter).getImageRes());
                        name.setText(poseList.get(poseCounter).getName());
                        setThumbs(poseList.get(poseCounter).getLike());
                        start.setVisibility(View.VISIBLE);
                        tempTracker = poseCounter;
                        foundValidPose = true;
                    }
                    poseCounter++;
                    if (poseCounter >= poseList.size()) {
                        poseCounter = firstPoseIndex;
                    }
                    whileLoopStuck++;
                }
            }
        });
        //Sets thumb icons and saves like status when the user likes a pose
        thumbsUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = poseCounter;
                poseCounter = tempTracker+1;
                changeLike(poseList.get(poseCounter-1).getLike(),1);
                SaveUser.setThumbsDown(Movement.this, false);
                poseCounter = temp;
            }
        });
        //Sets thumb icons and saves like status when the user dislikes a pose
        thumbsDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = poseCounter;
                poseCounter = tempTracker+1;
                changeLike(poseList.get(poseCounter-1).getLike(),2);
                SaveUser.setThumbsDown(Movement.this, true);
                poseCounter = temp;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveUser.setThumbsDown(Movement.this, false);
                startActivity(myIntent);
                finish();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(myIntent);
    }

    //Takes each button press case and changes the appropriate like value
    public void changeLike(int oldRating, int click){
        if(oldRating==0&&click==1) { //Rating is "neutral"; like is pressed
            setThumbs(1);
            poseList.get(poseCounter-1).setLike(1);
            databaseReference.child("users").child(SaveUser.getUserName(Movement.this)).child("customPoses")
                    .child(poseList.get(poseCounter-1).getName()).child("like").setValue(1);
            return;
        }
        if(oldRating==0&&click==2) { //Rating is "neutral"; dislike is pressed
            setThumbs(2);
            poseList.get(poseCounter-1).setLike(2);
            databaseReference.child("users").child(SaveUser.getUserName(Movement.this)).child("customPoses")
                    .child(poseList.get(poseCounter-1).getName()).child("like").setValue(2);
            return;
        }
        if(oldRating==1&&click==1) { //Rating is "liked"; like is pressed
            setThumbs(0);
            poseList.get(poseCounter-1).setLike(0);
            databaseReference.child("users").child(SaveUser.getUserName(Movement.this)).child("customPoses")
                    .child(poseList.get(poseCounter-1).getName()).child("like").setValue(0);
            return;
        }
        if(oldRating==1&&click==2) { //Rating is "liked"; dislike is pressed
            setThumbs(2);
            poseList.get(poseCounter-1).setLike(2);
            databaseReference.child("users").child(SaveUser.getUserName(Movement.this)).child("customPoses")
                    .child(poseList.get(poseCounter-1).getName()).child("like").setValue(2);
            return;
        }
        if(oldRating==2&&click==2) { //Rating is "disliked"; dislike is pressed
            setThumbs(0);
            poseList.get(poseCounter-1).setLike(0);
            databaseReference.child("users").child(SaveUser.getUserName(Movement.this)).child("customPoses")
                    .child(poseList.get(poseCounter-1).getName()).child("like").setValue(0);
            return;
        }
        if(oldRating==2&&click==1) { //Rating is "disliked"; like is pressed
            setThumbs(1);
            poseList.get(poseCounter-1).setLike(1);
            databaseReference.child("users").child(SaveUser.getUserName(Movement.this)).child("customPoses")
                    .child(poseList.get(poseCounter-1).getName()).child("like").setValue(1);
        }
    }

    //Sets the appropriate thumb icons to be enabled and disabled
    public void setThumbs(int rating){
        if(rating==0){ //Neutral
            thumbsDown.setImageResource(R.drawable.baseline_thumb_down_off_alt_24);
            thumbsUp.setImageResource(R.drawable.baseline_thumb_up_off_alt_24);
        }
        if(rating==1){ //Like
            thumbsDown.setImageResource(R.drawable.baseline_thumb_down_off_alt_24);
            thumbsUp.setImageResource(R.drawable.baseline_thumb_up_alt_24);
        }
        if(rating==2){ //Dislike
            thumbsDown.setImageResource(R.drawable.baseline_thumb_down_alt_24);
            thumbsUp.setImageResource(R.drawable.baseline_thumb_up_off_alt_24);
        }
    }
    public void setMode(String mode){
        this.mode=mode;
    }

}
