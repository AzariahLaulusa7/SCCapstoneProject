package com.example.guarden;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.app.Activity;
import android.view.Display;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;


public class Movement extends AppCompatActivity {
    public int counter;
    TextView timer;
    Button start;
    Drawable d;
    ImageView pose;
    Context context;
    private int[] yogaPoses = {
            R.drawable.pose1,
            R.drawable.pose2,
            R.drawable.pose3,
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movement);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        start = (Button) findViewById(R.id.start);
        timer = (TextView) findViewById(R.id.timer);
        pose = (ImageView) findViewById(R.id.pose);
        final TextView counttime=findViewById(R.id.timer);
        new CountDownTimer(50000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                counttime.setText(String.valueOf(counter));
                counter++;
            }
            @Override
            public void onFinish() {
                counttime.setText("Finished");
            }
        }.start();
    }
}
