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
public class DefaultTheme extends WallpaperTheme {
    private static final int DEFAULT_OUTER_CIRCLE_COLOR = 0xff5e736d;
    private static final int DEFAULT_CIRCLE_COLOR = 0xffa2bd3a;

    
    private final Paint backgroundPaint;
    private final Paint circlePaint;
    private final Paint outerCirclePaint;
    private final float circleSize;

    public DefaultTheme(float circleSize) {
        this.circleSize = circleSize;
        backgroundPaint = new Paint();
        backgroundPaint().setColor(Color.YELLOW);
        
        circlePaint = new Paint();
        circlePaint.setColor(DEFAULT_CIRCLE_COLOR);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Style.FILL);
        
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(DEFAULT_OUTER_CIRCLE_COLOR);
        outerCirclePaint.setAntiAlias(true);
        outerCirclePaint.setStyle(Style.STROKE);
        outerCirclePaint.setStrokeWidth(1.0f);
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
    @Override
    public float getCircleSize() {
        return circleSize;
    }


}
