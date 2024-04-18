package com.example.guarden;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Settings extends AppCompatActivity {
    private static final String PROGRESS_KEY =  "progress";
    private static final String DARK_MODE_KEY = "dark_mode";
    private static final String NOTIF_KEY = "notif";
    ImageButton arrows;
    TextView log_out;
    TextView aboutText, privacyPolicyText, termsOfUseText;

    ImageButton back_button;
    SharedPreferences prefs;
    androidx.appcompat.widget.SwitchCompat dark_mode;
    androidx.appcompat.widget.SwitchCompat notifications;
    private DatabaseReference userRef;

    SeekBar sound_bar;
    public Settings() {
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //initialize buttons
        dark_mode = (androidx.appcompat.widget.SwitchCompat) findViewById(R.id.Dark_Mode_Switch);
        notifications = (androidx.appcompat.widget.SwitchCompat) findViewById(R.id.NotificationSwitch);
        sound_bar = (SeekBar) findViewById(R.id.seekBar);
        log_out = (TextView) findViewById((R.id.Logout));
        back_button = (ImageButton) findViewById(R.id.Back_Button);
        aboutText = findViewById(R.id.About);
        privacyPolicyText = findViewById(R.id.PrivacyPolicy);
        termsOfUseText = findViewById(R.id.TermsOfUse);

        userRef = FirebaseDatabase.getInstance().getReference("users");

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

        aboutText.setOnClickListener(v -> startActivity(new Intent(this, About.class)));
        privacyPolicyText.setOnClickListener(v -> startActivity(new Intent(this, PrivacyPolicy.class)));
        termsOfUseText.setOnClickListener(v -> startActivity(new Intent(this, TermsOfUse.class)));

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
                SaveUser.clearUserName(Settings.this);
                Login.skipFlag = false;
                Intent LogIn = new Intent(Settings.this, Login.class);
                startActivity(LogIn);
            }
        });
    }
}