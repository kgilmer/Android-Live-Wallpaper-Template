package com.example.livewallpaper.model;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Scene implements SceneModel {

    // animation specific variables
    private float outerCircleRadius;
    private float circleRadius;

    private int centerX;
    private int centerY;

    private int circleX;
    private int circleY;

    private float angle;

    @Override
    public synchronized void onSurfaceChanged(int width, int height) {

        centerX = width / 2;
        centerY = height / 2;

        int size = (width < height) ? width : height;

        outerCircleRadius = size / 3;
        circleRadius = outerCircleRadius * 0.2f;

        update();

    }

    @Override
    public synchronized void update() {

        angle += 1.0f;
        if (angle > 360f) {
            angle -= 360f;
        }

        circleX = (int) (centerX - outerCircleRadius * Math.cos(Math.toRadians(angle)));
        circleY = (int) (centerY - outerCircleRadius * Math.sin(Math.toRadians(angle)));

    }

    /*
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
    */

    @Override
    public float outerCircleRadius() {
        return outerCircleRadius;
    }

    @Override
    public float getCenterX() {
        return centerX;
    }

    @Override
    public float getCenterY() {
        return centerY;
    }

    @Override
    public float circleY() {
        return circleY;
    }

    @Override
    public float circleX() {
        return circleX;
    }

    @Override
    public float circleRadius() {
        return circleRadius;
    }
}
