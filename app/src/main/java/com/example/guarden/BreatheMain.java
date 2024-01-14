package com.example.guarden;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Vibrator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class BreatheMain extends AppCompatActivity {

    TextView breatheInText;
    TextView breatheOutText;
    TextView holdText;
    TextView mouthText;
    TextView noseText;
    TextView relaxText;
    Button start;
    ImageButton back;
    ObjectAnimator grow;
    ImageView circle;
    ImageView pose;
    Handler handler;
    Runnable runnable;
    Vibrator vibrate;
    boolean ignore;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.breathe_main);

        start = (Button) findViewById(R.id.start_breathing);
        circle = (ImageView) findViewById(R.id.circle);
        // yoga pose icon from "https://www.flaticon.com/free-icon/yoga-pose_4646080?term=yoga+pose&page=2&position=73&origin=search&related_id=4646080"
        pose = (ImageView) findViewById(R.id.yoga_pose);
        back = (ImageButton) findViewById(R.id.breathe_back);
        breatheInText = (TextView) findViewById(R.id.breathe_in);
        breatheOutText = (TextView) findViewById(R.id.breathe_out);
        holdText = (TextView) findViewById(R.id.hold);
        mouthText = (TextView) findViewById(R.id.mouth);
        noseText = (TextView) findViewById(R.id.nose);
        relaxText = (TextView) findViewById(R.id.relax);
        handler = new Handler();
        vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        ignore = false;

        Intent myIntent = new Intent(this, HomeScreen.class);

        breatheInText.setVisibility(View.GONE);
        breatheOutText.setVisibility(View.GONE);
        holdText.setVisibility(View.GONE);
        mouthText.setVisibility(View.GONE);
        noseText.setVisibility(View.GONE);
        relaxText.setVisibility(View.GONE);

        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                start.setVisibility(View.GONE);
                /*grow = grow.ofPropertyValuesHolder(circle, PropertyValuesHolder.ofFloat("scaleX", 3f),
                        PropertyValuesHolder.ofFloat("scaleY", 3f));
                grow.setDuration(2500);
                grow.setRepeatCount(ValueAnimator.INFINITE);
                breatheOutText.setVisibility(View.GONE);
                holdText.setVisibility(View.GONE);
                 */
                infiniteAnim();
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ignore();
                handler.removeCallbacks(runnable);
                vibrate.cancel();
                startActivity(myIntent);
            }
        });
    }

    public void infiniteAnim() {

        runnable = new Runnable() {
            @Override
            public void run() {
                circleAnim();
                handler.postDelayed(runnable, 11500);
            }
        };

        handler.post(runnable);
    }

    public void holdAnim() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!ignore)
                    vibrate.vibrate(500);
                breatheInText.setVisibility(View.GONE);
                noseText.setVisibility(View.GONE);
                grow.end();
                holdText.setVisibility(View.VISIBLE);
            }
        }, 3000);
    }

    public void reverseAnim() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!ignore)
                    vibrate.vibrate(500);
                holdText.setVisibility(View.GONE);
                //grow.end();
                breatheOutText.setVisibility(View.VISIBLE);
                mouthText.setVisibility(View.VISIBLE);
                grow.reverse();
            }
        }, 5500);
    }

    public void secondHold() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!ignore)
                    vibrate.vibrate(500);
                breatheOutText.setVisibility(View.GONE);
                mouthText.setVisibility(View.GONE);
                holdText.setVisibility(View.VISIBLE);
            }
        }, 8400);
    }

    public void animEnd() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                holdText.setVisibility(View.GONE);
                //start.setVisibility(View.VISIBLE);
            }
        }, 11500);
    }

    public void circleAnim() {
        grow = grow.ofPropertyValuesHolder(circle, PropertyValuesHolder.ofFloat("scaleX", 3f),
                PropertyValuesHolder.ofFloat("scaleY", 3f));
        grow.setDuration(2500);
        grow.setRepeatCount(0);
        breatheOutText.setVisibility(View.GONE);
        holdText.setVisibility(View.GONE);
        breatheInText.setVisibility(View.VISIBLE);
        noseText.setVisibility(View.VISIBLE);
        grow.start();
        if(!ignore)
            vibrate.vibrate(500);
        holdAnim();
        reverseAnim();
        secondHold();
        animEnd();
    }

    public void ignore() {
        ignore = true;
    }

}
