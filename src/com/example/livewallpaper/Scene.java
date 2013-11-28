package com.example.livewallpaper;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Scene implements OnSharedPreferenceChangeListener {

    private static final int DEFAULT_OUTER_CIRCLE_COLOR = 0xff5e736d;
    private static final int DEFAULT_CIRCLE_COLOR = 0xffa2bd3a;
    private final Paint backgroundPaint;
    private final Paint outerCirclePaint;
    private final Paint circlePaint;

    // animation specific variables
    private float outerCircleRadius;
    private float circleRadius;

    private int centerX;
    private int centerY;

    private int circleX;
    private int circleY;

    private float angle;

    public Scene() {

        backgroundPaint = new Paint();
        backgroundPaint.setColor(0xff8aa8a0);

        outerCirclePaint = new Paint();
        outerCirclePaint.setAntiAlias(true);
        outerCirclePaint.setColor(DEFAULT_OUTER_CIRCLE_COLOR);
        outerCirclePaint.setStyle(Style.STROKE);
        outerCirclePaint.setStrokeWidth(3.0f);

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(DEFAULT_CIRCLE_COLOR);
        circlePaint.setStyle(Style.FILL);

    }

    public synchronized void updateSize(int width, int height) {

        centerX = width / 2;
        centerY = height / 2;

        int size = (width < height) ? width : height;

        outerCircleRadius = size / 3;
        circleRadius = outerCircleRadius * 0.2f;

        update();

    }

    public synchronized void update() {

        angle += 1.0f;
        if (angle > 360f) {
            angle -= 360f;
        }

        circleX = (int) (centerX - outerCircleRadius * Math.cos(Math.toRadians(angle)));
        circleY = (int) (centerY - outerCircleRadius * Math.sin(Math.toRadians(angle)));

    }

    public synchronized void draw(Canvas canvas) {

        // clear the background
        canvas.drawPaint(backgroundPaint);

        // draw objects
        canvas.drawCircle(centerX, centerY, outerCircleRadius, outerCirclePaint);
        canvas.drawCircle(centerX, centerY, 5f, circlePaint);
        canvas.drawCircle(circleX, circleY, circleRadius, circlePaint);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("theme_color")) {
            if (sharedPreferences.getString(key, "Default").equals("Pink")) {
                outerCirclePaint.setColor(Color.RED);
                circlePaint.setColor(Color.MAGENTA);
            } else {
                outerCirclePaint.setColor(DEFAULT_OUTER_CIRCLE_COLOR);
                circlePaint.setColor(DEFAULT_CIRCLE_COLOR);
            }
        }
    }

}
