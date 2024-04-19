package com.example.guarden;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
    public static final String ALARM_KEY = "alarm";
    private static final int NOTIF_REQUEST_CODE = 123;
    private static final String PROGRESS_KEY =  "progress";
    private static final String DARK_MODE_KEY = "dark_mode";
    public static final String NOTIF_KEY = "notif";
    ImageButton arrows;
    TextView log_out;
    TextView aboutText, privacyPolicyText, termsOfUseText;

    ImageButton back_button;
    SharedPreferences prefs;
    androidx.appcompat.widget.SwitchCompat dark_mode;
    androidx.appcompat.widget.SwitchCompat notifications;
    boolean notifButton;
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
        prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);

        //get sp for sound_bar
        int newProgress = prefs.getInt(PROGRESS_KEY, 0);
        sound_bar.setProgress(newProgress);

        //get sp for dark_mode
        boolean darkModeButton = prefs.getBoolean(DARK_MODE_KEY, false);
        dark_mode.setChecked(darkModeButton);

        //get sp for notifications
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notifButton = prefs.getBoolean(NOTIF_KEY, true);
        } else {
            notifButton = prefs.getBoolean(NOTIF_KEY, false);
        }
        notifications.setChecked(notifButton);

        aboutText.setOnClickListener(v -> startActivity(new Intent(this, About.class)));
        privacyPolicyText.setOnClickListener(v -> startActivity(new Intent(this, PrivacyPolicy.class)));
        termsOfUseText.setOnClickListener(v -> startActivity(new Intent(this, TermsOfUse.class)));

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home = new Intent(Settings.this, HomeScreen.class);
                startActivity(Home);
                finish();
            }
        });

        dark_mode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(DARK_MODE_KEY, isChecked);
            editor.apply();
        });

        notifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkNotifPermission();
            SharedPreferences.Editor editor = prefs.edit();
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                editor.putBoolean(NOTIF_KEY, isChecked);
            } else {
                editor.putBoolean(NOTIF_KEY, false);
            }
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
                finish();
            }
        });
    }
    public void checkNotifPermission() {
        if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{POST_NOTIFICATIONS}, NOTIF_REQUEST_CODE);
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