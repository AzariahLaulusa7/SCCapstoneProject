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
    ImageButton back;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.move_main);
        yoga = (Button) findViewById(R.id.yoga);
        exercise = (Button) findViewById(R.id.exercise);
        back = (ImageButton) findViewById(R.id.move_back);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Intent myIntent = new Intent(this, Movement.class);
        Intent myIntent2 = new Intent(this, HomeScreen.class);
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
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(myIntent2);
            }
        });
    }
}

