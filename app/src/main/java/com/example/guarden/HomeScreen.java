package com.example.guarden;

import static android.Manifest.permission.POST_NOTIFICATIONS;
import static android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM;

import android.app.AlarmManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.PowerManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeScreen extends AppCompatActivity {

//Commenting to commit and be a contributor
//Worked on home page and settings
private static final String PREF_NAME = "PermissionPrefs";
    private static final String PREF_ALARM_PERMISSION_REQUESTED = "alarm_permission_requested";

    private static final int NOTIF_REQUEST_CODE = 123;
    private SharedPreferences prefs;
    ImageButton journal;
    ImageButton games;
    ImageButton breath;
    ImageButton forums;

    ImageButton profile;

    ImageButton call;
    ImageButton move;
    ImageButton settings;
    TextView helloText;
    public boolean isAppInForeground;


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
        prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        checkNotifPermission();
        move.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent MoveMain = new Intent(HomeScreen.this, MoveMain.class);
                startActivity(MoveMain);
                finish();
            }
        });


        breath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BreatheMainActivity = new Intent(HomeScreen.this, BreatheMain.class);
                startActivity(BreatheMainActivity);
                finish();
            }});
          
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent Settings = new Intent(HomeScreen.this, Settings.class);
                startActivity(Settings);
                finish();
            }
        });
      
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent EditProfile = new Intent(HomeScreen.this, EditProfile.class);
                startActivity(EditProfile);
                finish();
            }
        });

        journal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent NewJournalEntry = new Intent(HomeScreen.this, NewJournalEntry.class);
                startActivity(NewJournalEntry);
                finish();
            }
        });

        games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, GameHome.class);
                startActivity(intent);
                finish();
            }
        });

        forums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Forums = new Intent(HomeScreen.this, ForumMain.class);
                startActivity(Forums);
                finish();
            }
        });


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, CrisisLines.class);
                startActivity(intent);
                finish();
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
    public void checkNotifPermission() {
        if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{POST_NOTIFICATIONS}, NOTIF_REQUEST_CODE);
            Settings s = new Settings();
            s.notifButton = prefs.getBoolean(Settings.NOTIF_KEY, true);
        } else {
            prefs.edit().putBoolean("notif_enabled", true).apply();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIF_REQUEST_CODE) {
            prefs.edit().putBoolean("notif_enabled", grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED).apply();
        }
    }
}
