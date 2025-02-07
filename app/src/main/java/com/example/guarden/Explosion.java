package com.example.guarden;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//This class is not used
public class Explosion {
    Bitmap explosion[] = new Bitmap[3];
    int explosionFrame = 0;
    int explosionX, explosionY;

    public Explosion(Context context) {
        explosion[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion1);
        explosion[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion2);
        explosion[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion3);
    }

    public Bitmap getExplosion(int explosionFrame) {
        return explosion[explosionFrame];
    }
}
