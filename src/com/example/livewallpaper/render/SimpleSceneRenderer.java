/*******************************************************************************
 * Unpublished work, Copyright 2013, Clear Channel Communications, All Rights Reserved
 ******************************************************************************/

package com.example.livewallpaper.render;

import com.example.livewallpaper.model.SceneModel;
import com.example.livewallpaper.theme.WallpaperTheme;
import android.graphics.Canvas;

/**
 * 
 */
public class SimpleSceneRenderer implements SceneRenderer {

    @Override
    public void draw(SceneModel sceneModel, WallpaperTheme theme, Canvas canvas) {

        // clear the background
        canvas.drawPaint(theme.backgroundPaint());

        // draw objects
        canvas.drawCircle(sceneModel.getCenterX(), sceneModel.getCenterY(), sceneModel.outerCircleRadius(), theme.outerCirclePaint());
        canvas.drawCircle(sceneModel.getCenterX(), sceneModel.getCenterY(), 5f, theme.circlePaint());
        canvas.drawCircle(sceneModel.circleX(), sceneModel.circleY(), sceneModel.circleRadius(), theme.circlePaint());

    }
}
