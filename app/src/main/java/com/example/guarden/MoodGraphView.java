package com.example.guarden;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public class MoodGraphView extends View {
    private int[] moodValues;
    private Bitmap sadFace, okFace, happyFace;

    public MoodGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Resources res = getResources();
        sadFace = getBitmapFromVectorDrawable(getContext(), R.drawable.baseline_mood_bad_24);
        okFace = getBitmapFromVectorDrawable(getContext(), R.drawable.baseline_face_24);
        happyFace = getBitmapFromVectorDrawable(getContext(), R.drawable.baseline_mood_24);
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = AppCompatResources.getDrawable(context, drawableId);
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawableCompat || drawable instanceof VectorDrawable) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            throw new IllegalArgumentException("Unsupported drawable type");
        }
    }


    public void setMoodValues(int[] values) {
        this.moodValues = values;
        invalidate(); // Redraw the view as the data has changed
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (moodValues == null) return;

        float width = getWidth();
        float height = getHeight();
        float dayWidth = width / 7f;

        for (int i = 0; i < moodValues.length; i++) {
            if (moodValues[i] >= 0) {
                float x = (i * dayWidth) + (dayWidth / 2f);
                // Adjust y based on the mood
                float y;
                Bitmap moodBitmap;
                switch (moodValues[i]) {
                    case 0: // Sad
                        y = height * 0.75f;
                        moodBitmap = sadFace;
                        break;
                    case 1: // OK
                        y = height * 0.5f;
                        moodBitmap = okFace;
                        break;
                    case 2: // Happy
                        y = height * 0.25f;
                        moodBitmap = happyFace;
                        break;
                    default:
                        continue; // Skip if the mood value is not recognized
                }
                // Center the image on x and y
                x -= moodBitmap.getWidth() / 2f;
                y -= moodBitmap.getHeight() / 2f;

                canvas.drawBitmap(moodBitmap, x, y, null);
            }
        }
    }
}
