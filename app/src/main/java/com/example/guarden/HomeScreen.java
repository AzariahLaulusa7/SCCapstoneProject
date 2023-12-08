package com.example.guarden;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class HomeScreen extends AppCompatActivity {


    ImageButton move;
    ImageButton journal;
    ImageButton games;
    ImageButton breath;
    ImageButton forums;
    ImageButton add;

    ImageButton profile;

    ImageButton call;

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
        Intent MoveMain = new Intent(this, MoveMain.class);
        Intent BreatheMainActivity = new Intent(this, BreatheMainActivity.class);
        //Intent name_of_games_class = new Intent(this, name_of_games_class.class);
        //Intent name_of_breath_class = new Intent(this, name_of_breath_class.class);
        //Intent name_of_forums_class = new Intent(this, name_of_forums_class.class);
        //Intent name_of_add_class = new Intent(this, name_of_add_class.class);
        //Intent name_of_profile_class = new Intent(this, name_of_profile_class.class);
        //Intent name_of_call_class = new Intent(this, name_of_call_class.class);
        //Intent name_of_settings_class = new Intent(this, name_of_settings_class.class);
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MoveMain);
            }
        });

        breath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(BreatheMainActivity); }
        });

        /*journal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(name_of_journal_class);
            }
        });

        games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(name_of_games_class);
            }
        });

        forums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(name_of_forums_class);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(name_of_add_class);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MoveMain);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(name_of_call_class);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(name_of_settings_class);
            }
        });*/
    }


}