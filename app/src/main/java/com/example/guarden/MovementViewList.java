package com.example.guarden;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MovementViewList extends AppCompatActivity {
    Button add;
    ImageButton back;
    private DatabaseReference databaseReference;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movement_view_list);
        add = (Button) findViewById(R.id.add);
        back = (ImageButton) findViewById(R.id.move_back);
        RecyclerView recycler = findViewById(R.id.recycler);
        ArrayList<Pose> poseList = new ArrayList<Pose>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        poseList.add(new Pose("yoga","Lunge",R.drawable.pose1,""));
        poseList.add(new Pose("yoga","Triangle",R.drawable.pose2,""));
        poseList.add(new Pose("yoga","Forward Fold",R.drawable.pose3,""));
        poseList.add(new Pose("exercise","Push Up",R.drawable.exercise1,""));
        poseList.add(new Pose("exercise","Sit Up",R.drawable.exercise2,""));
        poseList.add(new Pose("exercise","Squat",R.drawable.exercise3,""));
        //Pose pose = new Pose(databaseReference.child("joseph").getCustomPoses());
        //poseList.add(new Pose(
        Intent addNewMove = new Intent(this, MovementNewCustom.class);
        Intent goBack = new Intent(this, MoveMain.class);
        MovementAdapter movementAdapter = new MovementAdapter(this, poseList);
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

