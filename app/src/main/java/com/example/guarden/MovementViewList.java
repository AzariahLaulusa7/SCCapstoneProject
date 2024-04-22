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
    Intent goBack;
    static MovementAdapter movementAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movement_view_list);
        add = (Button) findViewById(R.id.add);
        back = (ImageButton) findViewById(R.id.backButton);
        RecyclerView recycler = findViewById(R.id.recycler);
        Intent addNewMove = new Intent(this, MovementNewCustom.class);
        goBack = new Intent(this, MoveMain.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        movementAdapter = new MovementAdapter(this, MoveMain.poseList);
        recycler.setAdapter(movementAdapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(addNewMove);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(goBack);
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(goBack);
    }

}

