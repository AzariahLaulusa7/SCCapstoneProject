package com.example.guarden;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private LayerDrawable layerDrawable;
    private boolean darkModeButton;
    ImageButton arrows;
    TextView log_out;
    TextView aboutText, privacyPolicyText, termsOfUseText;

    ImageButton back_button;
    SharedPreferences prefs;
    androidx.appcompat.widget.SwitchCompat dark_mode;
    androidx.appcompat.widget.SwitchCompat notifications;
    boolean notifButton;
    private DatabaseReference userRef;
    private Object ActivityLifecycleCallbacks;

    public Settings() {
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //initialize buttons
        dark_mode = (SwitchCompat) findViewById(R.id.Dark_Mode_Switch);
        notifications = (SwitchCompat) findViewById(R.id.NotificationSwitch);
        log_out = (TextView) findViewById((R.id.Logout));
        back_button = (ImageButton) findViewById(R.id.Back_Button);
        aboutText = findViewById(R.id.About);
        privacyPolicyText = findViewById(R.id.PrivacyPolicy);
        termsOfUseText = findViewById(R.id.TermsOfUse);

        userRef = FirebaseDatabase.getInstance().getReference("users");

        //initialize sp
        prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);

        //get sp for dark_mode
        darkModeButton = prefs.getBoolean(DARK_MODE_KEY, false);
        dark_mode.setChecked(darkModeButton);

        //get sp for notifications
        if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
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
            LayerDrawable layerDrawable = (LayerDrawable) ContextCompat.getDrawable(this, R.drawable.background_image);
            BitmapDrawable secondDrawable = (BitmapDrawable) layerDrawable.findDrawableByLayerId(R.id.background_light);
            View s = findViewById(R.id.settings);
            if(isChecked) {
                secondDrawable.setAlpha(0);
                s.setBackground(layerDrawable);
            } else {
                secondDrawable.setAlpha(255);
                s.setBackground(layerDrawable);
            }
            editor.apply();
        });

        notifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkNotifPermission();
            SharedPreferences.Editor editor = prefs.edit();
            if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                editor.putBoolean(NOTIF_KEY, isChecked);
            } else {
                editor.putBoolean(NOTIF_KEY, false);
            }
            editor.apply();
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