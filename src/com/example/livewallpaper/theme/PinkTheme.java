/*******************************************************************************
 * Unpublished work, Copyright 2013, Clear Channel Communications, All Rights Reserved
 ******************************************************************************/

package com.example.livewallpaper.theme;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

/**
 * 
 */
public class PinkTheme implements WallpaperTheme {

    
    private final Paint backgroundPaint;
    private final Paint circlePaint;
    private final Paint outerCirclePaint;

    public PinkTheme() {
        backgroundPaint = new Paint();
        backgroundPaint().setColor(Color.BLACK);
        
        circlePaint = new Paint();
        circlePaint.setColor(Color.RED);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Style.FILL);
        
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.BLUE);
        outerCirclePaint.setAntiAlias(true);
        outerCirclePaint.setStyle(Style.STROKE);
        outerCirclePaint.setStrokeWidth(3.0f);
    }
    @Override
    public Paint backgroundPaint() {
        return backgroundPaint;
    }

    @Override
    public Paint circlePaint() {
        return circlePaint;
    }

    @Override
    public Paint outerCirclePaint() {
        return outerCirclePaint;
    }

}
