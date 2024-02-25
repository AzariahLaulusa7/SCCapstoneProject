package com.example.guarden;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {
    private static final String PROGRESS_KEY =  "progress";
    private static final String DARK_MODE_KEY = "dark_mode";
    private static final String NOTIF_KEY = "notif";
    ImageButton arrows;
    ImageButton log_out;
    ImageButton back_button;
    SharedPreferences prefs;
    androidx.appcompat.widget.SwitchCompat dark_mode;
    androidx.appcompat.widget.SwitchCompat notifications;

    SeekBar sound_bar;
    public Settings() {
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //initialize buttons
        arrows = (ImageButton) findViewById(R.id.Arrows);
        dark_mode = (androidx.appcompat.widget.SwitchCompat) findViewById(R.id.Dark_Mode_Switch);
        notifications = (androidx.appcompat.widget.SwitchCompat) findViewById(R.id.NotificationSwitch);
        sound_bar = (SeekBar) findViewById(R.id.seekBar);
        log_out = (ImageButton) findViewById((R.id.LogOut));
        back_button = (ImageButton) findViewById(R.id.Back_Button);

        //initialize sp
        prefs = getPreferences(Context.MODE_PRIVATE);

        //get sp for sound_bar
        int newProgress = prefs.getInt(PROGRESS_KEY, 0);
        sound_bar.setProgress(newProgress);

        //get sp for dark_mode
        boolean darkModeButton = prefs.getBoolean(DARK_MODE_KEY, true);
        dark_mode.setChecked(darkModeButton);

        //get sp for notifications
        boolean notifButton = prefs.getBoolean(NOTIF_KEY, true);
        notifications.setChecked(notifButton);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MoveMain = new Intent(Settings.this, HomeScreen.class);
                startActivity(MoveMain);
            }
        });

        dark_mode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(DARK_MODE_KEY, isChecked);
            editor.apply();
        });

        notifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(NOTIF_KEY, isChecked);
            editor.apply();
        });

        sound_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SharedPreferences.Editor editor  = prefs.edit();
                editor.putInt(PROGRESS_KEY, progress);
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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