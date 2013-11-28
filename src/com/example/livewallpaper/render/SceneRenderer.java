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
public interface SceneRenderer {

    public abstract void draw(SceneModel sceneModel, WallpaperTheme theme, Canvas canvas);

}