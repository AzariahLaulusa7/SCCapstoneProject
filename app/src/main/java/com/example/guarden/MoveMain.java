package com.example.guarden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MoveMain extends AppCompatActivity {
    Button yoga;
    Button exercise;
    Button viewAll;
    ImageButton back;
    private DatabaseReference databaseReference;
    static ArrayList<Pose> poseList = new ArrayList<Pose>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.move_main);
        yoga = (Button) findViewById(R.id.yoga);
        exercise = (Button) findViewById(R.id.exercise);
        back = (ImageButton) findViewById(R.id.move_back);
        viewAll = (Button) findViewById(R.id.custom);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Intent myIntent = new Intent(this, Movement.class);
        Intent myIntent2 = new Intent(this, HomeScreen.class);
        Intent viewMoveList = new Intent(this, MovementViewList.class);
        yoga.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                myIntent.putExtra("mode","yoga");
                startActivity(myIntent);
            }
        });
        exercise.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                myIntent.putExtra("mode","exercise");
                startActivity(myIntent);
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("users").child(Login.UserID).child("customPoses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                poseList.clear();
                poseList.add(new Pose("yoga","Lunge",R.drawable.pose1,""));
                poseList.add(new Pose("yoga","Triangle",R.drawable.pose2,""));
                poseList.add(new Pose("yoga","Forward Fold",R.drawable.pose3,""));
                poseList.add(new Pose("exercise","Push Up",R.drawable.exercise1,""));
                poseList.add(new Pose("exercise","Sit Up",R.drawable.exercise2,""));
                poseList.add(new Pose("exercise","Squat",R.drawable.exercise3,""));
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tempName = snapshot.child("name").getValue(String.class);
                    String tempCategory = snapshot.child("category").getValue(String.class);
                    Integer tempImageRes = snapshot.child("imageRes").getValue(Integer.class);
                    String tempDescription = snapshot.child("description").getValue(String.class);
                    int tempLike = snapshot.child("like").getValue(Integer.class) != null ? snapshot.child("like").getValue(Integer.class) : 0;
                    Pose pose = new Pose(tempCategory, tempName, tempImageRes != 0 ? tempImageRes : R.drawable.resource_default, tempDescription);
                    poseList.add(pose);
                }
                if (MovementViewList.movementAdapter != null) {
                    MovementViewList.movementAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("DatabaseError", error.getMessage());
            }
        });
        viewAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(viewMoveList);
            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(myIntent2);
            }
        });
    }
    public static ArrayList<Pose> getPoseList(){
        return poseList;
    }
}

