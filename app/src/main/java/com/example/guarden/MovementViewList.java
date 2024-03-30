package com.example.guarden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MovementViewList extends AppCompatActivity {
    Button add;
    ImageButton back;
    long customPoseCount;
    String tempCategory;
    String tempName;
    int tempImage;
    String tempDescription;
    private DatabaseReference databaseReference;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movement_view_list);
        add = (Button) findViewById(R.id.add);
        back = (ImageButton) findViewById(R.id.move_back);
        RecyclerView recycler = findViewById(R.id.recycler);
        ArrayList<Pose> poseList = new ArrayList<Pose>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        /*databaseReference.child("users").child(Login.UserID).child("customPoses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    poseList.add(new Pose("exercise","Squat",R.drawable.exercise3,""));
                    tempName = snapshot.child("name").getValue(String.class);
                    tempCategory = snapshot.child("category").getValue(String.class);
                    tempImage = snapshot.child("imageRes").getValue(int.class);
                    tempDescription = snapshot.child("description").getValue(String.class);
                    Pose pose = new Pose(tempCategory,tempName, tempImage, tempDescription);
                    poseList.add(pose);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        */
        MovementAdapter movementAdapter = new MovementAdapter(this, poseList);
        databaseReference.child("users").child(Login.UserID).child("customPoses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                poseList.clear();
                poseList.add(new Pose("yoga","Lunge",R.drawable.pose1,""));
                poseList.add(new Pose("yoga","Triangle",R.drawable.pose2,""));
                poseList.add(new Pose("yoga","Forward Fold",R.drawable.pose3,""));
                poseList.add(new Pose("exercise","Push Up",R.drawable.exercise1,""));
                poseList.add(new Pose("exercise","Sit Up",R.drawable.exercise2,""));
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tempName = snapshot.child("name").getValue(String.class);
                    String tempCategory = snapshot.child("category").getValue(String.class);
                    Integer tempImageRes = snapshot.child("imageRes").getValue(Integer.class);
                    String tempDescription = snapshot.child("description").getValue(String.class);
                    int tempLike = snapshot.child("like").getValue(Integer.class) != null ? snapshot.child("like").getValue(Integer.class) : 0;
                    Pose pose = new Pose(tempCategory, tempName, tempImageRes != 0 ? tempImageRes : R.drawable.resource_default, tempDescription);
                    poseList.add(pose);
                }
                if (movementAdapter != null) {
                    movementAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("DatabaseError", error.getMessage());
            }
        });
        Intent addNewMove = new Intent(this, MovementNewCustom.class);
        Intent goBack = new Intent(this, MoveMain.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(movementAdapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(addNewMove);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(goBack);
            }
        });
    }
}

