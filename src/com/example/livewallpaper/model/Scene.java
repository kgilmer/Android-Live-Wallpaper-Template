package com.example.livewallpaper.model;

import com.example.livewallpaper.theme.WallpaperTheme;


public class Scene {

    private float outerCircleRadius;
    private float circleRadius;

    private int centerX;
    private int centerY;

    private int circleX;
    private int circleY;

    private float angle;
    private WallpaperTheme theme;
    private int width;
    private int height;
    
    public Scene(WallpaperTheme theme) {
        this.theme = theme;
    }

    
    public synchronized void onSurfaceChanged(int width, int height) {

        this.width = width;
        this.height = height;
        
        adjustScene();
    }

    public synchronized void onThemeChanged(WallpaperTheme theme) {
        this.theme = theme;
        
        adjustScene();
    }
    
    /**
     * 
     */
    private void adjustScene() {
        centerX = width / 2;
        centerY = height / 2;

        int size = (width < height) ? width : height;

        outerCircleRadius = size / 3;
        circleRadius = outerCircleRadius * theme.getCircleSize();

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

    
    public float outerCircleRadius() {
        return outerCircleRadius;
    }

    
    public float getCenterX() {
        return centerX;
    }

    
    public float getCenterY() {
        return centerY;
    }

    
    public float circleY() {
        return circleY;
    }

    
    public float circleX() {
        return circleX;
    }

    
    public float circleRadius() {
        return circleRadius;
    }
}
