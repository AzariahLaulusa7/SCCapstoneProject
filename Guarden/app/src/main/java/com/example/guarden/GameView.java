package com.example.guarden;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {

    Bitmap background, ground, plant;
    Rect rectBackground, rectGround;
    Context context;
    Handler handler;
    final long UPDATE_MILLIS = 30;
    Runnable runnable;
    Paint textPaint = new Paint();
    Paint healthPaint = new Paint();
    float TEXT_SIZE = 120;
    int points = 0;
    int life = 3;
    static int dWidth, dHeight;
    Random random;
    float plantX, plantY;
    float oldX;
    float oldPlantX;
    ArrayList<BugsAndStones> badObj;
    ArrayList<Explosion> explosions;

    public GameView(Context context) {
        super(context);
        this.context = context;
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        ground = BitmapFactory.decodeResource(getResources(), R.drawable.ground);
        plant = BitmapFactory.decodeResource(getResources(), R.drawable.plant);
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size =  new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        rectBackground = new Rect(0,0,dWidth,dHeight);
        rectGround = new Rect(0, dHeight - ground.getHeight(), dWidth, dHeight);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                invalidate();
            }
        };
        textPaint.setColor(Color.rgb(255, 165, 0));
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTypeface(ResourcesCompat.getFont(context, R.font.kenny_blocks));
        healthPaint.setColor(Color.GREEN);
        random = new Random();
        plantX = dWidth/2 -plant.getWidth()/2;
        plantY = dHeight - ground.getHeight() - plant.getHeight();
        badObj = new ArrayList<>();
        explosions = new ArrayList<>();
        for(int i=0; i<3; i++) {
            BugsAndStones obj = new BugsAndStones(context);
            badObj.add(obj);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background, null, rectBackground, null);
        canvas.drawBitmap(ground, null, rectGround, null);
        canvas.drawBitmap(plant, plantX, plantY, null);
        for(int i=0; i<badObj.size(); i++)  {
            canvas.drawBitmap(badObj.get(i).getBadObj(badObj.get(i).badObjFrame), badObj.get(i).badObjX, badObj.get(i).badObjY, null);
            badObj.get(i).badObjY += badObj.get(i).badObjVelocity;
            if(badObj.get(i).badObjY + badObj.get(i).getBadObjHeight() >= dHeight - ground.getHeight()) {
                points += 10;
                Explosion explosion = new Explosion(context);
                explosion.explosionX = badObj.get(i).badObjX;
                explosion.explosionY = badObj.get(i).badObjY;
                explosions.add(explosion);
                badObj.get(i).badObjFrame = (badObj.get(i).badObjFrame + 1) % 4;
                badObj.get(i).resetPosition();
            }
        }

        for(int i=0; i < badObj.size(); i++) {
            if(badObj.get(i).badObjX + badObj.get(i).getBadObjWidth() >= plantX
            && badObj.get(i).badObjX <= plantX + plant.getWidth()
            && badObj.get(i).badObjY + badObj.get(i).getBadObjWidth() >= plantY
            && badObj.get(i).badObjY + badObj.get(i).getBadObjWidth() <= plantY + plant.getHeight()) {
                life--;
                badObj.get(i).resetPosition();
                if(life == 0) {
                    Intent intent = new Intent(context.getApplicationContext(), GameOver.class);
                    intent.putExtra("points", points);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            }
        }

        for(int i=0; i<explosions.size(); i++) {
            canvas.drawBitmap(explosions.get(i).getExplosion(explosions.get(i).explosionFrame), explosions.get(i).explosionX,
                    explosions.get(i).explosionY, null);
            explosions.get(i).explosionFrame++;
            if(explosions.get(i).explosionFrame > 2) {
                explosions.remove(i);
            }
        }

        if(life == 2) {
            healthPaint.setColor(Color.YELLOW);
        } else if(life == 1) {
            healthPaint.setColor(Color.RED);
        }
        canvas.drawRect(dWidth-200, 30, dWidth-200+60*life, 80, healthPaint);
        canvas.drawText("" + points, 20, TEXT_SIZE, textPaint);
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        if(touchY >= plantY) {
            int action = event.getAction();
            if(action == MotionEvent.ACTION_DOWN) {
                oldX = event.getX();
                oldPlantX = plantX;
            }
            if (action == MotionEvent.ACTION_MOVE) {

                float shift = oldX - touchX;
                float newPlantX = oldPlantX - shift;
                if(newPlantX <= 0)
                    plantX = 0;
                else if(newPlantX >= dWidth - plant.getWidth())
                    plantX = dWidth - plant.getWidth();
                else
                    plantX = newPlantX;
            }
        }
        return true;
    }
}
