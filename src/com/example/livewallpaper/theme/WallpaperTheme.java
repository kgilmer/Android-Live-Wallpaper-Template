/*******************************************************************************
 * Unpublished work, Copyright 2013, Clear Channel Communications, All Rights Reserved
 ******************************************************************************/

package com.example.livewallpaper.theme;

import android.graphics.Paint;

public interface WallpaperTheme {

    /**
     * @return
     */
    Paint backgroundPaint();

    /**
     * @return
     */
    Paint circlePaint();

    /**
     * @return
     */
    Paint outerCirclePaint();

    
}