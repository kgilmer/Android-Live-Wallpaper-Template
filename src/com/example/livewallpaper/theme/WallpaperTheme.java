/*******************************************************************************
 * Unpublished work, Copyright 2013, Clear Channel Communications, All Rights Reserved
 ******************************************************************************/

package com.example.livewallpaper.theme;

import android.content.SharedPreferences;
import android.graphics.Paint;

/**
 * Defines all the elements that are changeable by the user via settings.  The theme is available to the scene and the renderer.
 */
public abstract class WallpaperTheme {
    
    public static final float SMALL_CIRCLE_SIZE = 0.3f;
    public static final float LARGE_CIRCLE_SIZE = 0.6f;

    /**
     * @return
     */
    abstract public Paint backgroundPaint();

    /**
     * @return
     */
    abstract public Paint circlePaint();

    /**
     * @return
     */
    abstract public Paint outerCirclePaint();

    /**
     * @return
     */
    abstract public float getCircleSize();

    public static WallpaperTheme getTheme(SharedPreferences prefs) {
        
        if (prefs == null) {
            //Always return a theme regardless of input param.
            return new DefaultTheme(SMALL_CIRCLE_SIZE);
        }
        
        String colorPref = prefs.getString("theme_color", "Default");
        String circleSizePref = prefs.getString("theme_size", "Small");
        
        float circleSize = SMALL_CIRCLE_SIZE;
        if (circleSizePref.equals("Large")) {
            circleSize = LARGE_CIRCLE_SIZE;
        }
        
        if (colorPref.equals("Pink")) {
            return new PinkTheme(circleSize);
        }
        
        return new DefaultTheme(circleSize);
    }
}