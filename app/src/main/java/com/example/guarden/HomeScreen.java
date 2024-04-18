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
import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

//Commenting to commit and be a contributor
//Worked on home page and settings

    static String key;
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
    DatabaseReference userRef;


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
        userRef = FirebaseDatabase.getInstance().getReference("users");

        if(SaveUser.getUserName(HomeScreen.this).length() == 0 && !Login.skipFlag)
        {
            Intent intent = new Intent(HomeScreen.this, Login.class);
            startActivity(intent);
        }

        if (Login.skipFlag) {
            if (SaveUser.getUserName(HomeScreen.this).length() == 0) {
                String uniqueKey = userRef.push().getKey();
                String name = "GUEST";
                User newUser = new User("", "", name, "", new ArrayList<>());
                userRef.child(uniqueKey).setValue(newUser)
                        .addOnSuccessListener(aVoid -> {
                            addDefaultPosesToList(uniqueKey);
                        });
                SaveUser.setUserName(HomeScreen.this, uniqueKey);
            }
        }

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
                Intent ViewJournalEntries = new Intent(HomeScreen.this, ViewJournalEntries.class);
                startActivity(ViewJournalEntries);
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
        key = SaveUser.getUserName(HomeScreen.this);

        if(key == null)
            key = " ";
        userRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null && user.firstName != null)
                        helloText.setText("Hello " + user.firstName.toUpperCase() + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled
            }
        });
    }

    public void addDefaultPosesToList(String email){
        Pose lunge = new Pose("yoga","Lunge",R.drawable.pose1,"", 0);
        userRef.child(email).child("customPoses").child("Lunge").setValue(lunge);
        Pose triangle = new Pose("yoga","Triangle",R.drawable.pose2,"", 0);
        userRef.child(email).child("customPoses").child("Triangle").setValue(triangle);
        Pose forwardFold = new Pose("yoga","Forward Fold",R.drawable.pose3,"", 0);
        userRef.child(email).child("customPoses").child("Forward Fold").setValue(forwardFold);
        Pose pushUp = new Pose("exercise","Push Up",R.drawable.exercise1,"", 0);
        userRef.child(email).child("customPoses").child("Push Up").setValue(pushUp);
        Pose sitUp = new Pose("exercise","Sit Up",R.drawable.exercise2,"", 0);
        userRef.child(email).child("customPoses").child("Sit Up").setValue(sitUp);
        Pose squat = new Pose("exercise","Squat",R.drawable.exercise3,"", 0);
        userRef.child(email).child("customPoses").child("Squat").setValue(squat);
    }

    private static class User {
        public String email, password, firstName, lastName;
        private ArrayList<Pose> customPoses;

        public User() {}

        public User(String email, String password, String firstName, String lastName) {
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public User(String email, String password, String firstName, String lastName, ArrayList<Pose> customPoses) {
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.customPoses = customPoses;
        }
    }


}
