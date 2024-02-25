package com.example.guarden;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class HomeScreen extends AppCompatActivity {

//Commenting to commit and be a contributor
//Worked on home page and settings
    ImageButton journal;
    ImageButton games;
    ImageButton breath;
    ImageButton forums;
    ImageButton add;

    ImageButton profile;

    ImageButton call;
    ImageButton move;
    ImageButton settings;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_home);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        move = (ImageButton) findViewById(R.id.Move);
        journal = (ImageButton) findViewById(R.id.Journal);
        games = (ImageButton) findViewById(R.id.Games);
        breath = (ImageButton) findViewById(R.id.Breath);
        forums = (ImageButton) findViewById(R.id.Forums);
        add = (ImageButton) findViewById(R.id.Add);
        profile = (ImageButton) findViewById(R.id.Profile);
        call = (ImageButton) findViewById(R.id.Call);
        settings = (ImageButton) findViewById(R.id.Settings);

        move.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent MoveMain = new Intent(HomeScreen.this, MoveMain.class);
                startActivity(MoveMain);
            }
        });


        breath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BreatheMainActivity = new Intent(HomeScreen.this, BreatheMain.class);
                startActivity(BreatheMainActivity);
            }});
          
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent Settings = new Intent(HomeScreen.this, Settings.class);
                startActivity(Settings);
            }
        });
      
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent EditProfile = new Intent(HomeScreen.this, EditProfile.class);
                startActivity(EditProfile);
            }
        });

        journal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent NewJournalEntry = new Intent(HomeScreen.this, NewJournalEntry.class);
                startActivity(NewJournalEntry);
            }
        });

        games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, GameHome.class);
                startActivity(intent);
            }
        });

        forums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Forums = new Intent(HomeScreen.this, Forums.class);
                startActivity(Forums);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, AddWidget.class);
                startActivity(intent);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, CrisisLines.class);
                startActivity(intent);
            }
        });
    }


}
