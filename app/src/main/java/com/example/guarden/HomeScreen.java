package com.example.guarden;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;

public class HomeScreen extends AppCompatActivity {

//Commenting to commit and be a contributor
//Worked on home page and settings
    ImageButton journal;
    ImageButton games;
    ImageButton breath;
    ImageButton forums;

    ImageButton profile;

    ImageButton call;
    ImageButton move;
    ImageButton settings;
    TextView helloText;
    ImageButton charts;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_home);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        move = (ImageButton) findViewById(R.id.Move);
        journal = (ImageButton) findViewById(R.id.Journal);
        games = (ImageButton) findViewById(R.id.Games);
        breath = (ImageButton) findViewById(R.id.Breath);
        forums = (ImageButton) findViewById(R.id.Forums);
        profile = (ImageButton) findViewById(R.id.Profile);
        call = (ImageButton) findViewById(R.id.Call);
        settings = (ImageButton) findViewById(R.id.Settings);
        charts = (ImageButton) findViewById(R.id.charts);


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

        charts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, ChartsActivity.class);
                startActivity(intent);
            }
        });
          
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
                Intent Forums = new Intent(HomeScreen.this, ForumMain.class);
                startActivity(Forums);
            }
        });


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, CrisisLines.class);
                startActivity(intent);
            }
        });

        // Edit hello
        helloText = findViewById(R.id.textView2);
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
        String key = Login.emailKey;

        if(key == null)
            key = " ";
        userRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null)
                    helloText.setText("Hello " + user.firstName.toUpperCase() + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled
            }
        });
    }

    private static class User {
        public String email, password, firstName, lastName, image;

        public User() {}

        public User(String email, String password, String firstName, String lastName) {
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public User(String email, String password, String firstName, String lastName, String image) {
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.image = image;
        }
    }


}
