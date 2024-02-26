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

import java.util.ArrayList;

public class MovementViewList extends AppCompatActivity {
    Button add;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movement_view_list);
        add = (Button) findViewById(R.id.add);
        RecyclerView recycler = findViewById(R.id.recycler);
        ArrayList<Pose> poseList = new ArrayList<Pose>();
        poseList.add(new Pose("yoga","Lunge",R.drawable.pose1));
        poseList.add(new Pose("yoga","Triangle",R.drawable.pose2));
        poseList.add(new Pose("yoga","Forward Fold",R.drawable.pose3));
        poseList.add(new Pose("exercise","Push Up",R.drawable.exercise1));
        poseList.add(new Pose("exercise","Sit Up",R.drawable.exercise2));
        poseList.add(new Pose("exercise","Squat",R.drawable.exercise3));
        Intent addNewMove = new Intent(this, MovementNewCustom.class);
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
    }
}

