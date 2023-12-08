package com.example.guarden;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {

    Handler h = new Handler();
    ImageButton arrows;

    androidx.appcompat.widget.SwitchCompat dark_mode;

    androidx.appcompat.widget.SwitchCompat notifications;

    SeekBar sound_bar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        arrows = (ImageButton) findViewById(R.id.Arrows);
        dark_mode = (androidx.appcompat.widget.SwitchCompat) findViewById(R.id.Dark_Mode_Switch);
        notifications = (androidx.appcompat.widget.SwitchCompat) findViewById(R.id.NotificationSwitch);
        sound_bar = (SeekBar) findViewById(R.id.seekBar);
        ImageButton log_out = (ImageButton) findViewById((R.id.LogOut));
        ImageButton back_button = (ImageButton) findViewById(R.id.Back_Button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MoveMain = new Intent(Settings.this, HomeScreen.class);
                startActivity(MoveMain);
            }
        });

        dark_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do nothing
            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do nothing
            }
        });

        sound_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do nothing
            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent LogIn = new Intent(Settings.this, Login.class);
                startActivity(LogIn);
            }
        });
    }
}