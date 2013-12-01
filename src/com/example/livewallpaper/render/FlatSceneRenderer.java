/*******************************************************************************
 * Unpublished work, Copyright 2013, Clear Channel Communications, All Rights Reserved
 ******************************************************************************/

package com.example.livewallpaper.render;

import android.graphics.Canvas;
import com.example.livewallpaper.model.Scene;
import com.example.livewallpaper.theme.WallpaperTheme;

/**
 * 
 */
public class FlatSceneRenderer implements Renderer {

    @Override
    public void render(Scene scene, WallpaperTheme theme, Canvas canvas) {

        // clear the background
        canvas.drawPaint(theme.backgroundPaint());

        // draw objects
        canvas.drawCircle(scene.getCenterX(), scene.getCenterY(), scene.outerCircleRadius(), theme.outerCirclePaint());
        canvas.drawCircle(scene.getCenterX(), scene.getCenterY(), 5f, theme.circlePaint());
        canvas.drawCircle(scene.circleX(), scene.circleY(), scene.circleRadius(), theme.circlePaint());

    }
}
