package com.example.guarden;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
//This class is not used
public class GameOver extends AppCompatActivity {

    TextView tvPoints;
    TextView tvHighest;
    SharedPreferences sharedPreferences;
    ImageView ivNewHighest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        tvPoints = findViewById(R.id.tvPoints);
        tvHighest = findViewById(R.id.tvHighest);
        ivNewHighest = findViewById(R.id.ivNewHighest); // Initialize ivNewHighest

        int points = getIntent().getExtras().getInt("points");
        tvPoints.setText(String.valueOf(points));

        sharedPreferences = getSharedPreferences("my_pref", 0);
        int highest = sharedPreferences.getInt("highest", 0);

        if (points > highest) {
            ivNewHighest.setVisibility(View.VISIBLE);
            highest = points;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("highest", highest);
            editor.apply(); // Use apply() instead of commit()
        }

        tvHighest.setText(String.valueOf(highest));
    }

    public void restart(View view) {
        /**
        Initializes intent which is used by startActivity to jump to the MainActivity class and activity_main.xml layout
         so that the user can start again.
         */
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view) {
        finish();
    }
}
