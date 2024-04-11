package com.example.guarden;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;


public class MoveMain extends AppCompatActivity {
    Button yoga;
    Button exercise;
    Button viewAll;
    ImageButton back;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.move_main);
        NotificationScheduler.setRecentView("move_main");
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
                finish();
            }
        });
    }
}

