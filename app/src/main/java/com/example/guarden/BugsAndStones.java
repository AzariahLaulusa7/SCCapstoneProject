package com.example.guarden;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

//This class is not used
public class BugsAndStones {
    Bitmap badObj[] = new Bitmap[4];
    int badObjFrame = 0;
    int badObjX, badObjY, badObjVelocity;
    Random random;

    public BugsAndStones(Context context) {
        badObj[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rock1);
        badObj[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rock2);
        badObj[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.bug1);
        badObj[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.bug2);
        random = new Random();
        resetPosition();
    }

    public Bitmap getBadObj(int objFrame) {
        return badObj[objFrame];
    }

    public int getBadObjWidth() {
        return badObj[0].getWidth();
    }

    public int getBadObjHeight() {
        return  badObj[0].getHeight();
    }

    public void resetPosition() {
        badObjX = random.nextInt( GameView.dWidth - getBadObjWidth());
        badObjY = -200 + random.nextInt(600) * -1;
        badObjVelocity = 35 + random.nextInt(16);
    }
}
